package com.backend.api.v1.mapper;


import com.backend.api.v1.dto.SignupResponse;
import com.backend.model.Signup;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class SignupMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    private static SignupMapper instance;

    public static SignupMapper getInstance() {
        if (instance == null) {
            instance = new SignupMapper();
        }
        return instance;
    }

    private SignupMapper() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public SignupResponse toResponse(Signup register) {
        return modelMapper.map(register, SignupResponse.class);
    }

}