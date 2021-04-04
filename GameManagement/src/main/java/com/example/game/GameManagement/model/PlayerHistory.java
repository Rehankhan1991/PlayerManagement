package com.example.game.GameManagement.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PlayerHistory {

	private List<Players> playerHistoryList;
	private Double avgScore;
	private Double minScore;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Tokyo")
	private Date minScoreDate;
	private Double maxScore;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Tokyo")
	private Date maxScoreDate;
	
	public Date getMinScoreDate() {
		return minScoreDate;
	}
	public void setMinScoreDate(Date minScoreDate) {
		this.minScoreDate = minScoreDate;
	}
	public Date getMaxScoreDate() {
		return maxScoreDate;
	}
	public void setMaxScoreDate(Date maxScoreDate) {
		this.maxScoreDate = maxScoreDate;
	}
	public List<Players> getPlayerHistoryList() {
		return playerHistoryList;
	}
	public void setPlayerHistoryList(List<Players> list) {
		this.playerHistoryList = list;
	}
	public Double getAvgScore() {
		return avgScore;
	}
	public void setAvgScore(Double Double) {
		this.avgScore = Double;
	}
	public Double getMinScore() {
		return minScore;
	}
	public void setMinScore(Double minScore) {
		this.minScore = minScore;
	}
	public Double getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(Double maxScore) {
		this.maxScore = maxScore;
	}

}
