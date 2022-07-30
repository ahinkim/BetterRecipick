package org.brp.service;

import java.util.Date;

import org.brp.domain.UserVO;
import org.brp.mapper.UserMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@RequiredArgsConstructor
@ToString
public class UserServiceImpl implements UserService{
	private final UserMapper mapper;

	@Override
	public int register(UserVO user) {
		
		return mapper.insert(user);
		
	}

	@Override
	public int checkId(String email) {

		return mapper.getCheckId(email);
		
	}

	@Override
	public String login(String email) {
		return mapper.getPasswordById(email);
	}

	@Override
	public int updateClientIp(String email, String ipAddress) {
		
		return mapper.updateIpAddress(email, ipAddress);
		
	}

	@Override
	public String getIpAddress(String email) {
		
		return mapper.getIpAddress(email);
		
	}

	@Override
	public int keepLogin(String sessionId, Date sessionlimit, String email) {
		
		return mapper.updateSessionInfo(sessionId, sessionlimit, email);
		
	}

	@Override
	public UserVO checkLoginBefore(String sessionId) {
		
		return mapper.checkSessionKey(sessionId);
		
	}

}
