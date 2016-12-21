package com.bcclst.club.server.dto;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bcclst.common.util.StringUtil;

public class ClubDtoTest {
	private final static Long ID_OK = 0L;
	private final static String NAME_OK = "Club Deportivo 1";
	private final static String ACRONYM_OK = "CLUB1";

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void clubIsValid() {
		ClubDto club = new ClubDto(ID_OK, NAME_OK, ACRONYM_OK);

		Set<ConstraintViolation<ClubDto>> violations = validator.validate(club);
		assertEquals(0, violations.size());
	}

	@Test
	public void acronymWithInvalidCharacters() {
		ClubDto club = new ClubDto(ID_OK, NAME_OK, "AA_AA");
		
		Set<ConstraintViolation<ClubDto>> violations = validator.validate(club);

		assertEquals(1, violations.size());
		ConstraintViolation<ClubDto> violation = violations.iterator().next();
		assertEquals("acronym", violation.getPropertyPath().toString());
		assertEquals("{javax.validation.constraints.Pattern.message}", violation.getMessageTemplate());
	}
	
	@Test
	public void nameTooLong() {
		ClubDto club = new ClubDto(ID_OK, StringUtil.createStringWithLength(65), ACRONYM_OK);

		Set<ConstraintViolation<ClubDto>> violations = validator.validate(club);

		assertEquals(1, violations.size());
		ConstraintViolation<ClubDto> violation = violations.iterator().next();
		assertEquals("name", violation.getPropertyPath().toString());
		assertEquals("{javax.validation.constraints.Size.message}", violation.getMessageTemplate());
	}
	
	@Test
	public void parametersAreNull() {
		ClubDto club = new ClubDto(null, null, null);

		Set<ConstraintViolation<ClubDto>> violations = validator.validate(club);

		assertEquals(3, violations.size());
		for(ConstraintViolation<ClubDto> violation : violations) {
			assertEquals("{javax.validation.constraints.NotNull.message}", violation.getMessageTemplate());
		}
	}
}
