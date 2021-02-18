package com.example.my_back_end.controller.user;


import com.example.my_back_end.vo.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/user")
public class AccountController  {
    @GetMapping("/hello")
    public ResponseVO getUserInfo() {
        System.out.print(1);
        return ResponseVO.buildSuccess("123");
    }

}
