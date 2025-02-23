package org.spirahldev.kelenFila.domain.model;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrative_document")
public class AdministrativeDocument extends PanacheEntity {
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "administrative_doc_type")
    private Integer administrativeDocType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}