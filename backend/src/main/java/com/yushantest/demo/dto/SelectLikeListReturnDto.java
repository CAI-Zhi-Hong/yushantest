package com.yushantest.demo.dto;
import java.util.List;


public class SelectLikeListReturnDto {
    private String userAccount;
    private String userEmail;
    private String userName;
    private List<SelectLikeListDto> userPortfolios;
    
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
    
   
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public List<SelectLikeListDto> getUserPortfolios() {
        return userPortfolios;
    }
    
    public void setUserPortfolios(List<SelectLikeListDto> userPortfolios) {
        this.userPortfolios = userPortfolios;
    }
    
    public SelectLikeListReturnDto () {}
    public SelectLikeListReturnDto( List<SelectLikeListDto> userPortfolios, String userAccount, String userEmail, String userName) {
        this.userPortfolios = userPortfolios;
        this.userAccount = userAccount;
        this.userEmail = userEmail;
        this.userName = userName;
    }
   
}
