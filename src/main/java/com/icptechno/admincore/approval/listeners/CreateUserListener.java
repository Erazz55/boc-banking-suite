package com.icptechno.admincore.approval.listeners;

import com.icptechno.admincore.approval.ApprovalService;
import com.icptechno.admincore.user.events.UserCreateEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CreateUserListener implements ApplicationListener<UserCreateEvent> {

    final ApprovalService approvalService;

    public CreateUserListener(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @Override
    public void onApplicationEvent(UserCreateEvent userCreateEvent) {
        approvalService.createUserApproval(userCreateEvent.getUser());
    }
}

