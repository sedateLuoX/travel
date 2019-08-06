package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(int rid,int uid) {
        Favorite favorite = favoriteDao.findByRidAnduid(rid, uid);



        return favorite!=null;
    }

    @Override
    public void addFavorite(int rid, int uid) {

        favoriteDao.addFavorite(rid,uid);


    }
}
