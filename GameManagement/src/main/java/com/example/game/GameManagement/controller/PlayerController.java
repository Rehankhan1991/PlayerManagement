package com.example.game.GameManagement.controller;

import java.text.ParseException;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.game.GameManagement.model.PlayerHistory;
import com.example.game.GameManagement.model.PlayerPage;
import com.example.game.GameManagement.model.PlayerSearchCriteria;
import com.example.game.GameManagement.model.Players;
import com.example.game.GameManagement.responses.GeneralResponse;
import com.example.game.GameManagement.responses.PlayerResponse;
import com.example.game.GameManagement.responses.Status;
import com.example.game.GameManagement.service.PlayerService;

@RestController
@RequestMapping("/players")
class PlayersController {

	private final PlayerService playerService;

	PlayersController(PlayerService playerService) {
		this.playerService = playerService;
	}

	@PostMapping("/createScore")
	ResponseEntity<GeneralResponse> createPlayers(@RequestBody Players newPlayers) throws ParseException {
		if (null == newPlayers || null == newPlayers.getPlayer() || ("").equals(newPlayers.getPlayer())
				|| null == newPlayers.getScore() || 0 > newPlayers.getScore()) {
			Status status = new Status(Status.EMPTY_REQUEST);
			GeneralResponse response = new GeneralResponse(status);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		Players player = playerService.storePlayerScore(newPlayers);

		if (null == player) {
			Status status = new Status(Status.DATABASE_ERROR);
			GeneralResponse response = new GeneralResponse(status);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new PlayerResponse(player.getId(), player.getPlayer(), player.getScore(), player.getTime()));
	}

	@GetMapping("/getScore/{id}")
	Players getScore(@PathVariable(required = true, name = "id") Long id) {
		return playerService.findByID(id);
	}

	@DeleteMapping("/delete/{id}")
	ResponseEntity<GeneralResponse> deletePlayers(@PathVariable(required = true, name = "id") Long id) {
		Status status = new Status(Status.NO_PLAYER);
		GeneralResponse response = new GeneralResponse(status);
		Players player = playerService.findByID(id);
		playerService.deleteById(player.getId());
		status = new Status(Status.OK);
		response = new GeneralResponse(status);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/getListOfScores")
	ResponseEntity<Page<Players>> getListOfScores(PlayerPage playersPage, PlayerSearchCriteria playerSearchCriteria) {
		return ResponseEntity.status(HttpStatus.OK).body(playerService.searchPlayerDetails(playersPage, playerSearchCriteria));
	}

	@GetMapping("/getPlayerHistory/{player}")
	ResponseEntity<PlayerHistory> getPlayerHistory(@PathVariable(required = true, name = "player") String player) throws ParseException {
		return new ResponseEntity<PlayerHistory>(playerService.playerHistoryDetails(player), HttpStatus.OK);
	}

	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
		return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}