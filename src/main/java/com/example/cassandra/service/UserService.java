package com.example.cassandra.service;

import com.example.cassandra.response.UserListResponse;

public interface UserService {

    UserListResponse findAllPaged(Integer pageNum, Integer pageSize);

}
