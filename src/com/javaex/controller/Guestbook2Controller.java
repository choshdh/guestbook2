package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestBookDAO;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestBookVO;


@WebServlet("/g2c")
public class Guestbook2Controller extends HttpServlet {
       
	//WebUtil 클래스를 만들어서 forward 와 redirect 를 담당하는 함수를 만들어서 좀더 편하게 처리.
	//redirect 는 사용자가 페이지를 다시 요청 하는 것이기 때문에 WEB-INF 폴더를 이용해서 폴더에 있는 페이지에 강제로 접근 하려는 행위를 막을수 있다.
	//forward 는 개발자가 사용자에게 제공할 페이지를 넘겨주는것이기 때문에 WEB-INF 폴더의 자원(페이지)에 접근 할 수 있다.
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("a");
		if(action.equals("deleteform")) {
			request.setAttribute("no", request.getParameter("no"));
			WebUtil.forward(request, response, "/WEB-INF/deleteform.jsp"); // path의 기본 root 는 /WebContent 이다.
//			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/deleteform.jsp");  
//			rd.forward(request, response);
		}else if(action.equals("list")) {
			GuestBookDAO dao = new GuestBookDAO();
			List<GuestBookVO> l = dao.selectAllGuestBook();
			request.setAttribute("l", l);
			WebUtil.forward(request, response, "/WEB-INF/list.jsp");
//			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/list.jsp");  //RequestDispatcher를 사용한 forward 는 무조건 select하는 페이지에만 사용할 것.
//																			  //만약 업데이트,삭제,생성하는 페이지에 사용하게 되면 사용자에게 변경된 url 이 적용되지 않기 때문에 새로고침시에 중복 삭제,생성,업데이트가 발생하게됨
//			rd.forward(request, response);
		}else {
			System.out.println("잘못된 요청입니다.");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("a");
		if(action.equals("delete")) {
			int no = Integer.parseInt(request.getParameter("no"));
			String pass = request.getParameter("password");
			GuestBookDAO dao = new GuestBookDAO();
			int result = dao.deleteGuestBook(no, pass);
			if(result == 1) {
				WebUtil.redirect(request, response, "g2c?a=list");
			}else {
				WebUtil.forward(request,response,"/WEB-INF/deletefail.html");
			}
			
		}else if(action.equals("add")) {
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
			
			GuestBookDAO dao = new GuestBookDAO();
			GuestBookVO vo = new GuestBookVO();
			vo.setName(name);
			vo.setPassword(password);
			vo.setContent(content);
			dao.insertGuestBook(vo);
			WebUtil.redirect(request, response, "g2c?a=list");
		}else {
			System.out.println("잘못된 요청입니다.");
		}
	}

}
