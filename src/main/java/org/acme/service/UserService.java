package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.dto.UserPostRequest;
import org.acme.dto.UserUpdateRequest;
import org.acme.dto.UserRequestResponse;
import org.acme.dto.results.Result;
import java.util.List;

@ApplicationScoped
public interface UserService {
    Result<List<UserRequestResponse>> findAll(int page, int size);
    Result<List<UserRequestResponse>> findByCountry(String country, int page, int size);
    Result<UserRequestResponse> findById(long id);
    Result<UserRequestResponse> save(UserPostRequest userPostRequest);
    Result<UserRequestResponse> update(long id, UserUpdateRequest userUpdateRequest);
    Result<UserRequestResponse> delete(long id);
}
