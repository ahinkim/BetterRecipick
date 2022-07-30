/* org.zerock.mapper의 UserMapper.java에 있는 기능 테스트 */
package org.brp.mapper;

import org.brp.domain.UserVO;
import org.brp.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class UserMapperTests {
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void testInsert() {
		
		UserVO vo = new UserVO();
		vo.setEmail("ahin00@naver.com");
		vo.setPassword("1207");
		vo.setName("김아인");
		vo.setNickName("천재");
		
		userMapper.insert(vo);
		log.info("============================");
	}
	
	@Test
	public void testUpdateIp() {
		
		String email = "ahin00@naver.com";
		String ipAddress = "127.0.0.1";
		
		userMapper.updateIpAddress(email, ipAddress);
		log.info("============================");
	}
	
//	@Test
//	public void testInsertSelectKey() {
//		
//		UserVO vo = new UserVO();
//		vo.setEmail("ahin00@naver.com");
//		vo.setPassword("1207");
//		vo.setName("김아인");
//		vo.setNickName("천재");
//		
//		userMapper.insertSelectKey(vo);
//		log.info("============================");
//		log.info("after insert selectKey " + vo.getUserno());
//	}
//	
//	@Test
//	public void testRead() {
//		
//		BoardVO vo = boardMapper.read(23L);
//		log.info(vo);
//		
//	}
//	
//	@Test
//	public void testDelete() {
//		
//		int count = boardMapper.delete(1L);
//		log.info("count: " + count);
//	
//	}
//	
//	@Test
//	public void testUpdate() {
//		
//		BoardVO vo = new BoardVO();
//		vo.setBno(26L);
//		vo.setTitle("Updated Title");
//		vo.setContent("Updated content");
//		vo.setWriter("user00");
//		
//		log.info("count: " + boardMapper.update(vo)); //업데이트 된 횟수
//	}
//	
//	@Test
//	public void testPaging() {
//		//1 10
//		Criteria cri = new Criteria();
//		
//		List<BoardVO> list = boardMapper.getListWithPaging(cri);
//		
//		list.forEach(b -> log.info(b));
//		
//	}
//	
//	@Test
//	public void testPageDTO() {
//		Criteria cri = new Criteria();
//		cri.setPageNum(25);
//		
//		PageDTO pageDTO = new PageDTO(cri, 251);
//		
//		log.info(pageDTO);
//	}
//	
//	@Test
//	public void testSearchPaging() {
//		//1 10
//		Criteria cri = new Criteria();
//		cri.setType("TCW");
//		cri.setKeyword("Test");
//		
//		List<BoardVO> list = boardMapper.getListWithPaging(cri);
//		
//		list.forEach(b -> log.info(b));
//		
//	}
}
