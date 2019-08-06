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

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, String[]> map = request.getParameterMap();

        User user = new User();
        try {

            BeanUtils.populate(user,map);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService service  = new UserServiceImpl();
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);

    }
}
