package com.icptechno.admincore.user.listeners;

import com.icptechno.admincore.approval.Approval;
import com.icptechno.admincore.approval.ApprovalType;
import com.icptechno.admincore.approval.events.ApprovalEvent;
import com.icptechno.admincore.common.ApprovalStatus;
import com.icptechno.admincore.common.util.CryptoUtil;
import com.icptechno.admincore.user.UserService;
import com.icptechno.admincore.user.UserStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UserApprovalListener implements ApplicationListener<ApprovalEvent> {

    Logger logger = LoggerFactory.getLogger(UserApprovalListener.class);

    final UserService userService;

    public UserApprovalListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ApprovalEvent approvalEvent) {
        Approval approval = approvalEvent.getApproval();
        if (approval.getType() != ApprovalType.USER) {
            return;
        }
        UserStatus status = UserStatus.PENDING;
        switch (approval.getStatus()) {
            case APPROVED:
                status = UserStatus.ACTIVE;
                break;
            case REJECTED:
                status = UserStatus.INACTIVE;
                break;
        }
        userService.updateStatusById(approval.getRefId(), status);

        if (approval.getStatus() == ApprovalStatus.APPROVED) {
            // generate a random password for the user
            String password = CryptoUtil.getRandomString(8);

            userService.updatePasswordById(approval.getRefId(), password);

            logger.debug("PASSWORD SET: " + approval.getRefId() + " " + password);

            //TODO: email the password to user
        }

    }
}
