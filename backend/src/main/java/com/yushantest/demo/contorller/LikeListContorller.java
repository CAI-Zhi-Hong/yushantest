package com.yushantest.demo.contorller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yushantest.demo.dto.DeleteLikeListParamsDto;
import com.yushantest.demo.dto.InsertLikeListParamsDto;
import com.yushantest.demo.dto.SelectLikeListParamsDto;
import com.yushantest.demo.dto.UpdateLikeListParamsDto;
import com.yushantest.demo.service.LikeListService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/likelist")
public class LikeListContorller {
    @Autowired
    private LikeListService likeListService;

    @PostMapping("/user-insert-like-list")
    public ResponseEntity<?> userInsertLikeList(
        @RequestBody @Valid InsertLikeListParamsDto params,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            return new ResponseEntity<>("Operation failed: parameter error" + errors.toString(), HttpStatus.BAD_REQUEST);
        }

        try {
            Integer index = likeListService.insertUserLikeListFn(params);
            return new ResponseEntity<Integer>(index, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Operation failed: parameter error" + e.getMessage(), HttpStatus.valueOf(402));
        }
        
    }

    @PostMapping("/user-select-like-list")
    public ResponseEntity<?> userSelectLikeList(
        @RequestBody @Valid SelectLikeListParamsDto params,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            return new ResponseEntity<>("Operation failed: parameter error" + errors.toString(), HttpStatus.BAD_REQUEST);
        }

        try {
            return new ResponseEntity<>(likeListService.selectUserLikeListFn(params.getUid()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Operation failed: parameter error" + e.getMessage(), HttpStatus.valueOf(402));
        }
        
    }
    
    @PostMapping("/user-delete-like-list")
    public ResponseEntity<String> userDeleteLikeList(
        @RequestBody @Valid DeleteLikeListParamsDto params,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            return new ResponseEntity<>("Operation failed: parameter error" + errors.toString(), HttpStatus.BAD_REQUEST);
        }

        try {
            likeListService.DeleteUserLikeListFn(params.getUid(), params.getLikeListSN());
            return new ResponseEntity<>("Operation executed successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Operation failed: parameter error" + e.getMessage(), HttpStatus.valueOf(402));
        }
    }


    @PostMapping("/user-update-like-list")
    public ResponseEntity<String> userUpdateLikeList(
        @RequestBody @Valid UpdateLikeListParamsDto params,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            return new ResponseEntity<>("Operation failed: parameter error" + errors.toString(), HttpStatus.BAD_REQUEST);
        }

        try {
            likeListService.UpdateUserLikeListFn(params);
            return new ResponseEntity<>("Operation executed successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Operation failed: parameter error" + e.getMessage(), HttpStatus.valueOf(402));
        }
    }

    
}
