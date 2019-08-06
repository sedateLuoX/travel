package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

     private  UserService  service = new UserServiceImpl();

    /**
     * 注册工能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       // System.out.println("这是add方法");
        //验证码
        String check = request.getParameter("check");

        HttpSession session = request.getSession();

        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");

        if (checkcode_server==null){

            errorMag(response, "请重新获取验证码");

            return;
        }
        if(!checkcode_server.equalsIgnoreCase(check)){

            //验证码错误
            errorMag(response, "验证码错误");

            return;



        }

        //  获取数据;

        Map<String, String[]> map = request.getParameterMap();

        //分装对象

        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //调用Service



        boolean flag =  service.regist(user);

        ResultInfo info = new ResultInfo();
        //响应结果
        if(flag){

            info.setFlag(true);

        }else {
            info.setFlag(false);
            info.setErrorMsg("注册失败");


        }
        //将info 序列化为json

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //将json 数据写会数据段;

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

    }

    private void errorMag(HttpServletResponse response, String str) throws IOException {
        //验证码错误
        ResultInfo info = new ResultInfo();
        info.setFlag(false);
        info.setErrorMsg(str);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //将json 数据写会数据段;

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /**
     * 登录工能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //System.out.println("这是find方法");
        Map<String, String[]> map = request.getParameterMap();

        User user = new User();
        try {

            BeanUtils.populate(user,map);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        User u = service.login(user);
        ResultInfo info = new ResultInfo();

        if(u==null){
            info.setFlag(false);
            info.setErrorMsg("用户名活密码错误");
        }
        if(u!=null&& !"Y".equals(u.getStatus())){
            info.setFlag(false);
            info.setErrorMsg("账户未激活");

        }
        if (u!=null&&"Y".equals(u.getStatus())){

            info.setFlag(true);
            HttpSession session = request.getSession();
            session.setAttribute("user",u);

        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //将json 数据写会数据段;

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /**
     * 查询单个对象
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        //将json 数据写会数据段;

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);


    }

    /**
     * 退出
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();

        response.sendRedirect(request.getContextPath()+"/login.html");


    }

    /**
     * 激活
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String code = request.getParameter("code");
        System.out.println(code);
        if(code!=null){
            //调用激活

            boolean flag = service.active(code);

            //判断标记
            String msg = null;
            if(flag){
                //成功
                msg = "激活成功,请 <a href='../login.html'> 登录 </a>";
            }else {
                //错误
                msg = "激活失败自己看着办";

            }
            response.setContentType("text/html;charset=utf-8");

            response.getWriter().write(msg);

        }

    }
}
