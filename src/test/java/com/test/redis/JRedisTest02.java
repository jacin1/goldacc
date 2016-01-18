package com.test.redis;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.xsyi.dao.redis.IUserDao;


@ContextConfiguration(locations={"classpath*:applicationContext-redis.xml"})
public class JRedisTest02 extends AbstractJUnit4SpringContextTests{
	
	private IUserDao userRDao;
	
	 public void setUserRDao(IUserDao userRDao) {
		this.userRDao = userRDao;
	}

	/** 
     * 新增 
     * <br>------------------------------<br> 
     */  
    @Test  
    public void testAddUser() {  
//        Usertest user = new Usertest();
//        user.setId("user1");
//        user.setName("java2000_wl");
//        boolean result = userRDao.add(user);
//        Assert.assertTrue(result);
    }
	
}
