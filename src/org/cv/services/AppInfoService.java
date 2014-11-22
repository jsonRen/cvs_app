package org.cv.services;

import org.cv.model.AppInfo;

public interface AppInfoService {
    int deleteByPrimaryKey(Long appId);

    int insert(AppInfo record);

    boolean checkRank(AppInfo record);
    
    String checkDateBase(AppInfo record);
    
    String insertAndGetAppId(AppInfo record);
    
    int insertSelective(AppInfo record);

    AppInfo selectByPrimaryKey(Long appId);

    int updateByPrimaryKeySelective(AppInfo record);

    int updateByPrimaryKey(AppInfo record);
}