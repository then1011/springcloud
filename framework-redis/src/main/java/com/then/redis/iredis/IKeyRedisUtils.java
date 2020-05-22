package com.then.redis.iredis;

import java.util.Set;

public interface IKeyRedisUtils {
	
	Boolean existKey(String nameSpace, String key);

	default Boolean existKey(String key) {
		return existKey(null, key);
	}
	
	Set<String> getAllKey(String nameSpace, String keyPattern);
	
	default Set<String> getAllKey(String keyPattern) {
		return getAllKey(null, keyPattern);
	};
}
