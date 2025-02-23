package org.spirahldev.kelenFila.domain.model;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrative_document_type")
public class AdministrativeDocumentType extends PanacheEntity {
    @Column(length = 200)
    private String name;

    @Column(length = 200)
    private String code;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
