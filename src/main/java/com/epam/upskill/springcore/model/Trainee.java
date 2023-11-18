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

@Entity
@Table(name = "trainee",uniqueConstraints = {@UniqueConstraint(columnNames = {"address","user_id","date_of_birth"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trainee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "address")
    private String address;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
}
