package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RoutService {

    /**
     * 分页的实现
     *
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    /**
     *
     * 根据Id查询
     * @param rid
     * @return
     */
    public Route findOne(int rid);
}
