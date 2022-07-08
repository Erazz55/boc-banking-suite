package com.icptechno.admincore.user.events;

import com.icptechno.admincore.user.ApplicationUser;
import org.springframework.context.ApplicationEvent;

public class UserCreateEvent extends ApplicationEvent {

    private final ApplicationUser user;

    public UserCreateEvent(Object source, ApplicationUser user) {
        super(source);
        this.user = user;
    }

    public ApplicationUser getUser() {
        return user;
    }

}
