package com.service;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.dao.LoginDao;
import com.dao.RegisterDao;
import com.domain.User;
import com.utils.MD5;
import com.utils.MyTools;


public class RegisterService extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String action=request.getParameter("action");
		MD5 MD=new MD5();
		String register=MD.toMD5("register");
		String toRegister=MD.toMD5("toRegister");
		if(action.equals(toRegister))
			this.toRegister(request, response);
		if(action.equals(register))
			this.registerCheck(request, response);
		System.out.println("*********************88888888888888");
	}
	
	/**
	 * @
	 */
   public void toRegister(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	   RequestDispatcher rd=request.getRequestDispatcher("/Register.jsp");
		rd.forward(request,response);
   }
   
   /**
	 * @
	 */
	public void registerCheck(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		 String name=request.getParameter("name");
		 String pwd=request.getParameter("pwd");
		 String mail=request.getParameter("mail");
		 String confirmpwd=request.getParameter("confirmpwd");
		 if(name==null||name.equals(""))                                     
		 {   
			 request.setAttribute("messages","<span>ע������ <b>����Ϊ�գ�</b></span>");
			 request.getRequestDispatcher("/Register.jsp").forward(request, response);
		 }else if(mail==null||mail.equals("")) 
		 {
			 request.setAttribute("messages","<span>ע������<b>����Ϊ�գ�</b></span>");
			 request.getRequestDispatcher("/Register.jsp").forward(request, response);
		 }
		 else if(pwd==null||pwd.equals(""))                                  //如果密码为空
		 {   
			 request.setAttribute("messages","<span>���벻�� <b>Ϊ�գ�</b></span>");
			 request.getRequestDispatcher("/Register.jsp").forward(request, response);
		 }else if(!pwd.equals(confirmpwd)) 
		 {
			 request.setAttribute("messages","<span>�������벻һ�£�</b></span>");
			 request.getRequestDispatcher("/Register.jsp").forward(request, response);
		 }else if(name.length()>20)
		 {
			 request.setAttribute("messages","<span>ע�����Ƴ��Ȳ��ܳ���20��</b></span>");
			 request.getRequestDispatcher("/Register.jsp").forward(request, response);
		 }
		 else if(pwd.length()>20)
		 {
			 request.setAttribute("messages","<span>���볤�Ȳ��ܳ���20��</b></span>");
			 request.getRequestDispatcher("/Register.jsp").forward(request, response);
		 }else if(!MyTools.validateEmail(mail))
		 {
			 request.setAttribute("messages","<span>��������Ч�����䣡</b></span>");
			 request.getRequestDispatcher("/Register.jsp").forward(request, response);
		 }
		 else
		 {
			 MD5 md5=new MD5();
			String[]s={MyTools.toChinese(name),md5.toMD5(pwd)};
			String[]ss={MyTools.toChinese(name),MyTools.toChinese(mail),md5.toMD5(pwd)};
			System.out.println("*********************");
			// User master=new LoginDao().getMaster(s);
			// if(!master.getUserName().equals(""))                        
			// {
				// request.setAttribute("messages","<span>�������Ѿ�ע�ᣡ</span>"); 
				// request.getRequestDispatcher("/WEB-INF/Register.jsp").forward(request,response);
			// }else {                                                         
				     
				     if(new RegisterDao().toRegister(ss))
				     {
				       HttpSession session=request.getSession();
				       User masterBean=new LoginDao().getMaster(s);
				       session.setAttribute("masterBean", masterBean);  
					   response.sendRedirect("/FoolAlgorithm/index.jsp");                         
				     }else {
				    	 request.setAttribute("messages","<span>ע��ɹ���</span>"); 
						 request.getRequestDispatcher("/Register.jsp").forward(request,response);
					}
			//}
		 }
	}
   
}