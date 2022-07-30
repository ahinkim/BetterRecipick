package org.brp.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AuthInfo {
	  @Email
	  @NotEmpty
	  private String email;
	  @NotEmpty
	  private String password;
	  @NotNull
	  private boolean auto; // 자동 로그인 체크 여부
}
