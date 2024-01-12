package com.example.votingsystem;

import com.example.votingsystem.model.VoteCount;
import com.example.votingsystem.service.VoterServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class VotingSystemApplicationTests {

	private final VoterServiceImpl voterService;

	@Autowired
	public VotingSystemApplicationTests(VoterServiceImpl voterService) {
		this.voterService = voterService;
	}

	@Test
	void contextLoads() {
	}
	@Test
	public void getVotes() throws JsonProcessingException {
		//VoteCount voteCount=voterService.getCastedVotes();
		log.info("VoteCount:{}",new ObjectMapper().writeValueAsString(voterService.getCastedVotes()));

	}

}
