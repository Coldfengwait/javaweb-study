package cn.study.travel.service.impl;

import cn.study.travel.dao.CategoryDao;
import cn.study.travel.dao.impl.CategoryDaoImpl;
import cn.study.travel.domain.Category;
import cn.study.travel.service.CategoryService;
import cn.study.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        //从redis中查询
        //获取jedis客户端
        Jedis jedis = JedisUtil.getJedis();
        //查询排序查询sortedset中的分数(cid)和值(cname)
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        List<Category> cs = null;
        if (categorys == null || categorys.size() == 0) {
            //重数据库中查询
            cs = categoryDao.findAll();
            //将集合数据存储到redis中，category的key

            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category",cs.get(i).getCid(), cs.get(i).getCname());
            }
        } else {
            //将redis中的set的数据存入list
            cs = new ArrayList<Category>();
            for(Tuple tuple:categorys) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                cs.add(category);
            }
        }
        return cs;
    }
}
