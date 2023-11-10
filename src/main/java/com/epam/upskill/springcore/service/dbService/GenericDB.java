package com.epam.upskill.springcore.service.dbService;

import com.epam.upskill.springcore.model.DTOs.TraineeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @className: GenericDAO  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 5:21 PM 15 $
 * @author: Qudratjon Komilov
 */


public interface GenericDB<T, ID> {
    T save( T entity);

    Optional<T> findById(ID id);

    void deleteById(ID id);

    List<T> findAll();
}

