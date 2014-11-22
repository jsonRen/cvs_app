package org.cv.model;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class AppInfo {
	
	private AppRank appRank;
	
	private String imgLogoPath;//本地logoUrl的路径
	
	private String detailUrl;//详细页url
	
    private String appId;

    private String appName;

    private String logoPath;

    private Long enterpriseId;

    private String appType;

    private String sysType;

    private String deviceType;

    private String publisher;

    private Integer isEnable;

    private Date createTime;

    private Date updateTime;

    private String behavior;

    private String remark;

    private String rankPicPath;

    public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public String getImgLogoPath() {
		return imgLogoPath;
	}

	public void setImgLogoPath(String imgLogoPath) {
		this.imgLogoPath = imgLogoPath;
	}

	public AppRank getAppRank() {
		return appRank;
	}

	public void setAppRank(AppRank appRank) {
		this.appRank = appRank;
	}

	public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath == null ? null : logoPath.trim();
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType == null ? null : appType.trim();
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType == null ? null : sysType.trim();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher == null ? null : publisher.trim();
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior == null ? null : behavior.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getRankPicPath() {
        return rankPicPath;
    }

    public void setRankPicPath(String rankPicPath) {
        this.rankPicPath = rankPicPath == null ? null : rankPicPath.trim();
    }
}