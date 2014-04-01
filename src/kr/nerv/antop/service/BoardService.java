package kr.nerv.antop.service;

import java.util.Map;

import kr.nerv.antop.entity.model.Board;

public interface BoardService {
	public Map<String, Object> inquire(Map<String, Object> map);
	
	public Board inquire(int boardId);
	
	public void increaseCount(Board board);
	
	public void save(Map<String, Object> map);
	
	public void auth(int boardId, String pwd) throws Exception;
	
	public void remove(int boardId);
}
