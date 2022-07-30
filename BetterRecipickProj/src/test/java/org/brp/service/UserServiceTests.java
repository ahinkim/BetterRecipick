/* org.zerock.service의 UserService.java에 있는 기능 테스트 */
package org.brp.service;

import org.brp.domain.UserVO;
import org.brp.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class UserServiceTests {
	
	@Autowired
	private UserService service;
	
	@Test
	public void testResister() {
		UserVO vo = new UserVO();
		vo.setEmail("ahin00@naver.com");
		vo.setPassword("1207");
		vo.setName("김아인");
		vo.setNickName("천재");
		
		long userno = service.register(vo);
		
		log.info("BNO: " + userno);
	}
	
	@Test
	public void testCheckId() {
		String email = "ahin98@naver.com";
		
		int count = service.checkId(email);
		
		log.info("COUNT: " + count);
	}
	
}
