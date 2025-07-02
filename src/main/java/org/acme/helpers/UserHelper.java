package org.acme.helpers;

import org.acme.dto.ResultRequest;
import org.acme.dto.UserPostRequest;
import org.acme.dto.UserRequestResponse;
import org.acme.dto.UserUpdateRequest;
import org.acme.dto.results.Result;
import org.acme.entity.Users;

import java.util.List;

public interface UserHelper {
    Result<Boolean> isValidUserPostRequest(UserPostRequest userPostRequest);
    Result<Boolean> isValidUserUpdateRequest(UserUpdateRequest userUpdateRequest);
    ResultRequest<UserRequestResponse> createUserResultRequest(String message, UserRequestResponse data, List<String> errors);
    ResultRequest<UserRequestResponse> updateUserResultRequest(String message, UserRequestResponse data, List<String> errors);
    ResultRequest<List<UserRequestResponse>> fetchUserResultRequest(String message,
                                                                    List<UserRequestResponse> data,
                                                                    List<String> errors,
                                                                    int page,
                                                                    int pages,
                                                                    int size);
    Users getUserFromUserPostRequest(UserPostRequest userPostRequest);
    UserRequestResponse getUserByEmailRequestResponse(String email);
    UserRequestResponse getUserRequestResponse(Users user);
}
