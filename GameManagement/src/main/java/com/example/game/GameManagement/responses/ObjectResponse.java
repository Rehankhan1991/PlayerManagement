package com.example.game.GameManagement.responses;

import java.math.BigDecimal;
import java.util.Date;

public class ObjectResponse {

	private BigDecimal score;
	private Date time;

	public ObjectResponse(BigDecimal score, Date time) {
		this.score = score;
		this.time = time;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
