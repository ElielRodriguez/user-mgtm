package org.acme.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import io.quarkus.logging.Log;
import jakarta.transaction.Transactional;
import org.acme.dto.CountriesGetResponse;
import org.acme.dto.UserPostRequest;
import org.acme.dto.UserUpdateRequest;
import org.acme.dto.UserRequestResponse;
import org.acme.dto.results.Result;
import org.acme.entity.Users;
import org.acme.helpers.UserHelper;
import org.acme.repository.UsersRepository;
import org.acme.service.ResultService;
import org.acme.service.UserService;

import java.util.List;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    DemonymServiceImpl demonymService;

    @Inject
    ResultService<UserRequestResponse> userResult;

    @Inject
    ResultService<List<UserRequestResponse>> userListResult;

    @Inject
    UserHelper userHelper;

    @Inject
    UsersRepository usersRepository;

    /**
     * Fetch all users with pagination support.
     *
     * @param page
     * @param size
     * @return
     * @see UserRequestResponse
     */
    @Override
    public Result<List<UserRequestResponse>> findAll(int page, int size) {
        Log.info("Fetching all users with page: " + page + " and size: " + size);
        var result = usersRepository.findAll();
        if (result == null || result.count() == 0) {
            return this.notFoundResult(List.of(), page, size);
        }

        int pages = (int) Math.ceil((double) result.count() / size);

        List<UserRequestResponse> userList = result
                .page(page, size)
                .stream()
                .map(userHelper::getUserRequestResponse)
                .toList();

        if (userList.isEmpty()) {
            return this.notFoundResult(List.of(), page, size);
        }

        Log.info("Successfully fetched users");
        return userListResult.createResultResponse(
                userHelper.fetchUserResultRequest(
                        "Users fetched successfully",
                        userList,
                        List.of(),
                        page,
                        pages,
                        size));
    }

    /**
     * Fetch users by country with pagination support.
     * This method retrieves users based on the specified country,
     * allowing for pagination through page and size parameters.
     *
     * @param country
     * @param page
     * @param size
     * @return
     */
    @Override
    public Result<List<UserRequestResponse>> findByCountry(String country, int page, int size) {
        Log.info("Fetching users by country: " + country + " with page: " + page + " and size: " + size);
        var result = usersRepository.findByCountry(country);

        if (result == null || result.count() == 0) {
            return this.notFoundResult(List.of(), page, size);
        }

        int pages = (int) Math.ceil((double) result.count() / size);

        List<UserRequestResponse> userList = result
                .page(page, size)
                .stream()
                .map(userHelper::getUserRequestResponse)
                .toList();

        if(userList.isEmpty()){
            return this.notFoundResult(List.of(), page, size);
        }

        Log.info("Successfully fetched users by country");
        return userListResult.createResultResponse(
                userHelper.fetchUserResultRequest(
                        "Users fetched successfully",
                        userList,
                        List.of(),
                        page,
                        pages,
                        size));
    }

    /**
     * Fetch a user by their ID.
     *
     * @param id
     * @return
     * @see UserRequestResponse
     */
    @Override
    public Result<UserRequestResponse> findById(long id) {
        Log.info("Fetching user by id: " + id);
        Users user = usersRepository.findById(id);
        if (user == null) {
            Log.warn("User with id " + id + " not found");
            return userResult.createResultResponse(
                    userHelper.createUserResultRequest(
                            "User with id " + id + " not found",
                            null,
                            List.of()));
        }
        Log.info("Successfully fetched user with id: " + id);
        return userResult.createResultResponse(
                userHelper.createUserResultRequest(
                        "User fetched successfully",
                        userHelper.getUserRequestResponse(user),
                        List.of()));
    }

    /**
     * Persist a new user based on the provided UserPostRequest.
     * This method validates the request, checks if the user already exists,
     * and performs the persist operation.
     *
     * @param userPostRequest
     * @return true or false based on user persist operation
     * @see UserPostRequest
     */
    @Transactional
    @Override
    public Result<UserRequestResponse> save(UserPostRequest userPostRequest) {
        try {
            var validationResult = userHelper.isValidUserPostRequest(userPostRequest);
            if (validationResult.getStatus().getCode() != 200) {
                return userResult.createResultResponse(
                        userHelper.createUserResultRequest(
                                validationResult.getStatus().getMessage(),
                                null,
                                validationResult.getErrors()));
            }
            Log.info("User post request is valid");
            Users user = userHelper.getUserFromUserPostRequest(userPostRequest);
            Log.debug("User not exists, creating new user");
            CountriesGetResponse demonymResponse = demonymService.getDemonynByCountryName(userPostRequest.getCountry());
            Log.info("Demonym for country is found");
            user.setDemonym(demonymResponse.getDemonym());
            usersRepository.persist(user);

            if (!usersRepository.isPersistent(user)) {
                String errorMsg = "User was not persisted successfully";
                Log.error(errorMsg);
                throw new IllegalStateException(errorMsg);
            }

            Log.info("User created successfully with email: " + user.getEmail());

            return userResult.createResultResponse(userHelper.createUserResultRequest(
                    "User created successfully",
                    userHelper.getUserByEmailRequestResponse(user.getEmail()),
                    List.of()
            ));
        } catch (Exception ex) {
            Log.warn("Error creating user: {}", ex.getMessage(), ex);
            return userResult.createResultResponse(userHelper.createUserResultRequest(
                    "Error creating user: " + ex.getMessage(),
                    null,
                    List.of(ex.getMessage())
            ));
        }
    }

    /**
     * Update an existing user based on the provided UserUpdateRequest.
     * This method checks if the user exists, validates the update request,
     * and performs the update operation.
     *
     * @param id
     * @param userUpdateRequest
     * @return updatedUser
     * @see UserRequestResponse
     */
    @Transactional
    @Override
    public Result<UserRequestResponse> update(long id, UserUpdateRequest userUpdateRequest) {
        try {
            var validationResult = userHelper.isValidUserUpdateRequest(userUpdateRequest);
            if (validationResult.getStatus().getCode() != 200) {
                return userResult.createResultResponse(
                        userHelper.createUserResultRequest(
                                validationResult.getStatus().getMessage(),
                                null,
                                validationResult.getErrors()));
            }
            Log.info("User update request is valid");

            Users existingUser = usersRepository.findById(id);
            if (existingUser == null) {
                String errorMsg = "User with id " + id + " not found";
                Log.error(errorMsg);
                throw new IllegalStateException(errorMsg);
            }

            CountriesGetResponse demonymResponse = demonymService.getDemonynByCountryName(userUpdateRequest.getCountry());

            int execution = usersRepository.update(String.format("email='%s', address='%s', phoneNumber='%s', country='%s', demonym='%s' where id=%s",
                    userUpdateRequest.getEmail(),
                    userUpdateRequest.getAddress(),
                    userUpdateRequest.getPhoneNumber(),
                    userUpdateRequest.getCountry(),
                    demonymResponse.getDemonym(),
                    id));

            if (execution <= 0) {
                String errorMsg = "User with id " + id + " not found or not updated";
                Log.error(errorMsg);
                throw new IllegalStateException(errorMsg);
            }

            return userResult.createResultResponse(userHelper.updateUserResultRequest(
                    "User updated sucessfully",
                    userHelper.getUserByEmailRequestResponse(userUpdateRequest.getEmail()),
                    List.of()
            ));

        } catch (Exception ex) {
            Log.error("Error updating user: {}", ex.getMessage(), ex);
            return userResult.createResultResponse(userHelper.updateUserResultRequest(
                    "Error updating user: " + ex.getMessage(),
                    null,
                    List.of(ex.getMessage())
            ));
        }
    }

    /**
     * Delete a user by their ID.
     * This method checks if the user exists,
     * and performs the delete operation.
     *
     * @param id
     * @return Result<UserRequestResponse>
     * @see UserRequestResponse
     */
    @Transactional
    @Override
    public Result<UserRequestResponse> delete(long id) {
        Log.info("Deleting user with id: " + id);
        Users user = usersRepository.findById(id);
        if (user == null) {
            Log.warn("User with id " + id + " not found");
            return userResult.createResultResponse(
                    userHelper.createUserResultRequest(
                            "User not found",
                            null,
                            List.of("User with id " + id + " not found")));
        }
        usersRepository.delete(user);
        Log.info("Successfully deleted user with id: " + id);
        return userResult.createResultResponse(
                userHelper.createUserResultRequest(
                        "User deleted successfully",
                        userHelper.getUserRequestResponse(user),
                        List.of()));
    }

    private Result<List<UserRequestResponse>> notFoundResult(List<String> errors, int page, int size) {
        Log.warn("No users found");
        return userListResult.createResultResponse(
                userHelper.fetchUserResultRequest(
                        "No users found",
                        List.of(),
                        errors,
                        page,
                        0,
                        size));
    }
}
