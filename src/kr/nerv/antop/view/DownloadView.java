package kr.nerv.antop.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.nerv.antop.entity.model.Attach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

@Component("download")
public class DownloadView extends AbstractView {

	public DownloadView() {
		setContentType("application/octet-stream");
	}

	@Autowired
	private ServletContext context;
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OutputStream out = response.getOutputStream();
		
		Attach attach = (Attach) model.get("attach");

		File file = new File(context.getRealPath("/") + "/" + attach.getPath() + "/" + attach.getRealName());
		
		if(file.exists()) {
			response.setContentType(attach.getContextType());
			response.setContentLength(attach.getFileSize());
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + attach.getFileName() + "\"");
			
			FileInputStream fis = null;
			
			try {
				fis = new FileInputStream(file);
				FileCopyUtils.copy(fis, out);
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException ex) {
						
					}
				}
			}
			
		}
		
		out.flush();
	}

}
