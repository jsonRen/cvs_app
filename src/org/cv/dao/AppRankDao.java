package org.cv.dao;

import org.cv.model.AppInfo;
import org.cv.model.AppRank;

public interface AppRankDao {
    int deleteByPrimaryKey(Long id);

    int insertRank(AppInfo record);

    int insertSelective(AppRank record);

    AppRank selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppRank record);

    int updateByPrimaryKey(AppRank record);
}