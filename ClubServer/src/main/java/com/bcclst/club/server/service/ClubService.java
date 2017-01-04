package com.bcclst.club.server.service;

import com.bcclst.club.server.dto.ClubDto;
import com.bcclst.club.server.service.exception.ClubNotFoundException;
import com.bcclst.club.server.service.exception.DuplicatedClubAcronymException;

public interface ClubService {

	public ClubDto create(ClubDto club) throws DuplicatedClubAcronymException;

	public ClubDto findById(long id) throws ClubNotFoundException;

}
