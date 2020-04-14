package com.sxt.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sxt.crm.dao.ModuleMapper;
import com.sxt.crm.dto.ModuleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseService<T,ID> {

    @Autowired
    private BaseMapper<T,ID> baseMapper;

    @Resource
    private ModuleMapper moduleMapper;

    /**
     * 添加记录返回行数
     * @param entity
     * @return
     */
    public Integer insertSelective(T entity) throws DataAccessException{
        return baseMapper.insertSelective(entity);
    }

    /**
     * 添加记录返回主键
     * @param entity
     * @return
     */
    public ID insertHasKey(T entity) throws DataAccessException{
        baseMapper.insertHasKey(entity);
        try {
           return (ID) entity.getClass().getMethod("getId").invoke(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    /**
     * 批量添加
     * @param entities
     * @return
     */
    public Integer insertBatch(List<T> entities) throws DataAccessException{
        return baseMapper.insertBatch(entities);
    }


    /**
     * 根据id 查询详情
     * @param id
     * @return
     */
    public T selectByPrimaryKey(ID id) throws DataAccessException{
        return baseMapper.selectByPrimaryKey(id);
    }


    /**
     * 多条件查询
     * @param baseQuery
     * @return
     */
    public List<T> selectByParams(BaseQuery baseQuery) throws DataAccessException{
        /*System.out.println("进来了");
        System.out.println(baseMapper.selectByParams(baseQuery)==null);*/
        return baseMapper.selectByParams(baseQuery);
    }


    /**
     * 更新单条记录
     * @param entity
     * @return
     */
    public Integer updateByPrimaryKeySelective(T entity) throws DataAccessException{
        return baseMapper.updateByPrimaryKeySelective(entity);
    }


    /**
     * 批量更新
     * @param entities
     * @return
     */
    public Integer updateBatch(List<T> entities) throws DataAccessException{
        return baseMapper.updateBatch(entities);
    }

    /**
     * 删除单条记录
     * @param id
     * @return
     */
    public Integer deleteByPrimaryKey(ID id) throws DataAccessException{
        return baseMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    public Integer deleteBatch(ID[] ids) throws DataAccessException{
        return baseMapper.deleteBatch(ids);
    }


    //分页查询信息
    public Map<String,Object> queryByParams(BaseQuery baseQuery){
        /*System.out.println("进来了");*/
        Map<String,Object> result=new HashMap<String,Object>();
        //开启分页
        PageHelper.startPage(baseQuery.getPage(),baseQuery.getRows());
        //分页查询功能
        PageInfo<T> pageInfo=new PageInfo<T>(selectByParams(baseQuery));
       /* System.out.println();*/
        result.put("total",pageInfo.getTotal());//页码
        result.put("rows",pageInfo.getList());//每一页的数据
        return result;
    }




}
