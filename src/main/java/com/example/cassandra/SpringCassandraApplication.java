package com.example.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication
public class SpringCassandraApplication implements CommandLineRunner {



    public static void main(String[] args) {
        SpringApplication.run(SpringCassandraApplication.class, args);
    }


    @Autowired
    private CqlSession session;

    @Override
    public void run(String... args) throws Exception {
        createSchema(session);
        populateSchema(session);
    }



    private static void createSchema(@Autowired CqlSession session) {

        session.execute("DROP KEYSPACE IF EXISTS for_test");
        session.execute(
                "CREATE KEYSPACE IF NOT EXISTS for_test "
                        + "WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1}");

        session.execute("DROP TABLE IF EXISTS for_test.users");
        session.execute(
                "CREATE TABLE IF NOT EXISTS for_test.users (" +
                        "id bigint primary key," +
                        "username text," +
                        "age int," +
                        "height double," +
                        "birthday date," +
                        "is_vip boolean," +
                        "salt uuid," +
                        "ip inet," +
                        "hobbies list<text>," +
                        "skills set<text>," +
                        "scores map<text, int>," +
                        "create_time timestamp," +
                        ") with comment = 'user info table';");
    }


    private static void populateSchema(CqlSession session) throws UnknownHostException {
        PreparedStatement prepare =
                session.prepare(
                        "INSERT INTO for_test.users(id,username,age,height,birthday,is_vip,salt,ip,hobbies,skills,scores,create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        // 3 users
        for (long i = 0; i < 89; i++) {
            // 49 videos each
            session.execute(
                    prepare.bind(
                            i, "user " + i, 18, 178.8, LocalDate.of(1998, 10,24), false, UUID.randomUUID(), Inet4Address.getByName("192.168.0.1"), Arrays.asList("java", "iOS"), new HashSet<String>() {{ add("eat"); add("drink"); }}, new HashMap<String, Integer>(){{ put("china", 80); put("english", 90); }}, Instant.now()));
        }
    }
}
