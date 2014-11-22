package org.cv.services.impl;

import org.cv.core.spring.SpringBaseService;
import org.cv.dao.AppInfoDao;
import org.cv.model.AppInfo;
import org.cv.services.AppInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppInfoServiceImpl extends SpringBaseService implements AppInfoService {
	
	@Autowired
	private AppInfoDao appInfoDao;
	
	@Override
	public boolean checkRank(AppInfo record) {
		// TODO Auto-generated method stub
		return appInfoDao.checkRank(record);
	}
	
	@Override
	public String checkDateBase(AppInfo record) {
		// TODO Auto-generated method stub
		System.out.println(record.getAppRank().getDownloadNum());
		System.out.println(record.getDeviceType());
		System.out.println(record.getAppName());
		return appInfoDao.checkDateBase(record);
	}
	
	@Override
	public String insertAndGetAppId(AppInfo record) {
		// TODO Auto-generated method stub
		return appInfoDao.insertAndGetAppId(record);
	}

	@Override
	public AppInfo selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return appInfoDao.selectByPrimaryKey(id);
	}

	@Override
	public int deleteByPrimaryKey(Long appId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(AppInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(AppInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(AppInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(AppInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}




}
