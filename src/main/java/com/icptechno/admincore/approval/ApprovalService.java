package com.icptechno.admincore.approval;

import com.icptechno.admincore.approval.events.ApprovalEvent;
import com.icptechno.admincore.common.ApprovalStatus;
import com.icptechno.admincore.user.ApplicationUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ApprovalService {

    private final ApplicationEventPublisher applicationEventPublisher;

    private final ApprovalRepository approvalRepository;

    private final ObjectMapper objectMapper;

    public ApprovalService(ApplicationEventPublisher applicationEventPublisher, ApprovalRepository approvalRepository, ObjectMapper objectMapper) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.approvalRepository = approvalRepository;
        this.objectMapper = objectMapper;
    }

    public List<Approval> getAll() {
        return approvalRepository.findAll();
    }

    public Approval createUserApproval(ApplicationUser user) {
        Approval approval = new Approval();
        approval.setRefId(user.getId());
        approval.setType(ApprovalType.USER);
        approval.setInfo(infoToString(user));
        return approvalRepository.save(approval);
    }

    public void updateApprovalStatusById(Long id, String approvedBy, String remark, ApprovalStatus approvalStatus) {
        Approval approval = approvalRepository.findById(id).orElseThrow();
        approval.setApprovedBy(approvedBy);
        approval.setApprovedAt(new Date());
        approval.setRemark(remark);
        approval.setStatus(approvalStatus);
        approvalRepository.save(approval);


        // emit user approval event
        ApprovalEvent event = new ApprovalEvent(this, approval);
        applicationEventPublisher.publishEvent(event);
    }

    public <T> T infoToObject(String json, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String infoToString(Object infoObject) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(infoObject);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

}
