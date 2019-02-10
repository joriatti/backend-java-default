package com.backend.api.v1.controller;

import com.backend.api.v1.dto.UserResponse;
import com.backend.api.v1.interceptor.ErrorResponse;
import com.backend.api.v1.mapper.UserMapper;
import com.backend.exceptions.BaseException;
import com.backend.model.User;
import com.backend.security.AuthenticationContext;
import com.backend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("UserController")
@RequestMapping(BaseController.BASE_PATH + "/users")
@Api(description = "Users", tags = "Users")
public class UserController extends BaseController {

    @Autowired
    private AuthenticationContext authenticationContext;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/current-user-data", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("Get Current User Data")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok", response = UserResponse.class),
            @ApiResponse(code = 400, message = "Error on find Signup", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "User not authorized", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "User Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Error", response = ErrorResponse.class)
    })
    public ResponseEntity<UserResponse> getCurrentUserData() throws BaseException {
        return buildSuccessResponse(UserMapper.getInstance().toResponse(authenticationContext.getCurrentUser()));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("Find User by Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok", response = UserResponse.class),
            @ApiResponse(code = 400, message = "Error on find Signup", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "User not authorized", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "User Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Error", response = ErrorResponse.class)
    })
    public ResponseEntity<UserResponse> findUserById(
            @ApiParam(value = "Id", required = true) @PathVariable String id) throws BaseException {

        User user = userService.findById(authenticationContext.getCurrentUser().getTenantId(), id);
        return buildSuccessResponse(UserMapper.getInstance().toResponse(user));
    }
}
