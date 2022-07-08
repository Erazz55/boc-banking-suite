package com.icptechno.admincore.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class AbstractApproval extends CreateOnlyAuditable {

    @Enumerated(value = EnumType.STRING)
    private ApprovalStatus status;
    private String remark;
    private String approvedBy;
    private Date approvedAt;

    public AbstractApproval(ApprovalStatus status, String remark, String approvedBy, Date approvedAt, String createdBy, Date createdAt) {
        super(createdBy, createdAt);
        this.status = status;
        this.remark = remark;
        this.approvedBy = approvedBy;
        this.approvedAt = approvedAt;
    }

    public AbstractApproval() {
        super();
    }
}
