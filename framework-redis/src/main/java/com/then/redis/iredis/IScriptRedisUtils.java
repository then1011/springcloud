package com.then.redis.iredis;

import java.util.List;

public interface IScriptRedisUtils {
	
	<T> T executeScript(Class<T> returnClass, String script, String nameSpace, List<String> keys, Object... args);

	default <T> T executeScript(Class<T> returnClass, String script, List<String> keys, Object... args) {
		return executeScript(returnClass, script, null, keys, args);
	}
}
