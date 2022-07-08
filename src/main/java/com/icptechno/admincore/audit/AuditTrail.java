package com.icptechno.admincore.audit;

import com.icptechno.admincore.common.CreateOnlyAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "um_audit_trial")
public class AuditTrail extends CreateOnlyAuditable {

    @Id
    @Column(name = "trial_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private AuditType auditType;

    private String action;

    public AuditTrail(Long id, AuditType auditType, String action, String createdBy, Date createdAt) {
        super(createdBy, createdAt);
        this.id = id;
        this.auditType = auditType;
        this.action = action;
    }

    public AuditTrail() {
    }
}
