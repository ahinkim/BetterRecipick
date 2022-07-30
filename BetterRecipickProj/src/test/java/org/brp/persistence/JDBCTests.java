package org.brp.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

import lombok.extern.log4j.Log4j;

@Log4j
public class JDBCTests {
	// 클래스 초기화 블럭 : 클래스 변수의 복잡한 초기화에 사용된다. 클래스가 처음 로딩될 때 한번만 수행된다. 
	// * 인스턴스 초기화도 가능
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testConnection() {
		
		try(Connection con = 
			DriverManager.getConnection(
				"jdbc:oracle:thin:@localhost:1521:XE",
				"book_ex",
				"1207")){
			log.info(con);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
}
