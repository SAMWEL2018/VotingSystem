package com.example.votingsystem.model;

import lombok.Data;

/**
 * @author samwel.wafula
 * Created on 09/01/2024
 * Time 10:54
 * Project VotingSystem
 */
@Data
public class LoginReq {

    private String voterIdNumber;
    private String password;
}
