package com.backend;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ApplicationStartup {

    @EventListener(ApplicationReadyEvent.class)
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void startup() throws Exception {
    }
}