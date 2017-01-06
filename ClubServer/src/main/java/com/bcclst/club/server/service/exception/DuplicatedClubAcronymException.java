package com.bcclst.club.server.service.exception;

public class DuplicatedClubAcronymException extends Exception {

	private static final long serialVersionUID = 2894647064409676921L;
	private static final String message = "The acronym %s is in use by another club.";

	private String duplicatedAcronym;

	public DuplicatedClubAcronymException(String acronym) {
		super(String.format(message, acronym));
		this.duplicatedAcronym = acronym;
	}

	public String getDuplicatedAcronym() {
		return duplicatedAcronym;
	}

}
