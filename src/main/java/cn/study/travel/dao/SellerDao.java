package cn.study.travel.dao;

import cn.study.travel.domain.Seller;

public interface SellerDao {
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Seller findById(int id);
}
