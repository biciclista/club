package com.bcclst.club.server.service;

import com.bcclst.club.server.dto.ClubDto;
import com.bcclst.club.server.service.exception.ExistentClubAcronymException;

public interface ClubService {

	public ClubDto create(ClubDto club) throws ExistentClubAcronymException;

}
