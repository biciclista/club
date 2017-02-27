package com.bcclst.club.webclient.service;

import java.util.List;

import com.bcclst.club.webclient.dto.ClubDto;
import com.bcclst.club.webclient.service.exception.ClubNotFoundException;
import com.bcclst.club.webclient.service.exception.DuplicatedClubAcronymException;

public interface ClubService {
	List<ClubDto> findAll();
	
	ClubDto findById(long id) throws ClubNotFoundException;
	
	ClubDto create(ClubDto club) throws DuplicatedClubAcronymException;
	
	ClubDto update(ClubDto club) throws DuplicatedClubAcronymException;
	
	ClubDto disable(long id);
	
	ClubDto enable(long id);
}
