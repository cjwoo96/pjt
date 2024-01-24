package com.sample2.redis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Req {

    private String fileKey;
    private String userId;
    private String filterInfo;

    public Req(String fileKey, String userId, String filterInfo) {
        this.fileKey = fileKey;
        this.userId = userId;
        this.filterInfo = filterInfo;
    }

    public Req() {

    }

    public String getFileKey() {
        return fileKey;
    }

    public String getUserId() {
        return userId;
    }

    public String getFilterInfo() {
        return filterInfo;
    }
}