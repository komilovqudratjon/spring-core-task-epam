package com.epam.upskill.springcore.repository;

import com.epam.upskill.springcore.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @className: UserRepository  $
 * @description: TODO
 * @date: 08 November 2023 $
 * @time: 5:37 AM 10 $
 * @author: Qudratjon Komilov
 */


@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    // Custom methods can be defined here if needed
}
