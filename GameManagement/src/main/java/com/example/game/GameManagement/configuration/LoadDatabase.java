package com.example.game.GameManagement.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.game.GameManagement.model.Players;
import com.example.game.GameManagement.repository.PlayerRepository;
import com.example.game.GameManagement.service.PlayerService;

@Configuration
class LoadDatabase {
	
	@Autowired
	private PlayerService noMockService;

	@Autowired
	PlayerRepository crudRepository;
	
	@Bean
	CommandLineRunner initDatabase(PlayerService repository) {

		return args -> {

//			List<Long> idsList = new ArrayList<Long>();
//
//			idsList.add(noMockService.storePlayerScore(new Players("sachin", 100d)).getId());
//			idsList.add(noMockService.storePlayerScore(new Players("virat", 90d)).getId());
//			idsList.add(noMockService.storePlayerScore(new Players("sachin", 50d)).getId());
//			idsList.add(noMockService.storePlayerScore(new Players("virat", 60d)).getId());
//			idsList.add(noMockService.storePlayerScore(new Players("sachin", 80d)).getId());
//			idsList.add(noMockService.storePlayerScore(new Players("virat", 50d)).getId());
//			idsList.add(noMockService.storePlayerScore(new Players("sachin", 60d)).getId());
//			idsList.add(noMockService.storePlayerScore(new Players("virat", 90d)).getId());
//			idsList.add(noMockService.storePlayerScore(new Players("sachin", 200d)).getId());
//			idsList.add(noMockService.storePlayerScore(new Players("virat", 23d)).getId());
//			
//			System.out.println(crudRepository.playerAllScores("virat"));
			

		};
	}
}