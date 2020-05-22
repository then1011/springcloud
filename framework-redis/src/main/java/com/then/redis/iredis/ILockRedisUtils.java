package com.then.redis.iredis;

import org.redisson.api.RLock;

public interface ILockRedisUtils {
	
	RLock getLock(String nameSpace, String key);

	default RLock getLock(String key) {
		return getLock(null, key);
	};

}
