package com.backend.api.v1.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class TranslatorService {

    @Autowired
    private MessageSource messageSource;

    public MessageDetailInfo translateError(String code, Object[] args) {
        return new MessageDetailInfo(messageSource.getMessage(code, args, new Locale("pt", "BR")));
    }
}