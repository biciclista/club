package com.bcclst.club.server.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * DTO object form transfer values between client and server. Club has the
 * following fields.
 * <ul>
 * 	<li>id: Unique identifier of club.
 * 	<li>name: Complete name of the club. Size must be between 3 and 64 characters.
 * 	<li>acronym: Short name for the club. Size must be between 3 and 5 characters.
 * </ul>
 * 
 * @author nacho
 *
 */
public class ClubDto {

	@NotNull
	private Long id;

	@NotNull
	@Size(min = 3, max = 64)
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
