package org.brp.mapper;


import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.brp.domain.UserVO;

public interface UserMapper {
	
	int insert(UserVO user);
	
	int getCheckId(String email);
	
	String getPasswordById(String email);

	int updateIpAddress(@Param("email") String email, @Param("ipAddress") String ipAddress);
	
	String getIpAddress(String email);
	
	int updateSessionInfo(@Param("sessionId") String sessionId, @Param("sessionlimit") Date sessionlimit, 
			@Param("email") String email);
	
	UserVO checkSessionKey(@Param("sessionId") String sessionId);
	
	int delete(String email);
}
