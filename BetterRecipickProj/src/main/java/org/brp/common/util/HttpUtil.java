package org.brp.common.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class HttpUtil {
	public String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		
		log.info("......X-FORWARDED-FOR : " + ip);
		
		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
			log.info("......Proxy-Client-IP: " + ip);
		}
		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			log.info("......WL-Proxy-Client-IP: " + ip);
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			log.info("......HTTP_CLIENT_IP: " + ip);
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			log.info("......HTTP_X_FORWARDED_FOR: " + ip);
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
		}
		
		log.info("......Result: IP Address:" + ip);
		
		return ip;
	}
}