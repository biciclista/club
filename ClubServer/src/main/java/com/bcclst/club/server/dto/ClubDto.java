package com.bcclst.club.server.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ClubDto {

	@NotNull
	private Long id;

	@NotNull
	@Size(max = 64)
	private String name;

	@NotNull
	@Pattern(regexp = "[a-zA-Z0-9]{3,5}")
	private String acronym;

	public ClubDto() {
		
	}
	
	public ClubDto(Long id, String name, String acronym) {
		this.id = id;
		this.name = name;
		this.acronym = acronym;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

}
