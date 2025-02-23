package org.spirahldev.kelenFila.domain.model;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "moral_person")
public class MoralPerson extends PanacheEntity {
    @Column(length = 200)
    private String name;

    @Column(length = 200)
    private String city;

    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "moral_person_type_id")
    private Integer moralPersonTypeId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "account_id")
    private Long accountId;
}