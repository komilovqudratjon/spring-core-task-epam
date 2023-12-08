package com.epam.upskill.springcore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @description: Entity class for TrainingType.
 * @date: 08 November 2023 $
 * @time: 5:23 AM 33 $
 * @author: Qudratjon Komilov
 */

@Entity
@Table(name = "training_type")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainingType extends AbsEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "training_type_name", nullable = false, unique = true)
    private String trainingTypeName;

//    @OneToMany(mappedBy = "trainingType")
//    private Set<Training> trainings = new HashSet<>();


}

