package com.icptechno.admincore.audit.listeners;

import com.icptechno.admincore.user.events.UserCreateEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AuditUserCreateListener implements ApplicationListener<UserCreateEvent> {



    @Override
    public void onApplicationEvent(UserCreateEvent userCreateEvent) {

    }

}
