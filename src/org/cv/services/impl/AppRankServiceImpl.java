package org.cv.services.impl;

import org.cv.core.spring.SpringBaseService;
import org.cv.dao.AppRankDao;
import org.cv.model.AppInfo;
import org.cv.model.AppRank;
import org.cv.services.AppRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AppRankServiceImpl  extends SpringBaseService implements AppRankService{
	@Autowired
	private AppRankDao appRankDao;
	
	@Override
	public int insertRank(AppInfo record) {
		// TODO Auto-generated method stub
		return appRankDao.insertRank(record);
	}
	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	

	@Override
	public int insertSelective(AppRank record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AppRank selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(AppRank record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(AppRank record) {
		// TODO Auto-generated method stub
		return 0;
	}

}
