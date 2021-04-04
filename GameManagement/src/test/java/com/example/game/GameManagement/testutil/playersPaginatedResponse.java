package com.example.game.GameManagement.testutil;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.game.GameManagement.model.Players;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class playersPaginatedResponse extends PageImpl<Players> {

	  private static final long serialVersionUID = 3248189030448292002L;

	    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	    public playersPaginatedResponse(@JsonProperty("content") List<Players> content, @JsonProperty("number") int number, @JsonProperty("size") int size,
	                    @JsonProperty("totalElements") Long totalElements, @JsonProperty("pageable") JsonNode pageable, @JsonProperty("last") boolean last,
	                    @JsonProperty("totalPages") int totalPages, @JsonProperty("sort") JsonNode sort, @JsonProperty("first") boolean first,
	                    @JsonProperty("numberOfElements") int numberOfElements,@JsonProperty("empty") boolean empty) {
	        super(content, PageRequest.of(number, size), totalElements);
	    }

	    public playersPaginatedResponse(List<Players> content, Pageable pageable, long total) {
	        super(content, pageable, total);
	    }

	    public playersPaginatedResponse(List<Players> content) {
	        super(content);
	    }

	    public playersPaginatedResponse() {
	        super(new ArrayList<Players>());
	    }
	
	
}
