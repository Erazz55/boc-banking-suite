package com.icptechno.admincore.approval.events;

import com.icptechno.admincore.approval.Approval;
import org.springframework.context.ApplicationEvent;

public class ApprovalEvent extends ApplicationEvent {

    final private Approval approval;

    public ApprovalEvent(Object source, Approval approval) {
        super(source);
        this.approval = approval;
    }

    public Approval getApproval() {
        return approval;
    }
}
