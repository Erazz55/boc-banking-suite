package com.icptechno.admincore.approval.dto;

import com.icptechno.admincore.approval.Approval;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ApprovalConverter {

    final ModelMapper modelMapper;
    final ObjectMapper objectMapper;

    public ApprovalConverter(ModelMapper modelMapper, ObjectMapper objectMapper) {
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }

    public Approval convertToEntity(ApprovalDto approvalDto) {
        return modelMapper.map(approvalDto, Approval.class);
    }

    public Approval convertToEntity(ApprovalUpdateDao approvalUpdateDao) {
        return modelMapper.map(approvalUpdateDao, Approval.class);
    }

    public ApprovalDto convertToDto(Approval approval) {
        ApprovalDto approvalDto = modelMapper.map(approval, ApprovalDto.class);

        try {
            Map<String, Object> infoMap = objectMapper.readValue(approval.getInfo(), HashMap.class);
            approvalDto.setInfo(infoMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return approvalDto;
    }

}
