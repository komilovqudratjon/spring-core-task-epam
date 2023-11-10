package com.epam.upskill.springcore.model;

import lombok.*;

import javax.persistence.*;

/**
 * @className: TrainingType  $
 * @description: TODO
 * @date: 08 November 2023 $
 * @time: 5:23 AM 33 $
 * @author: Qudratjon Komilov
 */

@Entity
@Table(name = "training_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainingType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "training_type_name", nullable = false)
    private String trainingTypeName;

}

