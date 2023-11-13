package com.epam.upskill.springcore.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @description: Entity class for Training.
 * @date: 08 November 2023 $
 * @time: 5:20 AM 58 $
 * @author: Qudratjon Komilov
 */

@Entity
@Table(name = "training", uniqueConstraints = {@UniqueConstraint(columnNames = {"trainee_id", "trainer_id", "training_name", "training_type_id", "training_date"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainee_id", nullable = false)
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @Column(name = "training_name", nullable = false)
    private String trainingName;

    @ManyToOne
    @JoinColumn(name = "training_type_id", nullable = false)
    private TrainingType trainingType;

    @Column(name = "training_date", nullable = false)
    private Date trainingDate;

    @Column(name = "training_duration", nullable = false)
    private Integer trainingDuration;

}
