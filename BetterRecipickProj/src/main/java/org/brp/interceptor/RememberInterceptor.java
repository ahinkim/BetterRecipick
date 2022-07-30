/* 자동 로그인 Interceptor */
package org.brp.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.brp.domain.AuthInfo;
import org.brp.domain.UserVO;
import org.brp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import lombok.extern.log4j.Log4j;

@Log4j
public class RememberInterceptor extends HandlerInterceptorAdapter {
	
	private static final String AUTH_INFO = "AuthInfo";
	private static final String IP = "IpAddress";
	@Autowired
	private UserService service;
    
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

    	log.info("RememberInterceptor.........preHandle");
        HttpSession session = request.getSession();
        Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
        AuthInfo authInfo = (AuthInfo) session.getAttribute(AUTH_INFO);
        
        // 자동 로그인일 때(처음 접속했을 때) session이 비어있다면 session 등록
        if (loginCookie != null & authInfo == null) {
        	
            UserVO user = service.checkLoginBefore(loginCookie.getValue());
            authInfo = new AuthInfo();
            		
            if (user != null) {
            	log.info(user);
            	authInfo.setEmail(user.getEmail());
            	authInfo.setPassword(user.getPassword());
            	authInfo.setAuto(true);
            	
                session.setAttribute(AUTH_INFO, authInfo);
                session.setAttribute(IP, service.getIpAddress(user.getEmail()));
            }
        }
        
        return true;
    }
	
}
