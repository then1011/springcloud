package com.then.redis.iredis;

public interface IHashRedisUtils {

	void setHash(String nameSpace, String key, String hkey, Object value, long timeoutSeconds);

	default void setHash(String key, String hkey, Object value, long timeoutSeconds) {
		setHash(null, key, hkey, value, timeoutSeconds);
	}

	Object getHash(String nameSpace, String key, String hkey);

	default Object getHash(String key, String hkey) {
		return getHash(null, key, hkey);
	}

}
