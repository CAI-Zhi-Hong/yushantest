package com.yushantest.demo.dto;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;

public class SelectLikeListParamsDto  {
    @NotNull(message = "createdDate may not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String uid;    
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
}
