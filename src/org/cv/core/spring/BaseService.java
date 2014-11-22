package org.cv.core.spring;

import org.springframework.context.ApplicationContext;

public interface BaseService {
	public ApplicationContext getApplicationContext(); //取得spring容器上下文
}
