package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {

    public Favorite findByRidAnduid (int rid, int uid);

    public int findFavoriteCountByRid(int rid);

     public void addFavorite(int rid, int uid);
}
