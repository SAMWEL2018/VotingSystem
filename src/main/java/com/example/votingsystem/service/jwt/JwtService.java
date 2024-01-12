package com.example.votingsystem.service.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @author samwel.wafula
 * Created on 09/01/2024
 * Time 11:48
 * Project VotingSystem
 */

public interface JwtService {

    String generateToken(UserDetails userDetails);
    }


