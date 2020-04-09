package com.sxt.crm.utils;

import com.sxt.crm.exceptions.ParamsException;

public class AssertUtil {
    public static void isTrue(Boolean flag,String msg){
        if(flag){
            throw new ParamsException(msg);
        }
    }
}
