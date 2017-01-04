package com.bcclst.club.server.service.exception;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestDuplicatedClubAcronymException {

	@Test
	public void testGetLocalizedMessage() {

		DuplicatedClubAcronymException e = new DuplicatedClubAcronymException("AA");
		assertEquals(String.format("The acronym '%s' is in use by another club.", "AA"), e.getMessage());
	}

}
