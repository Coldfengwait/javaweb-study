package cn.study.travel.dao.impl;

import cn.study.travel.dao.RouteDao;
import cn.study.travel.domain.Route;
import cn.study.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RoureDaoImpl implements RouteDao{
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findTotalCount(int cid, String rname) {
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        ArrayList params = new ArrayList(); //创建集合 存条件
        if(cid != 0){
            sb.append(" and cid = ? ");
            params.add(cid); //添加对应值
        }
        if(rname != null && rname.length() > 0){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sql = sb.toString();
        return template.queryForObject(sql,Integer.class,params.toArray());

    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        String sql = " select * from tab_route where 1=1 ";
        StringBuffer sb = new StringBuffer(sql);
        ArrayList params = new ArrayList();//条件
        if(cid != 0){
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if(rname != null && rname.length() > 0){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ? , ? "); //分页条件
        sql = sb.toString();
        params.add(start);
        params.add(pageSize);
        System.out.println(sql);
        for (Object o : params.toArray()) {
            System.out.println(o);
        }
        ;
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ? ";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }
}
