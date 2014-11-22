package org.cv.dao;

import org.cv.model.RankTemp;

public interface RankTempDao {
    int deleteByPrimaryKey(Long id);

    int insert(RankTemp record);

    int insertSelective(RankTemp record);

    RankTemp selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RankTemp record);

    int updateByPrimaryKey(RankTemp record);
}