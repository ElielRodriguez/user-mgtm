package org.acme.services;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.acme.dto.ResultRequest;
import org.acme.dto.UserPostRequest;
import org.acme.dto.UserRequestResponse;
import org.acme.dto.results.Result;
import org.acme.dto.results.ResultStatus;
import org.acme.helpers.UserHelper;
import org.acme.service.ResultService;
import org.acme.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.acme.repository.UsersRepository;
import org.acme.entity.Users;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@QuarkusTest
public class UserServiceImplTest {

    @InjectMock
    UsersRepository usersRepository;

    @InjectMock
    UserHelper userHelper;

    @InjectMock
    ResultService<List<UserRequestResponse>> userListResultService;

    @Inject
    UserService userService;

    private Users user1;

    private Users user2;

    @BeforeEach
    void setup() {
        user1 = new Users();
        user1.setId(1L);
        user1.setFirstName("John");
        user1.setFirstLastName("Doe");
        user1.setEmail("johndoe@example.com");
        user1.setAddress("123 Main St");
        user1.setCountry("US");
        user1.setPhoneNumber("1234567890");
        user1.setDemonym("American");

        user2 = new Users();
        user2.setId(1L);
        user2.setFirstName("Sarah");
        user2.setFirstLastName("Connor");
        user2.setEmail("sarahconnor@example.com");
        user2.setAddress("456 Main St");
        user2.setCountry("DO");
        user2.setPhoneNumber("0987654321");
        user2.setDemonym("Dominican");
    }

    @Test
    void testCreateUser_Success() {
        UserPostRequest user = new UserPostRequest();
        user.setFirstName("John");
        user.setFirstLastName("Doe");
        user.setEmail("johndoe@test.com");
        user.setAddress("1st street");
        user.setCountry("KP");
        user.setPhoneNumber("1234567890");

        UserRequestResponse userResponse1 = new UserRequestResponse();
        userResponse1.setId(user1.getId());
        userResponse1.setFirstName(user1.getFirstName());
        userResponse1.setFirstLastName(user1.getFirstLastName());
        userResponse1.setPhoneNumber(user1.getPhoneNumber());
        userResponse1.setEmail(user1.getEmail());
        userResponse1.setAddress(user1.getAddress());
        userResponse1.setCountry(user1.getCountry());
        userResponse1.setDemonym(user1.getDemonym());

        ResultRequest<UserRequestResponse> resultRq = new ResultRequest<>();
        resultRq.setData(userResponse1);
        resultRq.setCode(200);

        Mockito.doNothing().when(usersRepository).persist(ArgumentMatchers.any(Users.class));
        Mockito.when(usersRepository.isPersistent(ArgumentMatchers.any(Users.class))).thenReturn(true);
        Mockito.when(userHelper.createUserResultRequest(any(), any(), any()))
                .thenReturn(resultRq);

        var result = userService.save(user);
        assertEquals(200, result.getStatus().getCode());
    }

    @Test
    void testGetAllUsers_Success() {
        PanacheQuery<Users> query = Mockito.mock(PanacheQuery.class);
        List<Users> usersList = List.of(user1, user2);
        Stream<Users> userStream = Stream.of(user1);

        UserRequestResponse userResponse1 = new UserRequestResponse();
        userResponse1.setId(user1.getId());
        userResponse1.setFirstName(user1.getFirstName());
        userResponse1.setFirstLastName(user1.getFirstLastName());
        userResponse1.setPhoneNumber(user1.getPhoneNumber());
        userResponse1.setEmail(user1.getEmail());
        userResponse1.setAddress(user1.getAddress());
        userResponse1.setCountry(user1.getCountry());
        userResponse1.setDemonym(user1.getDemonym());

        ResultRequest<List<UserRequestResponse>> userListResultRequest = new ResultRequest<>();
        userListResultRequest.setData(List.of(userResponse1));
        userListResultRequest.setCode(200);
        userListResultRequest.setMessage("Users found successfully");
        userListResultRequest.setPage(0);
        userListResultRequest.setSize(10);
        userListResultRequest.setPages(1);

        Mockito.when(usersRepository.findAll()).thenReturn(query);
        Mockito.when(query.count()).thenReturn(1L);
        Mockito.when(query.page(anyInt(), anyInt())).thenReturn(query);
        Mockito.when(query.list()).thenReturn(usersList);
        Mockito.when(query.stream()).thenReturn(userStream);
        Mockito.when(userHelper.getUserRequestResponse(user1)).thenReturn(userResponse1);
        Mockito.when(userHelper.fetchUserResultRequest("Users found successfully",
                        List.of(userResponse1),
                        List.of(),
                        0,
                        1,
                        10))
                .thenReturn(userListResultRequest);

        Result<List<UserRequestResponse>> resultResponse = new Result<>();
        resultResponse.setData(userListResultRequest.getData());

        ResultStatus status = new ResultStatus();
        status.setCode(200);
        status.setMessage("Users found successfully");

        resultResponse.setStatus(status);

        Mockito.when(userListResultService.createResultResponse(any()))
                .thenReturn(resultResponse);

        var result = userService.findAll(0, 10);
        assertEquals(200, result.getStatus().getCode());
        assertFalse(result.getData().isEmpty());
        assertEquals("John", result.getData().get(0).getFirstName());
    }

    @Test
    void testGetUsersByCountry_Success() {
        String country = "DO";
        int page = 0;
        int size = 10;

        PanacheQuery<Users> query = Mockito.mock(PanacheQuery.class);
        List<Users> usersList = List.of(user1, user2);
        Stream<Users> userStream = usersList.stream();

        UserRequestResponse userResponse = new UserRequestResponse();
        userResponse.setFirstName("Sarah");
        List<UserRequestResponse> responseList = List.of(userResponse);

        ResultRequest<List<UserRequestResponse>> resultRequest = new ResultRequest<>();
        resultRequest.setData(responseList);
        resultRequest.setPage(page);
        resultRequest.setSize(size);
        resultRequest.setErrors(List.of());
        resultRequest.setMessage("Users found successfully");

        Result<List<UserRequestResponse>> resultResponse = new Result<>();
        resultResponse.setData(resultRequest.getData());

        ResultStatus status = new ResultStatus();
        status.setCode(200);
        status.setMessage("Users found successfully");
        status.setPage(page);
        status.setSize(size);
        status.setPages(1);
        resultResponse.setStatus(status);

        Mockito.when(usersRepository.findByCountry(country)).thenReturn(query);
        Mockito.when(query.count()).thenReturn(1L);
        Mockito.when(query.page(anyInt(), anyInt())).thenReturn(query);
        Mockito.when(query.stream()).thenReturn(userStream);

        Mockito.when(userHelper.getUserRequestResponse(user2)).thenReturn(userResponse);
        Mockito.when(userHelper.fetchUserResultRequest(
                Mockito.anyString(),
                Mockito.eq(responseList),
                Mockito.anyList(),
                Mockito.eq(page),
                Mockito.anyInt(),
                Mockito.eq(size)
        )).thenReturn(resultRequest);

        Mockito.when(userListResultService.createResultResponse(any()))
                .thenReturn(resultResponse);

        var result = userService.findByCountry("DO", 0, 10);
        assertEquals(200, result.getStatus().getCode());
        assertFalse(result.getData().isEmpty());
        assertEquals("Sarah", result.getData().get(0).getFirstName());
    }
}
