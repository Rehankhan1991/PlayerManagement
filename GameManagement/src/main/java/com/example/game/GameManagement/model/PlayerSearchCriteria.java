package com.example.game.GameManagement.model;

import java.sql.Date;
import java.util.List;

public class PlayerSearchCriteria {

	private List<String> player ;
	private Date before;
	private Date after;
	
	public Date getBefore() {
		return before;
	}
	public void setBefore(Date before) {
		this.before = before;
	}
	public Date getAfter() {
		return after;
	}
	public void setAfter(Date after) {
		this.after = after;
	}
	public List<String> getPlayer() {
		return player;
	}
	public void setPlayer(List<String> player) {
		this.player = player;
	}
	
}
