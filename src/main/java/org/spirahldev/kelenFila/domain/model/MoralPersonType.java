package org.spirahldev.kelenFila.domain.model;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "moral_person_type")
public class MoralPersonType extends PanacheEntity {
    @Column(length = 45)
    private String name;

    @Column(length = 45)
    private String code;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}