package com.backend.api.v1.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import com.backend.api.v1.dto.UserResponse;
import com.backend.api.v1.dto.UserResponseList;
import com.backend.model.User;

public class UserMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    private static UserMapper instance;

    public static UserMapper getInstance() {
        if (instance == null) {
            instance = new UserMapper();
        }
        return instance;
    }

    private UserMapper() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public UserResponse toResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }
    
    public UserResponseList toResponseList(List<User> users) {
    	UserResponseList responseList = new UserResponseList();
    	
    	for (User user : users) {
    		responseList.getItems().add(modelMapper.map(user, UserResponse.class));
    	}
    	
    	return responseList;
    }
}
