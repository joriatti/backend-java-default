package com.backend.api.v1.mapper;

import com.backend.api.v1.dto.UserResponse;
import com.backend.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

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
}
