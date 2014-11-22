package org.cv.dao;

import org.cv.model.AppInfo;

public interface AppInfoDao { 
    int deleteByPrimaryKey(Long appId);
    
    int insert(AppInfo record);

    boolean checkRank(AppInfo recored);
    
    String checkDateBase(AppInfo recored);
    
    String insertAndGetAppId(AppInfo record);
    
    int insertSelective(AppInfo record);

    AppInfo selectByPrimaryKey(Long appId);

    int updateByPrimaryKeySelective(AppInfo record);

    int updateByPrimaryKey(AppInfo record);
}