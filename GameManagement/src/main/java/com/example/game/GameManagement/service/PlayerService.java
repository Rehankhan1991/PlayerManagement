package com.example.game.GameManagement.service;

import java.text.ParseException;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.example.game.GameManagement.exceptions.PlayersNotFoundException;
import com.example.game.GameManagement.model.PlayerHistory;
import com.example.game.GameManagement.model.PlayerPage;
import com.example.game.GameManagement.model.PlayerSearchCriteria;
import com.example.game.GameManagement.model.Players;
import com.example.game.GameManagement.repository.PlayerCriteriaRepository;
import com.example.game.GameManagement.repository.PlayerRepository;
import com.example.game.GameManagement.responses.Status;

@Component
public class PlayerService {

	private final PlayerRepository crudRepository;
	private final PlayerCriteriaRepository criteriaRepository;

	PlayerService(PlayerRepository crudRepository, PlayerCriteriaRepository criteriaRepository) {
		this.crudRepository = crudRepository;
		this.criteriaRepository = criteriaRepository;
	}

	public Players findByID(Long id) {
		return crudRepository.findById(id).orElseThrow(() -> new PlayersNotFoundException(new Status(Status.NO_PLAYER)));
	}

	public void deleteById(Long id) {
		crudRepository.deleteById(id);
	}

	public Players storePlayerScore(Players newPlayers) {
		newPlayers.setTime(new Date());
		newPlayers.setPlayer(newPlayers.getPlayer().toLowerCase());
		return crudRepository.save(newPlayers);
	}

	public Page<Players> searchPlayerDetails(PlayerPage playerPage, PlayerSearchCriteria playerSearchCriteria) {

		return criteriaRepository.findAllWithFilter(playerPage, playerSearchCriteria);

	}

	public PlayerHistory playerHistoryDetails(String player) throws ParseException {

		player = player.toLowerCase();
		PlayerHistory playerHistory = new PlayerHistory();
		playerHistory.setAvgScore(crudRepository.avg(player));
		
		Players p1 = crudRepository.min(player);
		playerHistory.setMinScore(p1.getScore());
		playerHistory.setMinScoreDate(p1.getTime());
		
		p1 = null;
		
		p1 = crudRepository.max(player);
		playerHistory.setMaxScore(p1.getScore());
		playerHistory.setMaxScoreDate(p1.getTime());
		playerHistory.setPlayerHistoryList(crudRepository.playerAllScores(player));
		
		return playerHistory;

	}

}
