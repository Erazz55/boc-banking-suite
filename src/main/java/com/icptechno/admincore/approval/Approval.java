package com.icptechno.admincore.approval;

import com.icptechno.admincore.common.ApprovalStatus;
import com.icptechno.admincore.common.CreateOnlyAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sys_approval")
public class Approval extends CreateOnlyAuditable {

    @Id
    @Column(name = "approval_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ApprovalType type;

    private Long refId;

    @Lob
    private String info;

    private String remark;

    @Enumerated(value = EnumType.STRING)
    private ApprovalStatus status = ApprovalStatus.PENDING;

    private String approvedBy;

    private Date approvedAt;

}
