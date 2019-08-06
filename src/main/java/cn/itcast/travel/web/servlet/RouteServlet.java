package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RoutService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RoutServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet{

    private RoutService service = new  RoutServiceImpl();

    private FavoriteService favoriteService = new FavoriteServiceImpl();


    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //接收参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");

        String rname = request.getParameter("rname");


        //类别ID
        int cid = 0;
        if(cidStr!=null&&cidStr.length()>0&&!"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }
        //当前页数ID
        int currentPage =0;
        if(currentPageStr!=null&&currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage=1;
        }
        //每页多少条记录
        int pageSize = 0;
        if(pageSizeStr!=null&&pageSizeStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            pageSize =10;
        }

        //调用Service
        PageBean<Route> pageBean = service.pageQuery(cid, currentPage, pageSize ,rname);




        //序列化并返回
        writeValue(pageBean,response);

    }

    /**
     *根据ID 查询一个旅游线路的详细信息
     *
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String ridStr = request.getParameter("rid");

        int rid = Integer.parseInt(ridStr);

        Route route =  service.findOne(rid);

        writeValue(route,response);

    }

    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String ridStr = request.getParameter("rid");

        User user = (User) request.getSession().getAttribute("user");

        int uid ;
        int rid =  Integer.parseInt(ridStr);

        if(user ==null){

            uid=0;

        }else {

            uid = user.getUid();
        }

        boolean flag = favoriteService.isFavorite(rid, uid);

        writeValue(flag,response);


    }

    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String ridSrt = request.getParameter("rid");
        String uidSrt = request.getParameter("uid");

        int rid = Integer.parseInt(ridSrt);
        int uid = Integer.parseInt(uidSrt);


        favoriteService.addFavorite(rid,uid);




    }







    }
