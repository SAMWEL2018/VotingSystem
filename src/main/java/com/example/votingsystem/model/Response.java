package com.example.votingsystem.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author samwel.wafula
 * Created on 04/01/2024
 * Time 12:45
 * Project VotingSystem
 */
@Getter
@Setter
@Builder
public class Response {
    private String responseCode;
    private String responseDesc;
    private Object data;
}
