package com.example.my_back_end.bl.user;

import com.example.my_back_end.enums.positions;
import com.example.my_back_end.po.User;
import com.example.my_back_end.vo.*;

import java.util.List;

public interface AccountService {
    public ResponseVO registerAccount(UserVO userVO);

    public ResponseVO login(UserForm userForm);

    public ResponseVO changePos(PosChangeVO posChangeVO);

    public List<User> filterPosition(positions position);

    public ResponseVO changePassword(UserInfoVO userInfoVO);

    public ResponseVO changeInfo(UserInfoVO userInfoVO);

    public ResponseVO dropUser(String phone);

    public String getNameById(int user_id);

    public String getPhoneById(int user_id);

    }
