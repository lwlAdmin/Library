package com.lwl.controller;

import com.lwl.entity.Book;
import com.lwl.entity.Borrow;
import com.lwl.entity.Reader;
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

@WebServlet("/book")
public class BookServlet extends HttpServlet {

    private BookService bookService = new BookServiceImpl();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method= req.getParameter("method");
        if(method == null){//避免空指针
            method = "findAll";
        }
        String pageStr = req.getParameter("page");
        Integer page  =Integer.parseInt(pageStr);
        //登录之后，读者信息就被存储在session中
        HttpSession session = req.getSession();
        Reader reader = (Reader) session.getAttribute("reader");
        switch(method){
            case "findAll"://查询所有图书数据
                List<Book> list = bookService.findAll(page);
                //每页条数
                req.setAttribute("dataPrePage", LENGTH);
                //当前页数
                req.setAttribute("currentPage",page);
                //总页数
                req.setAttribute("pages",bookService.getPages());
                req.setAttribute("list",list);
                req.getRequestDispatcher("index.jsp").forward(req,resp);
                break;
            case "addBorrow"://借阅方法
                String bookidStr = req.getParameter("bookid");
                Integer bookid = Integer.parseInt(bookidStr);
                //发起借书请求
                bookService.addBorrow(bookid,reader.getId());
                resp.sendRedirect("/book?method=findAllBorrow&page=1");
                break;
            case "findAllBorrow"://查看所有借阅信息
                //展示当前所有的所有借书记录
                List<Borrow> borrowList = bookService.findAllBorrowByReaderId(reader.getId(),page);
                req.setAttribute("dataPrePage",LENGTH);
                //当前页数
                req.setAttribute("currentPage",page);
                //总页数
                req.setAttribute("pages",bookService.getBorrowPages(reader.getId()));
                req.setAttribute("list",borrowList);
                req.getRequestDispatcher("borrow.jsp").forward(req,resp);
        }

    }
}
