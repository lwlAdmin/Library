package com.lwl.controller;

import com.lwl.entity.Admin;
import com.lwl.entity.Borrow;
import com.lwl.service.BookService;
import com.lwl.service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.lwl.service.impl.BookServiceImpl.LENGTH;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private BookService bookService = new BookServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method == null){//method默认是findAllBorrow
            method = "findAllBorrow";
        }
        HttpSession session = req.getSession();
        Admin admin= (Admin)session.getAttribute("admin");
        String pageStr = req.getParameter("page");
        Integer page = Integer.parseInt(pageStr);
        switch(method){
            case "findAllBorrow"://找出所有未审核的图书记录
                List<Borrow> borrowList = bookService.findAllBorrowByState(0,page);
                req.setAttribute("dataPrePage",LENGTH);
                //当前页数
                req.setAttribute("currentPage",page);
                //总页数
                req.setAttribute("pages",bookService.getBorrowPagesByState(0));
                req.setAttribute("list",borrowList);
                req.getRequestDispatcher("admin.jsp").forward(req,resp);
                break;
            case "handle"://对未审核的图书进行处理
                Integer id = Integer.parseInt(req.getParameter("id"));
                Integer state = Integer.parseInt(req.getParameter("state"));
                bookService.handleBorrow(id,state,admin.getId());
                if (state == 1 || state == 2) {
                    resp.sendRedirect("/admin?page=" + page);
                }else if(state == 3) {//表示已归还
                    resp.sendRedirect("/admin?method=getBorrowed&page=" + page);
                }
                break;
            case "getBorrowed"://找出所有已被借出去的书进行还书处理
                List<Borrow> getBorrowedList = bookService.findAllBorrowByState(1,page);
                //每页LENGTH条数据
                req.setAttribute("dataPrePage",LENGTH);
                //当前页数
                req.setAttribute("currentPage",page);
                //总页数
                req.setAttribute("pages",bookService.getBorrowPagesByState(1));
                req.setAttribute("list",getBorrowedList);
                req.getRequestDispatcher("return.jsp").forward(req,resp);
        }
    }
}
