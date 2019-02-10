package com.backend.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdGenerator {

    public String nextId() {
        return UUID.randomUUID().toString();
    }
}