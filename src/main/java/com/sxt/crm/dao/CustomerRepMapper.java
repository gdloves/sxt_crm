package com.sxt.crm.dao;

import com.sxt.base.BaseMapper;
import com.sxt.crm.vo.CustomerRep;
import org.springframework.web.bind.annotation.RequestParam;

public interface CustomerRepMapper extends BaseMapper<CustomerRep,Integer> {
    public CustomerRep queryByMeasure(@RequestParam(value = "measure") String measure);
}