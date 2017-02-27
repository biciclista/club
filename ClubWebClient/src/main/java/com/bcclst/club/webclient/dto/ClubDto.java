package com.bcclst.club.webclient.dto;

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
 * <li>enabled: Indicates if the club is enabled in the application (can be
 * accessed) or not.
 * </ul>
 * 
 * @author Nacho
 */
public class ClubDto {

	private Long id = 0L;

	@NotNull
	@Size(min = 3, max = 64)
	private String name;

	@NotNull
	@Pattern(regexp = "[A-Z0-9]{3,5}")
	private String acronym;

	private boolean enabled = false;

	public ClubDto() {

	}

	public ClubDto(Long id, String name, String acronym, Boolean enabled) {
		this.id = id;
		this.name = name;
		this.acronym = acronym.toUpperCase();
		this.enabled = enabled;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
