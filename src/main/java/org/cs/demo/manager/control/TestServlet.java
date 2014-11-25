package org.cs.demo.manager.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cs.demo.manager.utils.BUserModel;
import org.cs.demo.manager.utils.TUserDAOJDBC;

public class TestServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List<BUserModel> list = new TUserDAOJDBC().testSelect();
		if(list !=null && list.size()!=0){
			request.setAttribute("list", list);
			request.getRequestDispatcher("/jsp/main.jsp").forward(request, response);
		}else{
			response.sendRedirect(request.getContextPath()+"/error.jsp");
		}
	}

}
