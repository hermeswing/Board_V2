package kr.nerv.antop.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;

import kr.nerv.antop.entity.mapper.AttachMapper;
import kr.nerv.antop.entity.model.Attach;
import kr.nerv.antop.entity.model.AttachExample;
import kr.nerv.antop.service.FileService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {
	// log4j
	private final Logger logger = Logger.getLogger(getClass());
	@Autowired
	private ServletContext context;
	@Value("${attach.path}")
	private String attachPath;
	@Autowired
	private AttachMapper mapper;
	
	@Override
	public Attach save(MultipartFile file, String session) {
		try {
			InputStream in = file.getInputStream();
			// 업로드해서 올려진 풀경로(파일이름 포함)
			File f = new File(context.getRealPath("/") + attachPath + "/" + UUID.randomUUID().toString());
			// 임시로 올려진 파일을 업로드 디렉토리에 복사(진짜 업로드)
			FileCopyUtils.copy(in, new FileOutputStream(f));

			// 확장자 추출
			String ext = null;
			int idx = file.getOriginalFilename().lastIndexOf(".");
			if (idx != -1) {
				ext = file.getOriginalFilename().substring(idx + 1);
			}
			if(ext !=null && ext.length() > 10) {
				// 확장자가 10글자가 넘으면 확장자로 안침
				ext = null;
			}
			
			Attach attach = new Attach();
			attach.setPath(attachPath);
			attach.setFileName(file.getOriginalFilename());
			attach.setRealName(f.getName());
			attach.setContextType(file.getContentType());
			attach.setExt(ext);
			attach.setFileSize((new Long(file.getSize())).intValue());
			attach.setCreated(new Date());
			attach.setSession(session);
			attach.setDelFlag(false);
			
			// insert
			mapper.insert(attach);
			
			return attach;
		} catch (FileNotFoundException e) {
			logger.warn(e);
		} catch (IOException e) {
			logger.warn(e);
		}

		return null;
	}

	@Override
	public Attach inquire(int seq) {
		return mapper.selectByPrimaryKey(seq);
	}

	@Override
	public void remove(int seq, String session) {
		Attach attach = mapper.selectByPrimaryKey(seq);

		if (attach == null) {
			throw new EmptyResultDataAccessException("파일이 존재하지 않습니다.", 0);
		}

		// 이 파일을 등록했던 세션이 같이야 삭제 가능
		if (attach.getSession().equals(session)) {
			// 파일 삭제
			File f = new File(context.getRealPath("/") + "/" + attachPath + "/" + attach.getRealName());

			if (f.exists()) {
				boolean isDelete = f.delete();

				if (isDelete) {
					// 파일이 정상적으로 삭되 되었으면 db 에서도 삭제
					mapper.deleteByPrimaryKey(attach.getSeq());
				} else {
					// 당장 파일 삭제가 안되는 경우
					// del_flag = true 로 설정
					// 그럼 배치에 의해서 삭제됨
					attach.setDelFlag(true);

					mapper.updateByPrimaryKey(attach);
				}
			}

		}
	}

	@Override
	public List<Attach> inquireByBoard(int boardId) {
		AttachExample example = new AttachExample();
		example.createCriteria().andBoardIdEqualTo(boardId);
		example.setOrderByClause("seq asc");

		return mapper.selectByExample(example);
	}
}
