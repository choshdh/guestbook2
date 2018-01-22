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
import com.javaex.vo.GuestBookVO;


@WebServlet("/g2c")
public class Guestbook2Controller extends HttpServlet {
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("a");
		if(action.equals("deleteform")) {
			RequestDispatcher rd = request.getRequestDispatcher("deleteform.jsp");
			request.setAttribute("no", request.getParameter("no"));
			rd.forward(request, response);
		}else if(action.equals("list")) {
			GuestBookDAO dao = new GuestBookDAO();
			List<GuestBookVO> l = dao.selectAllGuestBook();
			request.setAttribute("l", l);
			RequestDispatcher rd = request.getRequestDispatcher("list.jsp");  //RequestDispatcher를 사용한 forward 는 무조건 select하는 페이지에만 사용할 것.
																			  //만약 업데이트,삭제,생성하는 페이지에 사용하게 되면 사용자에게 변경된 url 이 적용되지 않기 때문에 새로고침시에 중복 삭제,생성,업데이트가 발생하게됨
			rd.forward(request, response);
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
				response.sendRedirect("g2c?a=list");
			}else {
				response.sendRedirect("deletefail.html");
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
			response.sendRedirect("g2c?a=list"); //list.jsp 페이지는 포워딩 해서 페이지를 받아오기 때문에 바로 호출하면 아무것도 받아 올수가 없다.
		}else {
			System.out.println("잘못된 요청입니다.");
		}
	}

}
