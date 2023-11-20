package com.epam.upskill.springcore.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * @description: Entity class for Trainer.
 * @date: 08 November 2023 $
 * @time: 5:18 AM 34 $
 * @author: Qudratjon Komilov
 */

@Entity
@Table(name = "trainer", uniqueConstraints = {@UniqueConstraint(columnNames = {"specialization_id", "user_id"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trainer extends AbsEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "specialization_id", nullable = false)
    private Specialization specialization;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "trainer_trainee",
            joinColumns = { @JoinColumn(name = "trainer_id") },
            inverseJoinColumns = { @JoinColumn(name = "trainee_id") }
    )
    private Set<Trainee> trainees = new HashSet<>();

    @Column(name = "is_active")
    private Boolean isActive=true;
}
