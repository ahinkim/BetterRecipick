/* 회원 관련 controller */

package org.brp.controller;



import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.brp.common.util.HttpUtil;
import org.brp.common.util.ValidationGroups;
import org.brp.domain.AuthInfo;
import org.brp.domain.UserVO;
import org.brp.exception.AlreadyExistEmailException;
import org.brp.exception.LoginFailureException;
import org.brp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/user/*")
@Log4j
@AllArgsConstructor

public class UserController {
	private static final String AUTH_INFO = "AuthInfo";
	private static final String IP = "IpAddress";
	
	private final UserService service; 
	private final HttpUtil util;
	BCryptPasswordEncoder passwordEncoder;
	
	// 회원가입 요청 경로
	@PostMapping(value = "/register", consumes = "application/json")
	public ResponseEntity<String> register(@RequestBody @Validated(ValidationGroups.group2.class) UserVO user, HttpServletRequest request){ 
		
		log.info("register...............user" + user);
		
		int idCount = service.checkId(user.getEmail());
		if (idCount == 1) {
			throw new AlreadyExistEmailException(); // 아이디 중복일 때
		}
		// 비밀번호 암호화
		String encPassword = passwordEncoder.encode(user.getPassword());
		
		user.setPassword(encPassword);
		
		int insertCount = service.register(user);
		
		log.info("insert Count: " + insertCount);

		return new ResponseEntity<>(HttpStatus.CREATED); // 회원가입 성공
	}
    
	// 아이디 중복 확인 요청 경로
	@PostMapping(value = "/checkId", consumes = "application/json")
	public ResponseEntity<String> checkId(@RequestBody @Validated(ValidationGroups.group1.class) UserVO user) {
		
		log.info("checkId...............user" + user);
		
		String email = user.getEmail();
		
		int idCount = service.checkId(email);
		log.info("id Count: " + idCount);

		if (idCount == 0) {
			return new ResponseEntity<>(HttpStatus.OK); // 아이디 중복 아닐 때
		}
		else {
			throw new AlreadyExistEmailException(); // 아이디 중복일 때
		}
		
	}
	// 로그인 요청 경로
	@PostMapping(value = "/login", consumes = "application/json")
	public ResponseEntity<String> login(@RequestBody @Valid AuthInfo authInfo,
			HttpServletRequest request, HttpServletResponse response){ 
		
		log.info("login...............auth" + authInfo);
		
		Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
		
		String email = authInfo.getEmail();
		String password = authInfo.getPassword();
		boolean auto = authInfo.isAuto();
		
		String truePassword = service.login(email);
		
		// DB에 있는 password와 일치할 때
		if (passwordEncoder.matches(password, truePassword)) {
			// 세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
			HttpSession session = request.getSession();
			
			// 세션에 로그인 회원 정보 보관
			session.setAttribute(AUTH_INFO, authInfo);
			
			// 클라이언트 IP Address 얻기
			String ipAddress = util.getIp(request);
			
			log.info("IP Address: " + ipAddress);
			
			// session에 IP Address 저장
			session.setAttribute(IP, ipAddress);
			// DB에 IP Address 저장
			service.updateClientIp(email, ipAddress);
			
			// 자동 로그인에 체크헀을 떄
			if (auto) {
				log.info("remember me........");
				
				int amount = 60*60*24*7;
				String sessionId = session.getId();
				
				// 로그인 정보 유지를 위한 쿠키: 유효기간 7일
            	loginCookie = new Cookie("loginCookie", sessionId);
            	loginCookie.setPath("/");
            	loginCookie.setMaxAge(amount);
            	response.addCookie(loginCookie);
            	
            	// 서버에 세션 정보 저장
            	Date sessionlimit = new Date(System.currentTimeMillis() + (1000*amount));
            	service.keepLogin(sessionId, sessionlimit, email);
			}
			
			return new ResponseEntity<>(HttpStatus.OK); // 로그인 성공
		}
		else {
			throw new LoginFailureException(); // 로그인 정보 불일치
		}
	}
	// 로그아웃 요청 경로
	@GetMapping(value = "/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
		
		log.info("logout...............user");
		
        HttpSession session = request.getSession(false);
        Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
        
        AuthInfo authInfo = (AuthInfo)session.getAttribute(AUTH_INFO);
        String email = authInfo.getEmail();
        
        if (session != null) {
            session.invalidate();
        }
        
        if (loginCookie != null) {
        	
        	loginCookie.setPath("/");
			loginCookie.setMaxAge(0);
			response.addCookie(loginCookie);
			service.keepLogin("none", new Date(), email);
        }
        
        return new ResponseEntity<>(HttpStatus.OK);

    }
	// 회원탈퇴 요청 경로
	@DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete(HttpServletRequest request, HttpServletResponse response) {
		
		log.info("delete...............user");
		
        HttpSession session = request.getSession(false);
        Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
        
        AuthInfo authInfo = (AuthInfo)session.getAttribute(AUTH_INFO);
        String email = authInfo.getEmail();
        
        if (session != null) {
            session.invalidate();
        }
        
        if (loginCookie != null) {
        	
        	loginCookie.setPath("/");
			loginCookie.setMaxAge(0);
			response.addCookie(loginCookie);
        }
		// 회원 탈퇴
        int idCount = service.deleteAccount(email);
        // 유효하지 않은 session 값일 때(DB에 일치하는 이메일이 없을 때)
        if (idCount == 0) {
            throw new LoginFailureException();
        }
        
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
