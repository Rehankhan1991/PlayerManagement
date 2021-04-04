package com.example.game.GameManagement.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Players implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4411689027547764663L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	private String player ;
	private Double score;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Tokyo")
	private Date time;

	protected Players() {
	}

	public Players(String player , Double score) {
		this.player  = player ;
		this.score = score;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlayer () {
		return player ;
	}

	public void setPlayer (String player ) {
		this.player  = player ;
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

	 @Override
	  public boolean equals(Object o) {

	    if (this == o)
	      return true;
	    if (!(o instanceof Players))
	      return false;
	    Players players = (Players) o;
	    return Objects.equals(this.id, players.id) && Objects.equals(this.player, players.player)
	        && Objects.equals(this.score, players.score);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(this.id, this.player, this.score);
	  }

	  @Override
	  public String toString() {
	    return "Players{" + "id=" + this.id + ", name='" + this.player + '\'' + ", score='" + this.score + '\'' + ", date='" + this.time + '\'' + '}';
	  }
	
}
