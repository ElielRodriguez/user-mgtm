package org.acme.helpers;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.dto.*;
import org.acme.entity.Users;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UserHelperImplTest {

    @Inject
    private UserHelper userHelper;

    @Inject
    Validator validator;

    @Test
    void testIsValidUserPostRequest() {
        UserPostRequest validRequest = new UserPostRequest();
        validRequest.setAddress("4th street");
        validRequest.setCountry("DO");
        validRequest.setEmail("validEmail@email.com");
        validRequest.setFirstName("John");
        validRequest.setFirstLastName("Doe");
        validRequest.setPhoneNumber("1234567890");
        var result = userHelper.isValidUserPostRequest(validRequest);
        assertEquals(200, result.getStatus().getCode());
    }

    @Test
    void testMissingPhoneNumberUserPostRequest() {
        UserPostRequest validRequest = new UserPostRequest();
        validRequest.setAddress("4th street");
        validRequest.setCountry("DO");
        validRequest.setEmail("validEmail@email.com");
        validRequest.setFirstName("John");
        validRequest.setFirstLastName("Doe");
        var result = userHelper.isValidUserPostRequest(validRequest);
        assertEquals(400, result.getStatus().getCode());
    }

    @Test
    void testInvalidPhoneNumberUserPostRequest() {
        UserPostRequest validRequest = new UserPostRequest();
        validRequest.setAddress("4th street");
        validRequest.setCountry("DO");
        validRequest.setEmail("validEmail@email.com");
        validRequest.setFirstName("John");
        validRequest.setFirstLastName("Doe");
        validRequest.setPhoneNumber("123456789"); // Invalid phone number
        var result = userHelper.isValidUserPostRequest(validRequest);
        assertEquals(400, result.getStatus().getCode());
    }

    @Test
    void testInvalidEmailUserPostRequest() {
        UserPostRequest validRequest = new UserPostRequest();
        validRequest.setAddress("4th street");
        validRequest.setCountry("DO");
        validRequest.setEmail("validEmail@");
        validRequest.setFirstName("John");
        validRequest.setFirstLastName("Doe");
        validRequest.setPhoneNumber("1234567890");
        var result = userHelper.isValidUserPostRequest(validRequest);
        assertEquals(400, result.getStatus().getCode());
    }

    @Test
    void testIsValidUserUpdateRequest() {
        UserUpdateRequest validRequest = new UserUpdateRequest();
        // set valid fields
        validRequest.setAddress("4th street");
        validRequest.setCountry("DO");
        validRequest.setEmail("validEmail@email.com");
        validRequest.setPhoneNumber("1234567890");
        var result = userHelper.isValidUserUpdateRequest(validRequest);
        assertEquals(200, result.getStatus().getCode());
    }

    @Test
    void testInvalidUserUpdateRequest() {
        UserUpdateRequest validRequest = new UserUpdateRequest();
        // set valid fields
        validRequest.setAddress(""); // Empty address
        validRequest.setCountry(""); // Empty country
        validRequest.setEmail("validEmail@"); // Invalid email
        validRequest.setPhoneNumber("123456789"); // Invalid phone number
        var result = userHelper.isValidUserUpdateRequest(validRequest);
        assertEquals(400, result.getStatus().getCode());
    }

    @Test
    void testCreateUserResultRequest() {
        UserRequestResponse data = new UserRequestResponse();
        List<String> errors = List.of();
        ResultRequest<UserRequestResponse> result = userHelper.createUserResultRequest("Success", data, errors);
        assertEquals("Success", result.getMessage());
        assertEquals(data, result.getData());
        assertEquals(errors, result.getErrors());
    }

    @Test
    void testGetUserFromUserPostRequest() {
        UserPostRequest request = new UserPostRequest();
        // set fields
        request.setAddress("4th street");
        request.setCountry("DO");
        request.setEmail("validEmail@email.com");
        request.setFirstName("John");
        request.setFirstLastName("Doe");
        request.setPhoneNumber("1234567890");
        Users user = userHelper.getUserFromUserPostRequest(request);
        assertNotNull(user);
    }
}