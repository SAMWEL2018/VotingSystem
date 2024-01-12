package com.example.votingsystem.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author samwel.wafula
 * Created on 09/01/2024
 * Time 09:52
 * Project VotingSystem
 */

@Data
@Builder
public class JwtTokenResponse {
    private String token;
}
