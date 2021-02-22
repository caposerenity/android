package com.example.my_back_end.blImpl.user;

import com.example.my_back_end.bl.user.AccountService;
import com.example.my_back_end.data.user.AccountMapper;
import com.example.my_back_end.enums.positions;
import com.example.my_back_end.po.User;
import com.example.my_back_end.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private static final String ACCOUNT_EXIST = "账号已存在";
    private static final String ACCOUNT_NOT_EXIST = "账号不存在，请先注册";
    private static final  String MD5_STR = "tttt";
    private static final String PASSWORD_ERROR = "密码错误";
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public ResponseVO registerAccount(UserVO userVO) {
        User user=new User();
        BeanUtils.copyProperties(userVO,user);
        user.setPassword(DigestUtils.md5DigestAsHex((user.getPassword()+MD5_STR).getBytes()));
        User user1=accountMapper.getAccountByPhone(userVO.getPhone());
        if(user1==null) {
            accountMapper.createNewAccount(user);
            return ResponseVO.buildSuccess(true);
        }
        else return ResponseVO.buildFailure(ACCOUNT_EXIST);
    }

    @Override
    public ResponseVO login(UserForm userForm) {
        User user = accountMapper.getAccountByPhone(userForm.getPhone());
        if(user==null){
            return ResponseVO.buildFailure(ACCOUNT_NOT_EXIST);
        }
        else if (!user.getPassword().equals(DigestUtils.md5DigestAsHex((userForm.getPassword()+MD5_STR).getBytes()))) {
            return ResponseVO.buildFailure(PASSWORD_ERROR);
        }
        return ResponseVO.buildSuccess(user);
    }

    @Override
    public ResponseVO changePos(PosChangeVO posChangeVO) {
        User user = accountMapper.getAccountByPhone(posChangeVO.getPhone());
        if(user==null){
            return ResponseVO.buildFailure(ACCOUNT_NOT_EXIST);
        }else {
            //TODO 修改任务列表中相关任务
            int user_id=user.getUser_id();
            accountMapper.changePos(user_id,posChangeVO.getNewPos().toString());
            return ResponseVO.buildSuccess(true);
        }
    }

    @Override
    public List<User> filterPosition(positions position) {
        List<User> filteredUsers=accountMapper.filterUser(position.toString());
        return filteredUsers;
        //return ResponseVO.buildSuccess(filteredUsers);
    }

    @Override
    public ResponseVO changePassword(UserInfoVO userInfoVO) {
        User user;
        if(userInfoVO.getUser_id()!=null&&userInfoVO.getUser_id()!=-1){
            user=accountMapper.getAccountById(userInfoVO.getUser_id());
        }
        else if(userInfoVO.getPhone()!=null){
            user=accountMapper.getAccountByPhone(userInfoVO.getPhone());
        }
        else{
            return ResponseVO.buildFailure(ACCOUNT_NOT_EXIST);
        }
        //验证原密码
        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex((userInfoVO.getPassword()+MD5_STR).getBytes()))) {
            return ResponseVO.buildFailure("原密码错误");
        }
        //新密码不得为空
        else if(userInfoVO.getNewPassword()==null){
            return ResponseVO.buildFailure("新密码不得为空");
        }
        else{
            userInfoVO.setNewPassword(DigestUtils.md5DigestAsHex((userInfoVO.getNewPassword()+MD5_STR).getBytes()));
            accountMapper.changePassword(user.getUser_id(),userInfoVO.getNewPassword());
            return ResponseVO.buildSuccess("修改成功");
        }

    }

    @Override
    public ResponseVO changeInfo(UserInfoVO userInfoVO) {
        //貌似只有name能在这改……
        User user;
        if(userInfoVO.getUser_id()!=null){
            user=accountMapper.getAccountById(userInfoVO.getUser_id());
        }
        else if(userInfoVO.getPhone()!=null){
            user=accountMapper.getAccountByPhone(userInfoVO.getPhone());
        }
        else{
            return ResponseVO.buildFailure(ACCOUNT_NOT_EXIST);
        }
        accountMapper.changeName(user.getUser_id(),userInfoVO.getName());
        return ResponseVO.buildSuccess("修改成功");
    }

}
