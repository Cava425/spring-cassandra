package com.example.cassandra.domain;

import com.datastax.oss.driver.api.core.data.TupleValue;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.net.Inet4Address;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Table(value = "users")
public class User {

    @PrimaryKey
    private Long id;
    private String username;
    private Integer age;
    private Double height;
    private LocalDate birthday;
    @Column("is_vip")
    private Boolean isVip;
    private String salt;
    private Inet4Address ip;
    private List<String> hobbies;
    private Set<String> skills;
    private Map<String, Integer> scores;
    @Column("create_time")
    private Instant createTime;

}
