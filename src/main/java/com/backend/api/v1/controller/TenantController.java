package com.backend.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.v1.dto.UserResponseList;
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

@RestController("TenantController")
@RequestMapping(BaseController.BASE_PATH + "/tenants")
@Api(description = "Tenants", tags = "Tenants")
public class TenantController extends BaseController {
	
	@Autowired
    private AuthenticationContext authenticationContext;

    @Autowired
    private UserService userService;
	
	@GetMapping(value = "/{tenantId}/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("Find All Users by Tenant Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok", response = UserResponseList.class),
            @ApiResponse(code = 400, message = "Error on find Signup", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "User not authorized", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "User Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Error", response = ErrorResponse.class)
    })
    public ResponseEntity<UserResponseList> findAllByTenantId(
            @ApiParam(value = "Tenant Id", required = true) @PathVariable String tenantId) throws BaseException {

        List<User> list = userService.findAllUsersByTenantId(authenticationContext.getCurrentUser().getTenantId(), tenantId);
        return buildSuccessResponse(UserMapper.getInstance().toResponseList(list));
    }

}
