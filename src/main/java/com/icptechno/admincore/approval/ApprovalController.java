package com.icptechno.admincore.approval;

import com.icptechno.admincore.approval.dto.ApprovalConverter;
import com.icptechno.admincore.approval.dto.ApprovalDto;
import com.icptechno.admincore.approval.dto.ApprovalUpdateDao;
import com.icptechno.admincore.common.ApprovalStatus;
import com.icptechno.admincore.common.ResponseBuilder;
import com.icptechno.admincore.common.ResponseHolder;
import com.icptechno.admincore.common.security.AppUserDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/approval")
public class ApprovalController {

    private final ApprovalConverter approvalConverter;
    private final ApprovalService approvalService;

    public ApprovalController(ApprovalConverter approvalConverter, ApprovalService approvalService) {
        this.approvalConverter = approvalConverter;
        this.approvalService = approvalService;
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseHolder<List<ApprovalDto>>> getAllApprovals() {
        List<ApprovalDto> dtoList = approvalService.getAll().stream().map(approvalConverter::convertToDto).collect(Collectors.toList());
        return ResponseBuilder.okResponseBuilder(dtoList).build();
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<ResponseHolder<Object>> approveById(@PathVariable Long id, @RequestBody ApprovalUpdateDao updateDao) {
        String remark = updateDao.getRemark();
        AppUserDetail userDetail = (AppUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        approvalService.updateApprovalStatusById(id, userDetail.getUsername(), remark, ApprovalStatus.APPROVED);
        return ResponseBuilder.okResponseBuilder(null).build();
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<ResponseHolder<Object>> rejectById(@PathVariable Long id, @RequestBody ApprovalUpdateDao updateDao) {
        String remark = updateDao.getRemark();
        AppUserDetail userDetail = (AppUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        approvalService.updateApprovalStatusById(id, userDetail.getUsername(), remark, ApprovalStatus.REJECTED);
        return ResponseBuilder.okResponseBuilder(null).build();
    }

}
