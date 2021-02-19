package com.example.my_back_end.bl.user;

import com.example.my_back_end.enums.positions;
import com.example.my_back_end.vo.*;

public interface AccountService {
    public ResponseVO registerAccount(UserVO userVO);

    public ResponseVO login(UserForm userForm);

    public ResponseVO changePos(PosChangeVO posChangeVO);

    public ResponseVO filterPosition(positions position);

    public ResponseVO changePassword(UserInfoVO userInfoVO);

    public ResponseVO changeInfo(UserInfoVO userInfoVO);
}
