package org.brp.domain;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.brp.common.util.ValidationGroups;

import lombok.Data;

@Data
public class UserVO {
	  private Long userno;
	  @NotEmpty(groups = {ValidationGroups.group1.class, ValidationGroups.group2.class})
	  @Email(groups = {ValidationGroups.group1.class, ValidationGroups.group2.class})
	  private String email;
	  @NotEmpty(groups = {ValidationGroups.group2.class})
	  private String password, name, nickName;
	  private String ipAddress;
	  private Date regDate, updateDate;
	  
}
