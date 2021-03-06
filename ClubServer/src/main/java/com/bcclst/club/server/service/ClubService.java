package com.bcclst.club.server.service;

import java.util.List;

import com.bcclst.club.server.dto.ClubDto;
import com.bcclst.club.server.service.exception.ClubNotFoundException;
import com.bcclst.club.server.service.exception.DuplicatedClubAcronymException;

public interface ClubService {

	/**
	 * Creates a new club. A club has a name and an acronym.
	 * 
	 * @param club {@link ClubDto} with the values for the new club.
	 * @return {@link ClubDto} with the values of the created club.
	 * @throws DuplicatedClubAcronymException If another club is using the same
	 *             acronym.
	 */
	ClubDto create(ClubDto club) throws DuplicatedClubAcronymException;

	/**
	 * Retrieves one club by its identifier.
	 * 
	 * @param id Identifier of the club looked up.
	 * @return {@link ClubDto} with the values of the found club.
	 * @throws ClubNotFoundException If there is no club with this identifier.
	 */
	ClubDto findById(long id) throws ClubNotFoundException;

	/**
	 * Retrieves all clubs.
	 * 
	 * @return List populated with {@link ClubDto} for each club.
	 */
	List<ClubDto> findAll();

	/**
	 * Updates the values of one club.
	 * 
	 * @param club {@link ClubDto} with the values of the club to update.
	 * @return {@link ClubDto} with the updated values of the club.
	 * @throws ClubNotFoundException If the club to update does not exist.
	 */
	ClubDto update(ClubDto club) throws ClubNotFoundException;

	/**
	 * Deletes one club.
	 * 
	 * @param id identifier of the club to delete.
	 * @return {@link ClubDto} with the values of the deleted club.
	 * @throws ClubNotFoundException If the club to delete does not exist.
	 */
	ClubDto deleteById(long id) throws ClubNotFoundException;
	
	ClubDto changeState(long id, boolean active) throws ClubNotFoundException;
}
