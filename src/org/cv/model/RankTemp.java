package org.cv.model;

import java.util.Date;

public class RankTemp {
    private Long id;

    private String appId;

    private String appName;

    private Integer thisRank;

    private Integer weekAgoRank;

    private Integer minRank;

    private String logoPath;

    private String rankAppType;

    private String appTypeName;

    private String sysType;

    private String deviceType;

    private String publisher;

    private Date createTime;

    private Date updateTime;

    private Date rankDate;

    private String rankType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public Integer getThisRank() {
        return thisRank;
    }

    public void setThisRank(Integer thisRank) {
        this.thisRank = thisRank;
    }

    public Integer getWeekAgoRank() {
        return weekAgoRank;
    }

    public void setWeekAgoRank(Integer weekAgoRank) {
        this.weekAgoRank = weekAgoRank;
    }

    public Integer getMinRank() {
        return minRank;
    }

    public void setMinRank(Integer minRank) {
        this.minRank = minRank;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath == null ? null : logoPath.trim();
    }

    public String getRankAppType() {
        return rankAppType;
    }

    public void setRankAppType(String rankAppType) {
        this.rankAppType = rankAppType == null ? null : rankAppType.trim();
    }

    public String getAppTypeName() {
        return appTypeName;
    }

    public void setAppTypeName(String appTypeName) {
        this.appTypeName = appTypeName == null ? null : appTypeName.trim();
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

    public Date getRankDate() {
        return rankDate;
    }

    public void setRankDate(Date rankDate) {
        this.rankDate = rankDate;
    }

    public String getRankType() {
        return rankType;
    }

    public void setRankType(String rankType) {
        this.rankType = rankType == null ? null : rankType.trim();
    }
}