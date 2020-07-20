package cn.study.travel.service.impl;

import cn.study.travel.dao.FavoriteDao;
import cn.study.travel.dao.RouteDao;
import cn.study.travel.dao.RouteImgDao;
import cn.study.travel.dao.SellerDao;
import cn.study.travel.dao.impl.FavoriteDaoImpl;
import cn.study.travel.dao.impl.RoureDaoImpl;
import cn.study.travel.dao.impl.RouteImgDaoImpl;
import cn.study.travel.dao.impl.SellerDaoImpl;
import cn.study.travel.domain.PageBean;
import cn.study.travel.domain.Route;
import cn.study.travel.domain.RouteImg;
import cn.study.travel.domain.Seller;
import cn.study.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService{
    private RouteDao routeDao = new RoureDaoImpl();

    private RouteImgDao routeImgDao = new RouteImgDaoImpl();

    private SellerDao sellerDao = new SellerDaoImpl();

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        //封装PageBean
        PageBean<Route> pb = new PageBean<Route>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);
        //设置总记录数
        int totalCount = routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);
        //设置当前页显示的数据集合
        int start = (currentPage -1) * pageSize;//开始的记录数
        System.out.println(start+" 啊啊"+pageSize+"并不 "+rname);
        List<Route> list = routeDao.findByPage(cid, start, pageSize, rname);
        System.out.println(list);
        pb.setList(list);
        //设置总页数= 总记录数/每页显示条数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);
        System.out.println(pb);
        return pb;
    }

    @Override
    public Route findOne(String rid) {
        //根据id去route表中查询route对象
        System.out.println("进入了这里");
        Route route = routeDao.findOne(Integer.parseInt(rid));
        //根据route的id 查询图片集合信息
        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
        //将集合设置到route对象
        route.setRouteImgList(routeImgList);
        //根据route的sid 查询商家对象
        Seller seller = sellerDao.findById(route.getSid());
        System.out.println(seller+seller.getSname());
        route.setSeller(seller);
        //查询收藏次数
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }
}
