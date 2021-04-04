package com.example.game.GameManagement.responses;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PlayerResponse  extends GeneralResponse {

	private Long id;
	private String player ;
	private Double score;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Tokyo")
	private Date time;
	
	public PlayerResponse() {
	}

	public PlayerResponse(Long id,String player,Double score, Date time) {
		this.id = id;
		this.player = player;
		this.score = score;
		this.time = time;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	
	
}
