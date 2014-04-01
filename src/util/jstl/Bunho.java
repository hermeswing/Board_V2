package util.jstl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Bunho extends SimpleTagSupport {
	
	private int page;
	private int total;
	private int rowSize = 10;
	private int rowIdx;
	
	@Override
	public void doTag() throws JspException, IOException {
		
		/*
		System.out.println("page = " + page);
		System.out.println("total = " + total);
		System.out.println("rowIdx = " + rowIdx);
		*/
		
		int page = this.page - 1;
		int bunho = total - ( page * rowSize ) - rowIdx;
		
		// 출력
		getJspContext().getOut().print(bunho);
		
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRowSize() {
		return rowSize;
	}

	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}

	public int getRowIdx() {
		return rowIdx;
	}

	public void setRowIdx(int rowIdx) {
		this.rowIdx = rowIdx;
	}
	
}
