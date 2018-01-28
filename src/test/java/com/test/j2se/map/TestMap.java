package com.test.j2se.map;

import com.google.common.collect.Maps;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by yixiaoshuang on 2018/1/8.
 */
public class TestMap extends TestCase{

    public void test1(){
        Map map = Maps.newHashMap();
        Map map2 = new HashMap();
        System.out.println(1<<30 );

    }
}
