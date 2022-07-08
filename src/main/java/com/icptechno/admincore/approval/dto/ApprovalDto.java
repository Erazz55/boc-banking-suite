package com.icptechno.admincore.approval.dto;

import com.icptechno.admincore.approval.ApprovalType;
import com.icptechno.admincore.common.ApprovalStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class ApprovalDto {
    private Long id;
    private ApprovalType type;
    private Long refId;
    private Map<String, Object> info;
    private String remark;
    private ApprovalStatus status;
    private String approvedBy;
    private String approvedAt;
}
