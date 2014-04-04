package util.jstl;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class FileSize extends SimpleTagSupport {

	private Double size;
	
	final private double KILO_BYTE = 1024;
	final private double MEGA_BYTE = 1024 * 1024;
	final private double GIGA_BYTE = 1024 * 1024 * 1024;
		
	@Override
	public void doTag() throws JspException, IOException {
		
		String html = "";
		BigDecimal bd = null;
		
		if(size < KILO_BYTE) {
			html = String.valueOf(size);
		} else if(size < MEGA_BYTE ) {
			// kb
			bd = new BigDecimal(size / KILO_BYTE).setScale(1, BigDecimal.ROUND_DOWN);
			html = bd.toString() + "KB";
		} else if(size < GIGA_BYTE) {
			// mb
			bd = new BigDecimal(size / MEGA_BYTE).setScale(1, BigDecimal.ROUND_DOWN);
			html = bd.toString() + "MB";
		} else {
			// gb
			bd = new BigDecimal(size / GIGA_BYTE).setScale(1, BigDecimal.ROUND_DOWN);
			html = bd.toString() + "GB";
		}
			
		// 출력
		getJspContext().getOut().print(html);
	}

	public Double getSize() {
		return size;
	}

	public void setSize(Double size) {
		this.size = size;
	}
	
}
