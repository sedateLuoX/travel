package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        UserService service = new UserServiceImpl();

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.doPost(request, response);
    }
}
