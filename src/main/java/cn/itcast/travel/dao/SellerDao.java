package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {


    /**
     * 根据Id查商家
     *
     * @param sid
     * @return
     */
    public Seller findSellerById(int sid);
}
