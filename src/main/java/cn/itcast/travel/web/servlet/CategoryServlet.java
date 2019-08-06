package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    private CategoryService service = new CategoryServiceImpl();
    /**
     *
     * 查询所有的分类
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categoryList = service.findAll();
        //序列化Json返回

   /*   ObjectMapper mapper = new ObjectMapper();

        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),categoryList);*/


        writeValue(categoryList,response);
      /*  String s = writeValueToString(categoryList);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(s);

*/
       // System.out.println(s);




    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("这是category的add");
    }
}
