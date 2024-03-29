package com.study.spring.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.study.spring.BDao.BDao;

public class BDeleteCommand implements BCommand {

	public BDeleteCommand() {
		
	}
	@Override
	public void execute(Model model) {
		Map<String,Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		
		String bId=request.getParameter("bId");		
		BDao dao = BDao.getInstance();
		dao.delete(bId);
	}

}
