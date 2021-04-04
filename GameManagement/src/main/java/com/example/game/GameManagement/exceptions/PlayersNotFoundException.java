package com.example.game.GameManagement.exceptions;

import com.example.game.GameManagement.responses.Status;

public class PlayersNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1120374414075220391L;

	public PlayersNotFoundException(Long id){
		super("Could not find player for: " + id);
	}
	public PlayersNotFoundException(Status status){
		super(status.toString());
	}
	
}
