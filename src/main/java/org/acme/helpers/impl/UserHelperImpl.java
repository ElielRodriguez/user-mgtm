package org.acme.helpers.impl;

import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.acme.dto.ResultRequest;
import org.acme.dto.UserPostRequest;
import org.acme.dto.UserRequestResponse;
import org.acme.dto.UserUpdateRequest;
import org.acme.dto.results.Result;
import org.acme.dto.results.ResultStatus;
import org.acme.entity.Users;
import org.acme.helpers.UserHelper;
import org.acme.repository.UsersRepository;
import org.acme.service.CountryService;
import org.acme.service.ResultService;

import java.util.List;
import java.util.Set;

@ApplicationScoped
public class UserHelperImpl implements UserHelper {

    @Inject
    ResultService<UserRequestResponse> saveUserResult;

    @Inject
    CountryService countryService;

    @Inject
    Validator validator;

    @Inject
    UsersRepository usersRepository;

    /**
     * @param userPostRequest
     * @return
     */
    @Override
    public Result<Boolean> isValidUserPostRequest(UserPostRequest userPostRequest) {
        Set<ConstraintViolation<UserPostRequest>> userViolations = validator.validate(userPostRequest);

        if (!userViolations.isEmpty()) {
            String errorMessage = userViolations.stream()
                    .map(ConstraintViolation::getMessage)
                    .reduce((msg1, msg2) -> msg1 + ", " + msg2)
                    .orElse("Invalid user post request");
            Log.debug("User post request validation failed: " + errorMessage);
            return this.createErrorResult(List.of(errorMessage));
        }

        if (!countryService.isValid(userPostRequest.getCountry())) {
            Log.debug("Invalid ISO 3166 country code: " + userPostRequest.getCountry());
            return this.createErrorResult(List.of("Invalid ISO 3166 country code"));
        }

        return this.createSuccessResult();
    }

    /**
     * @param userUpdateRequest
     * @return
     */
    @Override
    public Result<Boolean> isValidUserUpdateRequest(UserUpdateRequest userUpdateRequest) {
        Set<ConstraintViolation<UserUpdateRequest>> userViolations = validator.validate(userUpdateRequest);

        if (!userViolations.isEmpty()) {
            String errorMessage = userViolations.stream()
                    .map(ConstraintViolation::getMessage)
                    .reduce((msg1, msg2) -> msg1 + ", " + msg2)
                    .orElse("Invalid user update request");
            Log.debug("User update request validation failed: " + errorMessage);
            return this.createErrorResult(List.of(errorMessage));
        }

        if (!countryService.isValid(userUpdateRequest.getCountry())) {
            Log.debug("Invalid ISO 3166 country code: " + userUpdateRequest.getCountry());
            return this.createErrorResult(List.of("Invalid ISO 3166 country code"));
        }

        return this.createSuccessResult();
    }

    /**
     * @param message
     * @param userRequestResponse
     * @param errors
     * @return
     */
    @Override
    public ResultRequest<UserRequestResponse> createUserResultRequest(String message, UserRequestResponse userRequestResponse, List<String> errors) {
        ResultRequest<UserRequestResponse> resultRequest = new ResultRequest<>();
        resultRequest.setMessage(message);
        resultRequest.setData(userRequestResponse);
        resultRequest.setCode(errors.isEmpty() ? 200 : 400);
        resultRequest.setErrors(errors);
        return resultRequest;
    }

    /**
     * @param message
     * @param userRequestResponse
     * @param errors
     * @return
     */
    @Override
    public ResultRequest<UserRequestResponse> updateUserResultRequest(String message, UserRequestResponse userRequestResponse, List<String> errors) {
        ResultRequest<UserRequestResponse> resultRequest = new ResultRequest<>();
        resultRequest.setMessage(message);
        resultRequest.setData(userRequestResponse);
        resultRequest.setCode(errors.isEmpty() ? 200 : 400);
        resultRequest.setErrors(errors);
        return resultRequest;
    }

    /**
     * @param message
     * @param data
     * @param errors
     * @return
     */
    @Override
    public ResultRequest<List<UserRequestResponse>> fetchUserResultRequest(String message,
                                                                           List<UserRequestResponse> data,
                                                                           List<String> errors,
                                                                           int page,
                                                                           int pages,
                                                                           int size) {
        ResultRequest<List<UserRequestResponse>> resultRequest = new ResultRequest<>();
        resultRequest.setMessage(message);
        resultRequest.setData(data);
        resultRequest.setCode(errors.isEmpty() ? 200 : 400);
        resultRequest.setErrors(errors);
        resultRequest.setPage(page);
        resultRequest.setPages(pages);
        resultRequest.setSize(size);
        return resultRequest;
    }

    @Override
    public Users getUserFromUserPostRequest(UserPostRequest userPostRequest) {
        Users existingUser = usersRepository.findByEmail(userPostRequest.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        Users user = new Users();
        user.setFirstName(userPostRequest.getFirstName());
        user.setSecondName(userPostRequest.getSecondName());
        user.setFirstLastName(userPostRequest.getFirstLastName());
        user.setSecondLastName(userPostRequest.getSecondLastName());
        user.setEmail(userPostRequest.getEmail());
        user.setAddress(userPostRequest.getAddress());
        user.setCountry(userPostRequest.getCountry());
        user.setPhoneNumber(userPostRequest.getPhoneNumber());
        return user;
    }

    /**
     * @param email
     * @return
     */
    @Override
    public UserRequestResponse getUserByEmailRequestResponse(String email) {
        try {
            Users user = usersRepository.findByEmail(email);
            UserRequestResponse response = new UserRequestResponse();
            response.setId(user.getId());
            response.setAddress(user.getAddress());
            response.setDemonym(user.getDemonym());
            response.setEmail(user.getEmail());
            response.setFirstName(user.getFirstName());
            response.setFirstLastName(user.getFirstLastName());
            response.setSecondName(user.getSecondName());
            response.setSecondLastName(user.getSecondLastName());
            response.setCountry(user.getCountry());
            response.setPhoneNumber(user.getPhoneNumber());
            return response;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * @param user
     * @return
     */
    @Override
    public UserRequestResponse getUserRequestResponse(Users user) {
        UserRequestResponse response = new UserRequestResponse();
        response.setId(user.getId());
        response.setAddress(user.getAddress());
        response.setDemonym(user.getDemonym());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setFirstLastName(user.getFirstLastName());
        response.setSecondName(user.getSecondName());
        response.setSecondLastName(user.getSecondLastName());
        response.setCountry(user.getCountry());
        response.setPhoneNumber(user.getPhoneNumber());
        return response;
    }

    private Result<Boolean> createErrorResult(List<String> errors) {
        ResultStatus status = new ResultStatus();
        status.setMessage("Invalid");
        status.setCode(400);

        Result<Boolean> errorResult = new Result<>();
        errorResult.setStatus(status);
        errorResult.setData(false);
        errorResult.setErrors(errors);
        return errorResult;
    }

    private Result<Boolean> createSuccessResult() {
        ResultStatus status = new ResultStatus();
        status.setMessage("Valid");
        status.setCode(200);

        Result<Boolean> errorResult = new Result<>();
        errorResult.setStatus(status);
        errorResult.setData(true);
        errorResult.setErrors(List.of());
        return errorResult;
    }
}
