package com.then.db.config.datasource;

import com.then.db.config.common.DynamicDataSourceGlobal;

public final class DynamicDataSourceHolder {
	
	private static final ThreadLocal<DynamicDataSourceGlobal> holder = new ThreadLocal<DynamicDataSourceGlobal>();
	 
    private DynamicDataSourceHolder() {}
 
    public static void putDataSource(DynamicDataSourceGlobal dataSource){
        holder.set(dataSource);
    }
 
    public static DynamicDataSourceGlobal getDataSource(){
        return holder.get();
    }
 
    public static void clearDataSource() {
        holder.remove();
    }
}
