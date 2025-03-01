package org.spirahldev.kelenFila.domain.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "participant")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "auction_id")
    private Long auctionId;

    @OneToOne
    @JoinColumn(name = "participant_role_id",nullable = false)
    private ParticipantRole participantRole;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @Column(name = "has_join_auction")
    private Boolean hasJoinAuction;

    @Column(name = "has_solved_notification")
    private Boolean hasSolvedNotification;

    @Column(name = "deleted_at",nullable = true)
    private LocalDateTime deleteAt;
    // Getters et Setters
}
