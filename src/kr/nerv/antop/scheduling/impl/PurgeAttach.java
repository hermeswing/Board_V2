package kr.nerv.antop.scheduling.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import kr.nerv.antop.entity.mapper.AttachMapper;
import kr.nerv.antop.entity.model.Attach;
import kr.nerv.antop.entity.model.AttachExample;
import kr.nerv.antop.scheduling.Scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// @Component 어노테이션을 사용하는 스케쥴링은 인터페이스를 구현하게 해야 등록이 된다.
// 단독 클래스를 만들어서 사용하니까 동작을 안하드라 -_-;;
@Component
public class PurgeAttach implements Scheduler {
	@Autowired
	private AttachMapper attachMapper;
	@Autowired
	private ServletContext context;
	@Value("${attach.path}")
	private String ATTACH_PATH;
	@Value("${attach.purge.hour}")
	private int PURGE_HOUR;

	@Override
	// 단위: ms
	// 웹어플이 활성화되고 시작, 2시간마다 실행된다.
	@Scheduled(fixedRate = 7200000)
	public void doProcess() {
		// del_flag 가 true 이거나
		// board_id 가 없으면서 설정한 시간(PURGE_HOUR)이 지난 파일 첨부는 삭제된다.
		
		// condition
		AttachExample example = new AttachExample();
		// process = true
		example.createCriteria().andDelFlagEqualTo(true);
		// or (board_id is null and created <= `현재 - 2시간`)
		Date d = new Date((new Date()).getTime() - (PURGE_HOUR * 60 * 60 * 1000));
		example.or(example.createCriteria().andBoardIdIsNull().andCreatedLessThanOrEqualTo(d));

		// execute query
		List<Attach> attachs = attachMapper.selectByExample(example);

		File f = null;
		for (Attach attach : attachs) {
			// 업로드시에 업로드 했던 파일의 위치
			f = new File(context.getRealPath("/") + attach.getPath() + "/" + attach.getRealName());
			if (!f.exists()) { // 파일이 존재 하지 않느다면
				// 현재 설정되어있는 업로드 위치
				f = new File(context.getRealPath("/") + ATTACH_PATH + "/" + attach.getRealName());
			}

			// 두곳 모두 파일이 존재하지 않으면 DB 에서 삭제
			if (!f.exists()) {
				attachMapper.deleteByPrimaryKey(attach.getSeq());
			} else {
				if (f.delete()) {
					// 파일이 정상적으로 삭제 되었으면 DB 에서 삭제
					attachMapper.deleteByPrimaryKey(attach.getSeq());
				}
				
				// delete 를 해도 파일이 사용중이면 삭제가 되지 않는다(false)
				// 이때는 그냥 냅두고 다음 배치 때 삭제 되도록 한다.
			}
		} // end for
	}
}