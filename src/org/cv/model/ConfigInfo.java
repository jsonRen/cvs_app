package org.cv.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigInfo {
    @Value("${FILE_IP_PATH}")
	private String file_ip_path;//ip列表地址

    @Value("${HTML_ROOT_PATH}")
    private String html_root_path;//html文件根路径
    
    @Value("${LOG4J_PATH}")
    private String log4j_path;//log4j路径
    
    @Value("${QUANBU_PATH}")
    private String quanbu_path;//全局属性配置文件路径 
    
    @Value("${REAL_RANK_PIC_PATH}")
    private String real_rank_pic_path; //排名图片生成路径
    
    @Value("${PIC_PATH}") 
    private String pic_path; //appLogo路径 
    
    @Value("${RANK_PIC_PATH}")
    private String rank_pic_path; //排名图片实际路径
    
    @Value("${IP_PROPERTIES_PATH}")
    private String ip_Properties_Path;

    
	public String getIp_Properties_Path() {
		return ip_Properties_Path;
	}

	public void setIp_Properties_Path(String ip_Properties_Path) {
		this.ip_Properties_Path = ip_Properties_Path;
	}

	public String getHtml_root_path() {
		return html_root_path;
	}

	public void setHtml_root_path(String html_root_path) {
		this.html_root_path = html_root_path;
	}

	public String getLog4j_path() {
		return log4j_path;
	}

	public void setLog4j_path(String log4j_path) {
		this.log4j_path = log4j_path;
	}

	public String getQuanbu_path() {
		return quanbu_path;
	}

	public void setQuanbu_path(String quanbu_path) {
		this.quanbu_path = quanbu_path;
	}

	public String getReal_rank_pic_path() {
		return real_rank_pic_path;
	}

	public void setReal_rank_pic_path(String real_rank_pic_path) {
		this.real_rank_pic_path = real_rank_pic_path;
	}

	public String getPic_path() {
		return pic_path;
	}

	public void setPic_path(String pic_path) {
		this.pic_path = pic_path;
	}

	public String getRank_pic_path() {
		return rank_pic_path;
	}

	public void setRank_pic_path(String rank_pic_path) {
		this.rank_pic_path = rank_pic_path;
	}

	public String getFile_ip_path() {
		return file_ip_path;
	}

	public void setFile_ip_path(String file_ip_path) {
		this.file_ip_path = file_ip_path;
	}
    
}
