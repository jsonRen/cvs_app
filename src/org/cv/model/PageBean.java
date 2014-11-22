package org.cv.model;

import org.jsoup.nodes.Document;

/**
 * @classDescription: 网页实体
 * @author:Lambda
 */
public class PageBean {
	private String content;
	private Document contentDoc;
	public PageBean() {}
	public PageBean(String content, Document contentDoc) {
		this.content = content;
		this.contentDoc = contentDoc;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Document getContentDoc() {
		return contentDoc;
	}
	public void setContentDoc(Document contentDoc) {
		this.contentDoc = contentDoc;
	}
	
}
