package org.cv.core.spring;

import org.springframework.context.ApplicationContext;

public abstract class SpringBaseService implements BaseService{
	
	
	@Override
	public ApplicationContext getApplicationContext(){
		return SpringUtils.getApplicationContext();
	}
}
