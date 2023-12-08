package com.epam.upskill.springcore.model.dtos;

import lombok.*;

import java.util.List;

/**
 * @description: TODO
 * @date: 19 November 2023 $
 * @time: 12:58 AM 45 $
 * @author: Qudratjon Komilov
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageGeneral<T> {
    private List<T> content;
    private int number;
    private int size;
    private long totalElements;
}
