package org.cv.model;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class AppRank {
    private Long id;

    private String appId;

    private String rankDate;

    private String rankAppType;

    private String rank;

    private String appListType;

    private String downloadNum;

    private String downloadUnit;

    private Date createTime;

    private String remark;

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
        this.appId = appId;
    }

    public String getRankDate() {
        return rankDate;
    }

    public void setRankDate(String rankDate) {
        this.rankDate = rankDate;
    }

    public String getRankAppType() {
        return rankAppType;
    }

    public void setRankAppType(String rankAppType) {
        this.rankAppType = rankAppType == null ? null : rankAppType.trim();
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getAppListType() {
        return appListType;
    }

    public void setAppListType(String appListType) {
        this.appListType = appListType;
    }

    public String getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(String downloadNum) {
        this.downloadNum = downloadNum;
    }

    public String getDownloadUnit() {
        return downloadUnit;
    }

    public void setDownloadUnit(String downloadUnit) {
        this.downloadUnit = downloadUnit;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}