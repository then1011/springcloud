package com.then.redis.iredis;

public interface IStringRedisUtils {

	void set(String nameSpace, String key, Object value, long timeoutSeconds);

	default void set(String key, Object value, long timeoutSeconds) {
		set(null, key, value, timeoutSeconds);
	};

	Object get(String nameSpace, String key);

	default Object get(String key) {
		return get(null, key);
	}

}
