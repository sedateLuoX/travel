package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RoutService;

import java.util.List;

public class RoutServiceImpl implements RoutService {

    private RouteDao routeDao = new RouteDaoImpl();

    private RouteImgDao routeImgDao = new RouteImgDaoImpl();

    private SellerDao sellerDao = new SellerDaoImpl();


    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     * 查询分装数据
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {

        PageBean<Route>  pb = new PageBean<>();

        pb.setCurrentPage(currentPage);

        pb.setPageSize(pageSize);
        if("null".equals(rname)){
            rname="";
        }
        //
        int totalCount = routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);

        int start = (currentPage-1)*pageSize;


        //
        List<Route> list = routeDao.findByPage(cid, start, pageSize,rname);
        pb.setList(list);

        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize: (totalCount/pageSize)+1;

        pb.setTotalPage(totalPage);


        return pb;
    }


    @Override
    public Route findOne(int rid) {
        Route route  = routeDao.findOne(rid);


        //根据ID查询图片信息;
        List<RouteImg> routeImgsList = routeImgDao.findByRid(rid);

        route.setRouteImgList(routeImgsList);

        //查询商家Id

        int sid  = route.getSid();

        Seller seller = sellerDao.findSellerById(sid);

        route.setSeller(seller);

        int count =  favoriteDao.findFavoriteCountByRid(rid);

        route.setCount(count);


        return route;

    }
}
