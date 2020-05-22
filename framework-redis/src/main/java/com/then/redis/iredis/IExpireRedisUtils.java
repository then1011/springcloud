package com.then.redis.iredis;

public interface IExpireRedisUtils {
	
	Boolean expire(String nameSpace, String key, long timeoutSeconds);

	default Boolean expire(String key, long timeoutSeconds) {
		return expire(null, key, timeoutSeconds);
	}

	Long getExpire(String nameSpace, String key);

	default Long getExpire(String key) {
		return getExpire(null, key);
	}
}
