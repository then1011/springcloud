package com.then.redis.iredis;

public interface IIncrementRedisUtils {
	
	Long increment(String nameSpace, String key, long delta);

	default Long increment(String key, long delta) {
		return increment(null, key, delta);
	}

	default Long increment(String key) {
		return increment(null, key, 1);
	}

	default Long increment(String nameSpace, String key) {
		return increment(nameSpace, key, 1);
	}

	Long incrementHash(String nameSpace, String key, String hKey, long delta);

	default Long incrementHash(String key, String hKey, long delta) {
		return incrementHash(null, key, hKey, delta);
	}

	default Long incrementHash(String key, String hKey) {
		return incrementHash(null, key, hKey, 1);
	}

	default Long incrementHash(String nameSpace, String key, String hKey) {
		return incrementHash(nameSpace, key, hKey, 1);
	}
}
