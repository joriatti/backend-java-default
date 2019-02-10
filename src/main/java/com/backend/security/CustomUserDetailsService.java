package com.backend.security;

import com.backend.model.User;
import com.backend.model.enums.UserStateEnum;
import com.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Não foi possível encontrar o Usuário: " + email);
        }

        if (user.getState().equals(UserStateEnum.INACTIVE)) {
            throw new UsernameNotFoundException("Usuário não está ativo");
        }

        return org.springframework.security.core.userdetails
                .User.withUsername(user.getEmail()).password(user.getPassword()).roles("ADMIN").build();
    }
}