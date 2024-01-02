package com.epam.upskill.springcore.model.dtos;

import lombok.*;

/**
 * @description: TODO
 * @projectName koinot_market
 * @date: 15 February 2022 $
 * @time: 22:59 $
 * @author: Qudratjon Komilov
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JwtToken {
    private String token;
}
