package com.backend.security;

import com.backend.model.User;
import com.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class AuthenticationContext {

    private static final Logger logger = Logger.getLogger(AuthenticationContext.class.getName());

    @Autowired
    private UserService userService;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            return userService.findByEmail(authentication.getName());
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Não encontrou o usuário atual");
        }
        return null;
    }
}