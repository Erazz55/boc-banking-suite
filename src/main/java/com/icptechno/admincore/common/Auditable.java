package com.icptechno.admincore.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable extends CreateOnlyAuditable {

    @LastModifiedBy
    @Column(name = "modified_by")
    private String modifiedBy;

    @LastModifiedDate
    @Column(name = "modified_at")
    private Date modifiedAt;

    public Auditable(String modifiedBy, Date modifiedAt, String createdBy, Date createdAt) {
        super(createdBy, createdAt);
        this.modifiedBy = modifiedBy;
        this.modifiedAt = modifiedAt;
    }

    public Auditable() {
    }
}
