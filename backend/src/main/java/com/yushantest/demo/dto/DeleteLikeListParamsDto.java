package com.yushantest.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class DeleteLikeListParamsDto  {
    @NotNull(message = "uid may not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String uid;    
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    @NotNull(message = "likeListSN may not be null")
    @Min(value = 0, message = "likeListSN Can't Be negative")    
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer likeListSN;
    
    public Integer getLikeListSN() {
        return likeListSN;
    }

    public void setLikeListSN(Integer likeListSN) {
        this.likeListSN = likeListSN;
    }
}
