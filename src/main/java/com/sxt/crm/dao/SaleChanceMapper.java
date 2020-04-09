package com.sxt.crm.dao;

import com.sxt.base.BaseMapper;
import com.sxt.base.BaseQuery;
import com.sxt.crm.vo.SaleChance;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleChanceMapper extends BaseMapper<SaleChance,Integer> {

}