package com.then.redis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.then.redis.iredis.IDeleteRedisUtils;
import com.then.redis.iredis.IExpireRedisUtils;
import com.then.redis.iredis.IHashRedisUtils;
import com.then.redis.iredis.IIncrementRedisUtils;
import com.then.redis.iredis.IKeyRedisUtils;
import com.then.redis.iredis.IListRedisUtils;
import com.then.redis.iredis.ILockRedisUtils;
import com.then.redis.iredis.IScriptRedisUtils;
import com.then.redis.iredis.ISetRedisUtils;
import com.then.redis.iredis.IStringRedisUtils;
import com.then.redis.iredis.IZSetRedisUtils;

@Component
public class RedisUtils implements IDeleteRedisUtils, IExpireRedisUtils, IHashRedisUtils, IIncrementRedisUtils,
IKeyRedisUtils,IListRedisUtils,ILockRedisUtils,IScriptRedisUtils,ISetRedisUtils,IStringRedisUtils,IZSetRedisUtils{

	@Value("${spring.application.name:default_app}")
	private String springApplicationName;

	@Autowired
	@Qualifier("customRedisTemplate")
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private RedissonClient redissonClient;
	
	
	private String generateKey(String nameSpace, String key) {
		
		if (StringUtils.isEmpty(nameSpace)) {
			nameSpace = springApplicationName;
		}
		return "{" + nameSpace + "}:" + key;
	}


	@Override
	public void set(String nameSpace, String key, Object value, long timeoutSeconds) {
		redisTemplate.opsForValue().set(generateKey(nameSpace, key), value, timeoutSeconds, TimeUnit.SECONDS);
	}


	@Override
	public Object get(String nameSpace, String key) {
		return redisTemplate.opsForValue().get(generateKey(nameSpace, key));
	}


	@Override
	public <T> T executeScript(Class<T> returnClass, String script, String nameSpace, List<String> keys,Object... args) {
		List<String> newKeys = new ArrayList<>();
		keys.stream().forEach(key -> {
			newKeys.add(generateKey(nameSpace, key));
		});

		return redisTemplate.execute(new DefaultRedisScript<T>(script, returnClass), newKeys, args);
	}


	@Override
	public Boolean existKey(String nameSpace, String key) {
		return redisTemplate.hasKey(generateKey(nameSpace, key));
	}


	@Override
	public Set<String> getAllKey(String nameSpace, String keyPattern) {
		if (StringUtils.isEmpty(nameSpace)) {
			nameSpace = springApplicationName;
		}
		
		String pattern = "{" + nameSpace + "}*" + (StringUtils.isEmpty(keyPattern)?"" : (keyPattern + "*"));
		return redisTemplate.keys(pattern);
	}


	@Override
	public Long increment(String nameSpace, String key, long delta) {
		return redisTemplate.opsForValue().increment(generateKey(nameSpace, key), delta);
	}


	@Override
	public Long incrementHash(String nameSpace, String key, String hKey, long delta) {
		return redisTemplate.opsForHash().increment(generateKey(nameSpace, key), hKey, delta);
	}


	@Override
	public void setHash(String nameSpace, String key, String hkey, Object value, long timeoutSeconds) {
		HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
		hashOperations.put(generateKey(nameSpace, key), hkey, value);
		expire(generateKey(nameSpace, key), timeoutSeconds);
	}


	@Override
	public Object getHash(String nameSpace, String key, String hkey) {
		return redisTemplate.opsForHash().get(generateKey(nameSpace, key), hkey);
	}


	@Override
	public Boolean expire(String nameSpace, String key, long timeoutSeconds) {
		return redisTemplate.expire(generateKey(nameSpace, key), timeoutSeconds, TimeUnit.SECONDS);
	}


	@Override
	public Long getExpire(String nameSpace, String key) {
		return redisTemplate.getExpire(generateKey(nameSpace, key), TimeUnit.SECONDS);
	}


	@Override
	public Long del(String nameSpace, String[] key) {
		if (key == null || key.length == 0) {
			return 0l;
		}
		List<String> delKeys = Arrays.stream(key).map(streamKey -> generateKey(nameSpace, streamKey))
				.collect(Collectors.toList());
		return redisTemplate.delete(delKeys);
	}


	@Override
	public Long delHashByKey(String nameSpace, String key, Object... hKeys) {
		HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
		return hashOperations.delete(generateKey(nameSpace, key), hKeys);
	}


	@Override
	public RLock getLock(String nameSpace, String key) {
		return redissonClient.getLock(generateKey(nameSpace, key));
	}

}
