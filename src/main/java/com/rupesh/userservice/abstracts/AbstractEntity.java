package com.rupesh.userservice.abstracts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractEntity<PK extends Serializable> {

    private static final Long serialVersionUID = 1234567890876543456L;

    @Basic
    @CreatedDate
    @Column(updatable = false, name = "created_date")
    private LocalDateTime createdDate;

    @Basic
    @LastModifiedDate
    @Column(updatable = false, name = "modified_date")
    private LocalDateTime modifiedDate;

    @Basic
    @CreatedBy
    @Column(updatable = false, name = "created_by")
    private Long createdBy;

    @Basic
    @LastModifiedBy
    @Column(updatable = false, name = "modified_by")
    private Long modifiedBy;

}
