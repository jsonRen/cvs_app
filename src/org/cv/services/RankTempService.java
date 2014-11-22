package org.cv.services;

import org.cv.model.RankTemp;

public interface RankTempService {
    int deleteByPrimaryKey(Long id);

    int insert(RankTemp record);

    int insertSelective(RankTemp record);

    RankTemp selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RankTemp record);

    int updateByPrimaryKey(RankTemp record);
}