package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;


import java.io.IOException;
import java.util.List;


public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();



    @Override
    public List<Category> findAll() {

        //获取redies
        Jedis jedis = JedisUtil.getJedis();
        //查找缓存
        String categorys= jedis.get("category");

        ObjectMapper mapper = new ObjectMapper();
        List<Category> cs = null;
        //如果缓存中没有数据
        if(categorys==null||categorys.equals("")){
             cs = categoryDao.findAll();
            System.out.println("从数据库中读取数据");
            try {
                String json = mapper.writeValueAsString(cs);
                jedis.set("category",json);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }


        }else{
            try {
                 cs = mapper.readValue(categorys, List.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



        return cs;
    }
}
