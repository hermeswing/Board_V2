package util.jstl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Paging extends SimpleTagSupport {

	private String action;
	private String search = "";
	private String className = "";

	private int page;
	private int total;
	private int rowSize = 10;
	private int blockSize = 10;

	@Override
	public void doTag() throws JspException, IOException {

		/*
		 * System.out.println("page = " + page); System.out.println("total = " +
		 * total); System.out.println("rowSize = " + rowSize);
		 * System.out.println("blockSize = " + blockSize);
		 */

		int page = this.page - 1;

		int totalBlock = (total / rowSize) + (this.total % rowSize > 0 ? 1 : 0);
		int startBlock = (new Integer((page / blockSize)) * blockSize) + 1;
		int endBlock = startBlock + blockSize - 1;

		if (totalBlock < endBlock) {
			endBlock = totalBlock;
		}

		/*
		 * System.out.println("totalBlock = " + totalBlock);
		 * System.out.println("startBlock = " + startBlock);
		 * System.out.println("endBlock = " + endBlock);
		 */

		// 뒤에 붙는 파라미터
		String params = "&rowSize=" + rowSize + "&search=" + search;

		String html = "";

		// 처음
		if (this.page > blockSize) {
			html += " <a href=\"" + action + "?page=1" + params + "\" id=\"ui-paging-first\" class=\""
					+ className + "\">처음</a> ";
		}

		// 이전 페이지
		if (this.page > blockSize) {
			html += " <a href=\"" + action + "?page=" + (startBlock - 1) + params
					+ "\" id=\"ui-paging-prev\" class=\"" + className + "\">이전</a> ";
		}

		// 페이지 번호
		for (int i = startBlock; i <= endBlock; i++) {

			if (i > startBlock) {
				html += " | ";
			}

			if (i == this.page) {
				html += " <span class=\"" + className
						+ "\" style=\"font-weight:bold; color: #ff0000\">" + i
						+ "</span>";
			} else {
				html += " <a href=\"" + action + "?page=" + i + params + "\" class=\""
						+ className + "\">" + i + "</a> ";
			}
		}

		// 다음 페이지
		if (endBlock != totalBlock) {
			html += " <a href=\"" + action + "?page=" + (endBlock + 1) + params
					+ "\" id=\"ui-paging-next\" class=\"" + className + "\">다음</a> ";
		}

		// 마지막
		if (endBlock < totalBlock) {
			html += " <a href=\"" + action + "?page=" + totalBlock + params
					+ "\" id=\"ui-paging-last\" class=\"" + className + "\">마지막</a> ";
		}

		// 출력
		getJspContext().getOut().print(html);

	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
