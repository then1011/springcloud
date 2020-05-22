package com.then.redis.iredis;

public interface IDeleteRedisUtils {

	Long del(String nameSpace, String[] key);

	default Long del(String[] key) {
		return del(null, key);
	}

	default Long del(String nameSpace, String key) {
		return del(nameSpace, new String[] { key });
	}

	default Long del(String key) {
		return del(null, key);
	}

	Long delHashByKey(String nameSpace, String key, Object... hKeys);

	default Long delHashByKey(String key, Object... hKeys) {
		return delHashByKey(null, key, hKeys);
	};

}
