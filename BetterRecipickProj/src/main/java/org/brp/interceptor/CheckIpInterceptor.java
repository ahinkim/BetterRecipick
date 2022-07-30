/* 중간에 IP가 변경되지 않았는 지 확인하는 Interceptor */
package org.brp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.brp.common.util.HttpUtil;
import org.brp.domain.AuthInfo;
import org.brp.exception.MismatchedIPException;
import org.brp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
public class CheckIpInterceptor extends HandlerInterceptorAdapter {
	
	private static final String AUTH_INFO = "AuthInfo";
	private static final String IP = "IpAddress";
	@Autowired
	private HttpUtil util;
	@Autowired
	private UserService service; 
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		log.info("CheckIpInterceptor.........preHandle");
		
        HttpSession session = request.getSession();
        String ipAddress = (String)session.getAttribute(IP);

        //session에 IP 주소가 없으면 DB에서 가져오기
        if (ipAddress == null) {
        	
            AuthInfo authInfo = (AuthInfo) session.getAttribute(AUTH_INFO);
            if (authInfo != null) {
                String email = authInfo.getEmail();
            	ipAddress = service.getIpAddress(email);	
            }
        }
        
        String reqIp = util.getIp(request);
        
        log.info("userIP: " + ipAddress + " reqIP: " + reqIp);
        
    	// IP가 일치하지 않을 때 (클라이언트에서 강제 로그아웃 처리)	
        if (!ipAddress.equals(reqIp)) {
            if (session != null) {
                session.invalidate();
            }
            throw new MismatchedIPException();
        } 
        
		return true;
		
	}
}
