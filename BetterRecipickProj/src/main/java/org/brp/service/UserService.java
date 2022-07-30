package org.brp.service;


import java.util.Date;

import org.brp.domain.UserVO;

public interface UserService {
	
	int register(UserVO user); // 회원 가입

	int checkId(String email); // 아이디 중복 확인
	
	String login(String email); // 로그인 정보 일치 확인
	
	int updateClientIp(String email, String ipAddress); // 클라이언트 IP 갱신
	
	String getIpAddress(String email); // 클라이언트 IP 주소 조회
	
	int keepLogin(String sessionId, Date sessionlimit, String email); // 로그인 유지
	
	UserVO checkLoginBefore(String sessionId); // 로그인 되어 있는 상태인지 확인(자동 로그인)
}