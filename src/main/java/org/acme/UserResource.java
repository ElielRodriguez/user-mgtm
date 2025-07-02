package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import io.quarkus.logging.Log;
import jakarta.ws.rs.core.Response;
import org.acme.dto.UserPostRequest;
import org.acme.dto.UserRequestResponse;
import org.acme.dto.UserUpdateRequest;
import org.acme.dto.results.Result;
import org.acme.service.UserService;

@Path("/users")
public class UserResource {
    @Inject
    UserService userService;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(@QueryParam("page") int page, @QueryParam("size") int size) {
        Log.info("Received request to find all users with page: " + page + " and size: " + size);
        var result = userService.findAll(page, size);
        if (result.getStatus().getCode() != 200) {
            Log.error("Error occurred while fetching users: " + result.getStatus().getMessage());
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(result)
                    .build();
        }
        Log.info("Successfully fetched users");
        return Response
                .status(Response.Status.OK)
                .entity(result)
                .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") long id) {
        Log.info("Received request to find user with id: " + id);
        var result = userService.findById(id);
        if (result.getStatus().getCode() != 200) {
            Log.error("Error occurred while fetching users: " + result.getStatus().getMessage());
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(result)
                    .build();
        }
        Log.info("Successfully fetched users");
        return Response
                .status(Response.Status.OK)
                .entity(result)
                .build();
    }

    @GET
    @Path("/country/{country}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByCountry(@PathParam("country") String country,
                                  @QueryParam("page") int page,
                                  @QueryParam("size") int size) {
        Log.info("Received request to find users by country: " + country + " with page: " + page + " and size: " + size);
        var result = userService.findByCountry(country, page, size);
        if (result.getStatus().getCode() != 200) {
            Log.error("Error occurred while fetching users: " + result.getStatus().getMessage());
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(result).build();
        }

        Log.info("Successfully fetched users");
        return Response
                .status(Response.Status.OK)
                .entity(result)
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Result<UserRequestResponse> save(UserPostRequest userPostRequest) {
        Log.info("Received request to save user: " + userPostRequest);
        var result = userService.save(userPostRequest);
        if (result.getStatus().getCode() != 200) {
            Log.error("Error occurred while saving user: " + result.getStatus().getMessage());
        } else {
            Log.info("User saved successfully");
        }
        return result;
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Result<UserRequestResponse> update(@PathParam("id") long id, UserUpdateRequest userUpdateRequest) {
        Log.info("Received request to save user: " + userUpdateRequest);
        var result = userService.update(id, userUpdateRequest);
        if (result.getStatus().getCode() != 200) {
            Log.error("Error occurred while saving user: " + result.getStatus().getMessage());
        } else {
            Log.info("User saved successfully");
        }
        return result;
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Result<UserRequestResponse> update(@PathParam("id") long id) {
        Log.info("Received request to delete user with id: " + id);
        var result = userService.delete(id);
        if (result.getStatus().getCode() != 200) {
            Log.error("Error occurred while saving user: " + result.getStatus().getMessage());
        } else {
            Log.info("User saved successfully");
        }
        return result;
    }
}
