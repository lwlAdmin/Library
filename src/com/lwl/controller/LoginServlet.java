package com.lwl.controller;

import com.lwl.entity.Admin;
import com.lwl.entity.Borrow;
import com.lwl.entity.Reader;
import com.lwl.service.impl.BookServiceImpl;
import com.lwl.service.BookService;
import com.lwl.service.LoginService;
import com.lwl.service.impl.LoginServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private LoginService loginService = new LoginServiceImpl();
    private BookService bookService = new BookServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    /**
     * 处理登录的业务逻辑
     * @param req 浏览器发来的请求
     * @param resp 服务端的响应
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String type = req.getParameter("type");
        Object object = loginService.login(username,password,type);
        if (object != null) {
            //object不为空说明用户存在
            HttpSession session = req.getSession();
            switch(type){
                case "reader":
                    Reader reader = (Reader)object;
                    session.setAttribute("reader",reader);
                    //读者登录成功，跳转到读者界面,默认在第一页
                    resp.sendRedirect("/book?page=1");
                    break;
                case "admin":
                    Admin admin = (Admin)object;
                    session.setAttribute("admin",admin);
                    //管理员登录成功，跳转到管理员首页
                   resp.sendRedirect("/admin?method=findAllBorrow&page=1");
                    break;
            }
        }else{
            //为空则说明用户不存在需要重新登录

            resp.sendRedirect("login.jsp");
        }
    }
}
