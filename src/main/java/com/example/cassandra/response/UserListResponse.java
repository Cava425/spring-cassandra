package com.example.cassandra.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserListResponse {

    private Integer pageNum;
    private Integer pageSize;
    private Long total;
    private List<Object> list;

}
