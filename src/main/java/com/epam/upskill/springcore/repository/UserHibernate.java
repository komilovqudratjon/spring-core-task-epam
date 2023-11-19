package com.epam.upskill.springcore.repository;

import com.epam.upskill.springcore.model.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @description: Repository interface for Users entity.
 * @date: 08 November 2023 $
 * @time: 5:37 AM 10 $
 * @author: Qudratjon Komilov
 */


@Service

@Slf4j
public class UserHibernate extends CrudRepository<Users, Long> {


    public UserHibernate() {
        super(Users.class);
    }

    public boolean existsByUsername(String username) {
        return false;
    }


}
