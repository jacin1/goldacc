package com.xsyi.dao.redis;

import java.util.List;

import com.xsyi.model.UserR;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.xsyi.common.AbstractBaseRedisDao;

public class UserDaoImpl extends AbstractBaseRedisDao<String, UserR> implements IUserDao {

	@Override
	public boolean add(UserR user) {
		redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection conn) throws DataAccessException {
//				RedisSerializer<JacksonJsonRedisSerializer<T>>
				
				return null;
			}
		});
		
		return false;
	}

	@Override
	public boolean add(List<UserR> list) {
		return false;
	}

	@Override
	public void delete(String key) {
		
	}

	@Override
	public void delete(List<String> keys) {
		
	}

	@Override
	public boolean update(UserR user) {
		return false;
	}

	@Override
	public UserR get(String keyId) {
		return null;
	}

}
