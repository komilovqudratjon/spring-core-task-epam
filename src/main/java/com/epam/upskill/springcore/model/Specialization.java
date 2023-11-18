package com.epam.upskill.springcore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @description: Entity class for Specialization.
 * @date: 08 November 2023 $
 * @time: 5:24 AM 28 $
 * @author: Qudratjon Komilov
 */

@Entity
@Table(name = "specialization")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "specialization_name", nullable = false, unique = true)
    private String specializationName;

}

