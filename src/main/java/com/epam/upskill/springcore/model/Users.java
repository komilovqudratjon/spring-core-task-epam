package com.epam.upskill.springcore.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @description: Entity class for TrainingType.
 * @date: 08 November 2023 $
 * @time: 5:17 AM 26 $
 * @author: Qudratjon Komilov
 */

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users extends AbsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "address")
    private String address;

    @OneToOne(mappedBy = "user",  optional = true)
    private Trainee trainee;

    @OneToOne(mappedBy = "user",  optional = true)
    private Trainer trainer;

    @Enumerated(EnumType.STRING)
    private RoleName role = RoleName.ROLE_USER;
}
