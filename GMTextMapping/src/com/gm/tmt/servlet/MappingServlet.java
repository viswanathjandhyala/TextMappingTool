package com.gm.tmt.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/MappingServlet")
public class MappingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public MappingServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String fileName = "withshortkeys";
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName+".json"+ "\"");
		String jsonString = request.getParameter("jsonString");
		ServletOutputStream os = response.getOutputStream();
		byte[] bufferData = jsonString.getBytes();
		os.write(bufferData);
	}
}