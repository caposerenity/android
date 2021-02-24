package com.example.my_back_end.controller.user;


import com.example.my_back_end.bl.user.AccountService;
import com.example.my_back_end.enums.positions;
import com.example.my_back_end.po.User;
import com.example.my_back_end.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/user")
public class AccountController  {
    @Autowired
    private AccountService accountService;

    @GetMapping("/hello")
    public ResponseVO getUserInfo() {
        System.out.print(1);
        return ResponseVO.buildSuccess("123");
    }

    @PostMapping("/register")
    public ResponseVO registerAccount(@RequestBody UserVO userVO) {
        return accountService.registerAccount(userVO);
    }

    @PostMapping("/login")
    public ResponseVO login(@RequestBody UserForm userForm) {
        return accountService.login(userForm);
    }

    @GetMapping("/{phone}/drop")
    public ResponseVO dropUser(@PathVariable String phone) {
        return accountService.dropUser(phone);
    }
    @PostMapping("/changePos")
    public ResponseVO changePos(@RequestBody PosChangeVO posChangeVO) {
        return accountService.changePos(posChangeVO);
    }

    @GetMapping("/{position}/filterPosition")
    public List<User> filterPosition(@PathVariable positions position) {
       return accountService.filterPosition(position);
    }

    @GetMapping("/{user_id}/getNameById")
    public String getNameById(@PathVariable int user_id) {
        return accountService.getNameById(user_id);
    }

    @PostMapping("/changePassword")
    public ResponseVO changePassword(@RequestBody UserInfoVO userInfoVO) {
        return accountService.changePassword(userInfoVO);
    }

    @PostMapping("/changeInfo")
    public ResponseVO changeInfo(@RequestBody UserInfoVO userInfoVO) {
        return accountService.changeInfo(userInfoVO);
    }
}
