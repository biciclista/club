package com.bcclst.club.server.dto;

import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bcclst.common.util.StringUtil;

public class ClubDtoTest {
	private final Long ID_OK = 0L;
	private final String NAME_OK = "Club Deportivo";
	private final String SHORTNAME_OK = "CLUB";

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void ifParametersOkPasses() {
		ClubDto club = new ClubDto(ID_OK, NAME_OK, SHORTNAME_OK);
		
		Set<ConstraintViolation<ClubDto>> violations = validator.validate(club);
		assertEquals(0, violations.size());
	}

	@Test
	public void ifShortStringsValidationFails() {
		ClubDto club = new ClubDto(ID_OK, NAME_OK, StringUtil.createStringWithLength(2));

		Set<ConstraintViolation<ClubDto>> violations = validator.validate(club);

		assertEquals(1, violations.size());
		assertEquals("size must be between 3 and 5", violations.iterator().next().getMessage());
	}
}
