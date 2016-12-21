package com.bcclst.club.server;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClubDto {

	@NotNull
	private Long id;

	@NotNull
	@Size(max = 64)
	private String name;

	@NotNull
	@Size(min = 3, max = 5)
	private String shortName;

	public ClubDto() {
		
	}
	
	public ClubDto(Long id, String name, String shortName) {
		this.id = id;
		this.name = name;
		this.shortName = shortName;
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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

}
