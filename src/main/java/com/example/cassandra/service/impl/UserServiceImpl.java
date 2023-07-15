package com.example.cassandra.service.impl;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;
import com.example.cassandra.domain.User;
import com.example.cassandra.response.UserListResponse;
import com.example.cassandra.service.UserService;
import com.example.cassandra.utils.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final String count = "SELECT COUNT(1) AS total FROM users";

    private final String query = "SELECT id,username,age,height,birthday,is_vip,salt,ip,hobbies,skills,scores,create_time FROM users";

    private final Integer FETCH_SIZE = 10 * 10;
    @Resource
    private CqlSession session;

    @Override
    public UserListResponse findAllPaged(Integer pageNum, Integer pageSize) {

        long total = 0;
        ResultSet resultSet = session.execute(count);
        if(resultSet.iterator().hasNext()){
            for(Row row : resultSet){
                total = row.getLong("total");
                break;
            }
        }

        if(total <= 0){
            return UserListResponse.builder().build();
        }

        PreparedStatement prepare = session.prepare(query);
        Statement statement = prepare.bind().setPageSize(FETCH_SIZE);


        ResultSet rs = PageUtil.skipTo(session, statement, pageNum, pageSize);

        List<Object> users;
        boolean empty = !rs.iterator().hasNext();
        if (empty) {
            users = Collections.emptyList();
        } else {
            int remaining = pageSize;
            users = new ArrayList<>(remaining);
            for (Row row : rs) {
                User user = User.builder()
                        .id(row.getLong("id"))
                        .age(row.getInt("age"))
                        .username(row.getString("username"))
                        .birthday(row.getLocalDate("birthday"))
                        .height(row.getDouble("height"))
                        .isVip(row.getBoolean("is_vip"))
                        .salt(row.getUuid("salt").toString())
                        .ip(row.getInetAddress("ip"))
                        .hobbies(row.getList("hobbies", String.class))
                        .skills(row.getSet("skills", String.class))
                        .scores(row.getMap("scores", String.class, Integer.class))
                        .createTime(row.getInstant("create_time")).build();
                users.add(user);

                if (--remaining == 0) {
                    break;
                }
            }
        }

        return UserListResponse.builder().pageNum(pageNum).pageSize(pageSize).total(total).list(users).build();
    }

}
