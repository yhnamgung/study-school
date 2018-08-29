package com.study.jsp1.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp1.BDao;
import com.study.jsp1.BDto;

public class BListCommand implements BCommand {

	public BListCommand() {
		
	}
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		BDao dao = BDao.getInstance();
		ArrayList<BDto> dtos = dao.list();
		request.setAttribute("list", dtos);
	}
}