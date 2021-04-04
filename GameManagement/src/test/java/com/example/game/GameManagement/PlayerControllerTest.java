/**
 * 
 */
package com.example.game.GameManagement;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.game.GameManagement.exceptions.PlayersNotFoundException;
import com.example.game.GameManagement.model.PlayerHistory;
import com.example.game.GameManagement.model.Players;
import com.example.game.GameManagement.responses.PlayerResponse;
import com.example.game.GameManagement.responses.Status;
import com.example.game.GameManagement.service.PlayerService;
import com.example.game.GameManagement.testutil.playersPaginatedResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author prreh
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerTest {
	@MockBean
	private PlayerService service;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("GET /getListOfScores success")
	void testGetPlayersWithPaginationSuccess() throws Exception {
		// Setup our mocked service

		Players playerToReturn = new Players("Sachin", 100d);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		playerToReturn.setId(1L);
		playerToReturn.setTime(formatter.parse("2020-03-20 16:49:53"));

		Players playerToReturn1 = new Players("Virat", 90d);

		playerToReturn1.setId(2L);
		playerToReturn1.setTime(formatter.parse("2020-03-01 16:49:53"));

		Players playerToReturn2 = new Players("Sourav", 95d);

		playerToReturn2.setId(3L);
		playerToReturn2.setTime(formatter.parse("2020-04-04 16:49:53"));

		List<Players> players = new ArrayList<Players>();
		players.add(playerToReturn);
		players.add(playerToReturn1);
		players.add(playerToReturn2);

		Sort.Direction sortdir = Sort.Direction.ASC;
		String sortBy = "player";

		Sort sort = Sort.by(sortdir, sortBy);
		Page<Players> page = new PageImpl<Players>(players, PageRequest.of(0, 10, sort), 3L);

		doReturn(page).when(service).searchPlayerDetails(any(), any());

		// Execute the GET request
		MvcResult result = mockMvc.perform(get("/players/getListOfScores"))
				// Validate the response code and content type
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))

				// Validate the returned fields
				.andExpect(jsonPath("$.content", hasSize(3)))
				.andExpect(jsonPath("$.content[0].id", is(playerToReturn.getId().intValue())))
				.andExpect(jsonPath("$.content[0].player", is(playerToReturn.getPlayer())))
				.andExpect(jsonPath("$.content[0].score", is(playerToReturn.getScore())))
				.andExpect(jsonPath("$.content[1].id", is(playerToReturn1.getId().intValue())))
				.andExpect(jsonPath("$.content[1].player", is(playerToReturn1.getPlayer())))
				.andExpect(jsonPath("$.content[1].score", is(playerToReturn1.getScore())))
				.andExpect(jsonPath("$.content[2].id", is(playerToReturn2.getId().intValue())))
				.andExpect(jsonPath("$.content[2].player", is(playerToReturn2.getPlayer())))
				.andExpect(jsonPath("$.content[2].score", is(playerToReturn2.getScore()))).andReturn();

		String resultJson = result.getResponse().getContentAsString();
		playersPaginatedResponse response = new ObjectMapper().readValue(resultJson, playersPaginatedResponse.class);

		Date[] comparableDates = dateCompareable(playerToReturn.getTime(), response.getContent().get(0).getTime());

		Assertions.assertTrue(comparableDates[0].compareTo(comparableDates[1]) >= 0);

		comparableDates = dateCompareable(playerToReturn1.getTime(), response.getContent().get(1).getTime());

		Assertions.assertTrue(comparableDates[0].compareTo(comparableDates[1]) >= 0);

		comparableDates = dateCompareable(playerToReturn2.getTime(), response.getContent().get(2).getTime());

		Assertions.assertTrue(comparableDates[0].compareTo(comparableDates[1]) >= 0);

	}

	@Test
	@DisplayName("GET /getPlayerHistory/{player} success")
	void testGetPlayerHistorySuccess() throws Exception {
		// Setup our mocked service

		String json1 = "{\n    \"playerHistoryList\": [\n        {\n            \"id\": 2,\n            \"player\": \"virat\",\n            \"score\": 90.0,\n            \"time\": \"2021-04-05 02:21:59\"\n        },\n        {\n            \"id\": 4,\n            \"player\": \"virat\",\n            \"score\": 60.0,\n            \"time\": \"2021-04-05 02:21:59\"\n        },\n        {\n            \"id\": 6,\n            \"player\": \"virat\",\n            \"score\": 50.0,\n            \"time\": \"2021-04-05 02:21:59\"\n        },\n        {\n            \"id\": 8,\n            \"player\": \"virat\",\n            \"score\": 90.0,\n            \"time\": \"2021-04-05 02:21:59\"\n        },\n        {\n            \"id\": 10,\n            \"player\": \"virat\",\n            \"score\": 23.0,\n            \"time\": \"2021-04-05 02:21:59\"\n        }\n    ],\n    \"avgScore\": 62.6,\n    \"minScore\": 23.0,\n    \"minScoreDate\": \"2021-04-05 02:21:59\",\n    \"maxScore\": 90.0,\n    \"maxScoreDate\": \"2021-04-05 02:21:59\"\n}";
		PlayerHistory response1 = new ObjectMapper().readValue(json1, PlayerHistory.class);

		String json2 = "{\n    \"playerHistoryList\": [\n        {\n            \"id\": 1,\n            \"player\": \"sachin\",\n            \"score\": 100.0,\n            \"time\": \"2021-04-05 02:42:10\"\n        },\n        {\n            \"id\": 3,\n            \"player\": \"sachin\",\n            \"score\": 50.0,\n            \"time\": \"2021-04-05 02:42:10\"\n        },\n        {\n            \"id\": 5,\n            \"player\": \"sachin\",\n            \"score\": 80.0,\n            \"time\": \"2021-04-05 02:42:10\"\n        },\n        {\n            \"id\": 7,\n            \"player\": \"sachin\",\n            \"score\": 60.0,\n            \"time\": \"2021-04-05 02:42:10\"\n        },\n        {\n            \"id\": 9,\n            \"player\": \"sachin\",\n            \"score\": 200.0,\n            \"time\": \"2021-04-05 02:42:10\"\n        }\n    ],\n    \"avgScore\": 98.0,\n    \"minScore\": 50.0,\n    \"minScoreDate\": \"2021-04-05 02:42:10\",\n    \"maxScore\": 200.0,\n    \"maxScoreDate\": \"2021-04-05 02:42:10\"\n}";
		PlayerHistory response2 = new ObjectMapper().readValue(json2, PlayerHistory.class);

		doReturn(response1).when(service).playerHistoryDetails("virat");
		doReturn(response2).when(service).playerHistoryDetails("sachin");
//
//		// Execute the GET request
		MvcResult result1 = mockMvc.perform(get("/players/getPlayerHistory/{player}", "virat"))
				// Validate the response code and content type
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
//				// Validate the returned fields
				.andExpect(jsonPath("$.avgScore", is(response1.getAvgScore().doubleValue())))
				.andExpect(jsonPath("$.minScore", is(response1.getMinScore().doubleValue())))
				.andExpect(jsonPath("$.maxScore", is(response1.getMaxScore().doubleValue())))
				.andExpect(jsonPath("$.playerHistoryList", hasSize(response1.getPlayerHistoryList().size()))).andReturn();

		String resultJson1 = result1.getResponse().getContentAsString();
		PlayerHistory response3 = new ObjectMapper().readValue(resultJson1, PlayerHistory.class);

		Date[] comparableDates = dateCompareable(response1.getMaxScoreDate(), response3.getMaxScoreDate());

		Assertions.assertTrue(comparableDates[0].compareTo(comparableDates[1]) == 0);

		comparableDates = dateCompareable(response1.getMinScoreDate(), response3.getMinScoreDate());

		Assertions.assertTrue(comparableDates[0].compareTo(comparableDates[1]) == 0);

		MvcResult result2 = mockMvc.perform(get("/players/getPlayerHistory/{player}", "sachin"))
				// Validate the response code and content type
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
//				// Validate the returned fields
				.andExpect(jsonPath("$.avgScore", is(response2.getAvgScore().doubleValue())))
				.andExpect(jsonPath("$.minScore", is(response2.getMinScore().doubleValue())))
				.andExpect(jsonPath("$.maxScore", is(response2.getMaxScore().doubleValue())))
				.andExpect(jsonPath("$.playerHistoryList", hasSize(response2.getPlayerHistoryList().size()))).andReturn();
		
		String resultJson2 = result2.getResponse().getContentAsString();
		PlayerHistory response4 = new ObjectMapper().readValue(resultJson2, PlayerHistory.class);

		comparableDates = dateCompareable(response2.getMaxScoreDate(), response4.getMaxScoreDate());

		Assertions.assertTrue(comparableDates[0].compareTo(comparableDates[1]) == 0);

		comparableDates = dateCompareable(response2.getMinScoreDate(), response4.getMinScoreDate());

		Assertions.assertTrue(comparableDates[0].compareTo(comparableDates[1]) == 0);

	}


	@Test
	@DisplayName("POST /createScore")
	void testCreatePlayer() throws Exception {
		// Setup our mocked service

		Players playerToReturn = new Players("Sachin", 100d);
		Date date = new Date();

		playerToReturn.setId((long) 1);
		playerToReturn.setTime(date);

		doReturn(playerToReturn).when(service).storePlayerScore(any());

		Players playerToPost = new Players("Sachin", 100d);

		// Execute the POST request
		MvcResult result = mockMvc
				.perform(post("/players/createScore").contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(playerToPost)))

				// Validate the response code and content type
				.andExpect(status().is(200)).andExpect(content().contentType(MediaType.APPLICATION_JSON))

				// Validate the returned fields
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.player", is("Sachin")))
				.andExpect(jsonPath("$.score", is(100d))).andReturn();

		String resultJson = result.getResponse().getContentAsString();
		PlayerResponse response = new ObjectMapper().readValue(resultJson, PlayerResponse.class);
		Date first = date;
		Date second = response.getTime();

		Date[] comparableDates = dateCompareable(first, second);

		Assertions.assertTrue(comparableDates[0].compareTo(comparableDates[1]) >= 0);

	}

	@Test
	@DisplayName("GET /getScore")
	void testGetScore() throws Exception {
		// Setup our mocked service
		Players playerToReturn = new Players("Sachin", 100d);
		Date date = new Date();

		playerToReturn.setId((long) 1);
		playerToReturn.setTime(date);

		doReturn(playerToReturn).when(service).findByID((long) 1);

		// Execute the Get request
		MvcResult result = mockMvc.perform(get("/players/getScore/{id}", 1L))

				// Validate the response code and content type
				.andExpect(status().is(200)).andExpect(content().contentType(MediaType.APPLICATION_JSON))

				// Validate the returned fields
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.player", is("Sachin")))
				.andExpect(jsonPath("$.score", is(100d))).andReturn();

		String resultJson = result.getResponse().getContentAsString();
		PlayerResponse response = new ObjectMapper().readValue(resultJson, PlayerResponse.class);
		Date first = date;
		Date second = response.getTime();

		Date[] comparableDates = dateCompareable(first, second);

		Assertions.assertTrue(comparableDates[0].compareTo(comparableDates[1]) >= 0);
	}

	@Test
	@DisplayName("GET /getScoreAssertPlayerNotFoundException")
	void testGetScoreAssertThrowPlayerNotFound() throws Exception {
		// Setup our mocked service

		Status status = new Status(Status.NO_PLAYER);
		when(service.findByID(any())).thenThrow(new PlayersNotFoundException(status));

		// Execute the Delete request
		mockMvc.perform(get("/players/getScore/{id}", 1L))

				// Validate the response code and content type
				.andExpect(status().is(500)).andExpect(content().contentType(MediaType.APPLICATION_JSON))

				// Validate the returned fields
				.andExpect(jsonPath("$.message", is("Code=10004, Message=No player found for this ID")));

	}

	@Test
	@DisplayName("DELETE /DeletePlayers")
	void testDeletePlayers() throws Exception {
		// Setup our mocked service
		Players playerToReturn = new Players("Sachin", 100d);
		Date date = new Date();

		playerToReturn.setId((long) 1);
		playerToReturn.setTime(date);

		doReturn(playerToReturn).when(service).findByID(any());
		doAnswer((i) -> {
			return null;
		}).when(service).deleteById(any());

		// Execute the Delete request
		mockMvc.perform(delete("/players/delete/{id}", 1L))

				// Validate the response code and content type
				.andExpect(status().is(200)).andExpect(content().contentType(MediaType.APPLICATION_JSON))

				// Validate the returned fields
				.andExpect(jsonPath("$.status", is(200))).andExpect(jsonPath("$.statusMessage", is("OK")));
	}


	static Date[] dateCompareable(Date first, Date second) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return new Date[] { formatter.parse(formatter.format(first)), formatter.parse(formatter.format(second)) };

	}

	static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
//}
