package com.example.cassandra.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.net.InetAddress;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private InetAddress ip;
    private List<String> hobbies;
    private Set<String> skills;
    private Map<String, Integer> scores;
    @Column("create_time")
    private Instant createTime;

}
