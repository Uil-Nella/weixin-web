package org.cs.demo.manager.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cs.demo.manager.db.model.pojo.User;
import org.cs.demo.manager.db.service.UserService;
import org.cs.demo.manager.db.serviceimpl.UserServiceImpl;

public class UserServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		UserService service = new UserServiceImpl();
		boolean flag = service.loginCheck(user);
		if(flag){
			//用户名和密码验证正确
			request.getSession().setAttribute("user",user);
			request.getRequestDispatcher("/jsp/main.jsp").forward(request, response);
		}else{
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			request.getSession().setAttribute("error_message","用户名或密码错误！");
		}
		
	}

}
