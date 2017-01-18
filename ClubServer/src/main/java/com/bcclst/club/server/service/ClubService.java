package com.bcclst.club.server.service;

import java.util.List;

import com.bcclst.club.server.dto.ClubDto;
import com.bcclst.club.server.service.exception.ClubNotFoundException;
import com.bcclst.club.server.service.exception.DuplicatedClubAcronymException;

public interface ClubService {

	ClubDto create(ClubDto club) throws DuplicatedClubAcronymException;

	ClubDto findById(long id) throws ClubNotFoundException;

	List<ClubDto> findAll();
}
