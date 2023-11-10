package com.epam.upskill.springcore.model;

import lombok.*;

import javax.persistence.*;


/**
 * @className: Trainee  $
 * @description: TODO
 * @date: 08 November 2023 $
 * @time: 5:18 AM 34 $
 * @author: Qudratjon Komilov
 */

@Entity
@Table(name = "trainer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "specialization_id", nullable = false)
    private Specialization specialization;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
}