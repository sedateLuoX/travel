package cn.itcast.travel.service;

public interface FavoriteService {

    public  boolean isFavorite(int rid, int uid);

    public void addFavorite(int rid, int uid);
}
