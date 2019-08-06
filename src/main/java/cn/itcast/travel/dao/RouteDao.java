package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {

    //查询中记录数
    public  int findTotalCount(int cid, String rname);

    //查询需要查询的信息
    public List<Route> findByPage(int cid, int start, int pageSize,String rname);


    /**
     * 更具ID 查询
     * @param rid
     * @return
     */
    public  Route findOne(int rid);

}
