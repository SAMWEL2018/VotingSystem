package com.example.votingsystem.model;

import lombok.*;

/**
 * @author samwel.wafula
 * Created on 10/01/2024
 * Time 11:39
 * Project VotingSystem
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VoteCount {

    private int candidate_id;
    private int count;
}
