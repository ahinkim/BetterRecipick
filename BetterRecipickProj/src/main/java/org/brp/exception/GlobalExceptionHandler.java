package org.brp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
@Log4j
public class GlobalExceptionHandler {
	
	/* IllegalArgumentException.class, NestedServletException.class, TypeNotPresentException.class,
	NullAttributeException.class, NullPointerException.class, HttpMessageNotReadableException.class
	, MissingServletRequestParameterException.class, MethodArgumentNotValidException.class, BindException.class, 	MethodArgumentTypeMismatchException.class*/
	
	//클라이언트 요청 에러 - 유효하지 않은 값 
	@ExceptionHandler(value = {MethodArgumentNotValidException.class, HttpMessageNotReadableException.class}) 
    public ResponseEntity<String> ClientExceptionHandler(Exception ex) {
			
		log.error("ClientException........" + ex.getMessage());
		
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		//return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
	
	//클라이언트 요청 에러 - 이메일 중복
	@ExceptionHandler(value = {AlreadyExistEmailException.class}) 
    public ResponseEntity<String> ExistEmailExceptionHandler(Exception ex) {
			
		log.error("AlreadyExistEmailException........" + ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

	//클라이언트 요청 에러 - 로그인 실패
	@ExceptionHandler(value = {LoginFailureException.class}) 
    public ResponseEntity<String> LoginFailureExcetpionHandler(Exception ex) {
			
		log.error("LoginFailureException........" + ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
	
	//클라이언트 요청 에러 - IP 불일치
	@ExceptionHandler(value = {MismatchedIPException.class}) 
    public ResponseEntity<String> MismatchedIPExceptionHandler(Exception ex) {
			
		log.error("MismatchedIPExceptionException........" + ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }
	
	//서버 내부 에러
	@ExceptionHandler(value = {Exception.class})
    public ResponseEntity<String> ServerExceptionHandler(Exception ex) {
		
		log.error("ServerException........" + ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
