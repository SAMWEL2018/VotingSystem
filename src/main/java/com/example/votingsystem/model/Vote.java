package com.example.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

/**
 * @author samwel.wafula
 * Created on 04/01/2024
 * Time 12:01
 * Project VotingSystem
 */

@Entity
@Table(name = "votes")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "candidateId")
    private int candidateId;

    @Column(name = "voterIdNumber")
    private String voterIdNumber;
}
