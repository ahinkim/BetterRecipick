package org.brp.domain;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PageDTO {
	
	private int startPage, endPage;
	private boolean prev, next;
	
	private int total;
	private Criteria cri;
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
								//0.3 -> 1 -> 10
		this.endPage = (int) (Math.ceil(cri.getPageNum()/10.0)) * 10;
		
		this.startPage = endPage - 9;
		
		this.prev = this.startPage > 1; //starPage가 1이 아니라면 이전 페이지가 있는 것
										//1case: 71.0 / 10 -> 7.1 -> 8 // 2case: 300.0/10 -> 30 -> 30
		int realEnd = (int)(Math.ceil((total * 1.0) / cri.getAmount())); //1.0곱하는 이유는 소수로 만들기 위해
		
		this.endPage = realEnd <= endPage? realEnd : endPage;
		
		this.next = this.endPage < realEnd; // 뒤로가는 페이지가 있는 지 여부 - 1case: False, 2case: True 
	}
}
