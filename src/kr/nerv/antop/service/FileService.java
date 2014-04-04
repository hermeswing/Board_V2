package kr.nerv.antop.service;

import java.util.List;

import kr.nerv.antop.entity.model.Attach;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public Attach save(MultipartFile file, String session);

	public Attach inquire(int seq);

	public void remove(int seq, String session);

	public List<Attach> inquireByBoard(int bardId);
}
