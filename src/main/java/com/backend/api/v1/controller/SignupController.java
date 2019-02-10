package com.backend.api.v1.controller;

import com.backend.api.v1.dto.SignupRequest;
import com.backend.api.v1.dto.SignupResponse;
import com.backend.api.v1.interceptor.ErrorResponse;
import com.backend.api.v1.mapper.SignupMapper;
import com.backend.exceptions.BaseException;
import com.backend.exceptions.NotFoundException;
import com.backend.model.Signup;
import com.backend.security.AuthenticationContext;
import com.backend.service.SignupService;
import com.backend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

// http://localhost:8080/swagger-ui.html#/Signup
@RestController("SignupController")
@RequestMapping(BaseController.BASE_PATH + "/signup")
@Api(description = "Signup", tags = "Signup")
public class SignupController extends BaseController {

    @Autowired
    private AuthenticationContext authenticationContext;

    @Autowired
    private UserService userService;

    @Autowired
    private SignupService signupService;

    // POST: http://localhost:8080/api/signup/
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("User Signup")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok", response = SignupResponse.class),
            @ApiResponse(code = 400, message = "Error on register User", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "User not authorized", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "User Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Error", response = ErrorResponse.class)
    })
    public ResponseEntity<SignupResponse> register(
            @ApiParam(value = "User", required = true) @RequestBody SignupRequest request)
            throws BaseException {

        Signup register = signupService.autoRegister(request.getEmail(), request.getFirstName(),
                request.getLastName(), request.getPassword(), request.getRetypePassword());

        SignupResponse response = SignupMapper.getInstance().toResponse(register);

        return buildCreatedSuccessResponse(response);
    }

    @GetMapping(value = "/{signupId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("Find Signup by Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok", response = SignupResponse.class),
            @ApiResponse(code = 400, message = "Error on find Signup", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "User not authorized", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "User Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Error", response = ErrorResponse.class)
    })
    public ResponseEntity<SignupResponse> findSignupById(
            @ApiParam(value = "Signup Id", required = true) @PathVariable String signupId) throws BaseException {

        Optional<Signup> register = signupService.findById(signupId);
        if (!register.isPresent()) {
            throw new NotFoundException("RECORD_NOT_FOUND");
        }
        SignupResponse response = SignupMapper.getInstance().toResponse(register.get());

        return buildCreatedSuccessResponse(response);
    }

    @PostMapping(value = "/{email}/{signupHash}")
    @ApiOperation("Confirm User Register")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok", response = SignupResponse.class),
            @ApiResponse(code = 400, message = "Error on register User", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "User not authorized", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "User Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Error", response = ErrorResponse.class)
    })
    public ResponseEntity confirmUser(
            @ApiParam(value = "Email", required = true) @PathVariable String email,
            @ApiParam(value = "Signup Hash", required = true) @PathVariable String signupHash)
            throws BaseException {
        signupService.confirmUser(email, signupHash);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
