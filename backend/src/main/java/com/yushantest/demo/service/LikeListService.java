package com.yushantest.demo.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yushantest.demo.dto.InsertLikeListParamsDto;
import com.yushantest.demo.dto.SelectLikeListDto;
import com.yushantest.demo.dto.SelectLikeListReturnDto;
import com.yushantest.demo.dto.UpdateLikeListParamsDto;
import com.yushantest.demo.entities.User;
import com.yushantest.demo.repository.LikeListRepo;

@Service
public class LikeListService{
    @Autowired
    private LikeListRepo likeListRepo;


    // correspend to InsertLikeListParamsModel
    public Integer insertUserLikeListFn(
        InsertLikeListParamsDto params
    ) {
        Integer newIndex = likeListRepo.Insert_User_Like_List(
            params.getUid(), params.getProductName(), 
            params.getProductPrice(), 
            params.getProductFeeRate(), 
            params.getDefaultAccount(), 
            params.getOrderName()
        );
        return newIndex; 
    }


    // correspend to SelectLikeListParamsModel
    public SelectLikeListReturnDto selectUserLikeListFn(
        String uid
    ) {
        List<SelectLikeListDto> selectLikeListDtoArray = new ArrayList<>();        
        List<Object[]> rawResult = likeListRepo.Select_User_Like_List(uid);   
        User user = new User() ;
        for (Object[] row : rawResult) {
            SelectLikeListDto dto = new SelectLikeListDto(row);
            selectLikeListDtoArray.add(dto);
        }  


        List<Object[]> userData = likeListRepo.Select_User(uid);
        if (userData.size() == 1){
            user = new User(userData.get(0));
        }       

        SelectLikeListReturnDto selectLikeListReturnDto = new SelectLikeListReturnDto(
            selectLikeListDtoArray,
            user.getAccount(),
            user.getEmail()
        );

        return selectLikeListReturnDto;      
    }


    public void DeleteUserLikeListFn(String uid, Integer likeListSN) {
        likeListRepo.Delete_User_Like_List(uid, likeListSN);      
    }


    public void UpdateUserLikeListFn(UpdateLikeListParamsDto params) {
        likeListRepo.Update_User_Like_List(
            params.getUid(), 
            params.getLikeListSN(), 
            params.getAccount(), 
            params.getOrderName(), 
            params.getFeeRate(),
            params.getPrice(), 
            params.getProductName());
    }
}
