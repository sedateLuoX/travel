package cn.itcast.travel.web.servlet;


import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.doGet(request,response);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String code = request.getParameter("code");
        System.out.println(code);
        if(code!=null){
            //调用激活
            UserService  service = new UserServiceImpl();
            boolean flag = service.active(code);

            //判断标记
            String msg = null;
            if(flag){
                //成功
                msg = "激活成功,请 <a href='login.html'> 登录 </a>";
            }else {
                //错误
                msg = "激活失败自己看着办";

            }
            response.setContentType("text/html;charset=utf-8");

            response.getWriter().write(msg);

        }

    }
}
