/* 로그인 인터셉터 */
package org.brp.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.brp.domain.UserVO;
import org.brp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import lombok.extern.log4j.Log4j;

@Log4j
public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
    	
		log.info("loginInterceptor.............preHandel");
        
        Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
        
        // 자동 로그인 상태가 아닌 경우(일반 로그인 요청이 들어온 상태)
        if (loginCookie == null) {
        	return true;
        }
        // 자동 로그인인 경우
        else {
            return false;
        }
    }
	 
}
