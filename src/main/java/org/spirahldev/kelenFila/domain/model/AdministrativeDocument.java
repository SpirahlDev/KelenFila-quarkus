package org.spirahldev.kelenFila.domain.model;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrative_document")
public class AdministrativeDocument extends PanacheEntity {
    @OneToOne
    @JoinColumn(name = "administrative_doc_type")
    private AdministrativeDocumentType administrativeDocType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}