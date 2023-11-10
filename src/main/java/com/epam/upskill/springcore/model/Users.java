package com.epam.upskill.springcore.model;

import lombok.*;

import javax.persistence.*;

/**
 * @className: Users  $
 * @description: TODO
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
public class Users {

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

}