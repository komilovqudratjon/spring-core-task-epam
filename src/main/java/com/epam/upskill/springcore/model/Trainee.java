package com.epam.upskill.springcore.model;

/**
 * @description: Entity class for Trainee.
 * @date: 08 November 2023 $
 * @time: 5:22 AM 05 $
 * @author: Qudratjon Komilov
 */

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "trainee", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id"}),
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trainee extends AbsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToMany(mappedBy = "trainees", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Set<Trainer> trainers = new HashSet<>();

    @Column(name = "is_active")
    private Boolean isActive = true;
}
