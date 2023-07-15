package com.example.cassandra.controller;

import com.example.cassandra.repository.UserRepository;
import com.example.cassandra.response.UserListResponse;
import com.example.cassandra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jws.WebResult;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Resource
    private UserService userService;


    @GetMapping
    public Object findAll(){
        return userRepository.findAll();
    }


    @GetMapping("/list")
    public UserListResponse findAllPaged(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize){
        return userService.findAllPaged(pageNum, pageSize);
    }
}
