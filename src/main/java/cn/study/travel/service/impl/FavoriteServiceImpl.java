package cn.study.travel.service.impl;

import cn.study.travel.dao.FavoriteDao;
import cn.study.travel.dao.impl.FavoriteDaoImpl;
import cn.study.travel.domain.Favorite;
import cn.study.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService{
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
        return favorite != null;
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }
}
