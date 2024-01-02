package com.epam.upskill.springcore.model.dtos;

import com.epam.upskill.springcore.model.RoleName;
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
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private String sessionId;
    private String username;
    private String lastName;
    private String firstName;
    private RoleName role;
}
