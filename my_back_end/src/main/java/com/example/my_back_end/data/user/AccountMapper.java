package com.example.my_back_end.data.user;


import com.example.my_back_end.enums.positions;
import com.example.my_back_end.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AccountMapper {
    User getAccountByPhone(@Param("phone") String phone);

    User getAccountById(@Param("user_id") int user_id);

    void createNewAccount(User user);

    void changePos(@Param("user_id")int user_id,@Param("newPos") String newPos,@Param("name") String name);

    List<User> filterUser(@Param("position") String position);

    void changePassword(@Param("user_id")int user_id, @Param("newPassword")String newPassword);

    void changeName(@Param("user_id")int user_id, @Param("name")String name);

    void dropUser(@Param("phone")String phone);
}
