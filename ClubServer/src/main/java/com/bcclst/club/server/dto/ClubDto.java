package com.bcclst.club.server.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * DTO object form transfer values between client and server. It has the
 * following fields.
 * <ul>
 * <li>id: Unique identifier of club.
 * <li>name: Complete name of the club. Size must be between 3 and 64
 * characters.
 * <li>acronym: Short name for the club. Size must be between 3 and 5
 * characters. Has to be formed by capital letters (A-Z) and numbers (0-9).
 * </ul>
 * 
 * @author Nacho
 */
public class ClubDto {

	@NotNull
	private Long id;

	@NotNull
	@Size(min = 3, max = 64)
	private String name;
	
	private boolean active = false;

	/**
	 * 
	 */
	@NotNull
	@Pattern(regexp = "[A-Z0-9]{3,5}")
	private String acronym;

	public ClubDto() {

	}

	public ClubDto(Long id, String name, String acronym, Boolean active) {
		this.id = id;
		this.name = name;
		this.acronym = acronym.toUpperCase();
		this.active = active;
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
		this.acronym = acronym.toUpperCase();
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
