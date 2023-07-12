
--
CREATE TABLE IF NOT EXISTS users (
                                     id bigint primary key,
                                     username text,
                                     age int,
                                     height double,
                                     birthday date,
                                     is_vip boolean,
                                     salt uuid,
                                     ip inet,
                                     hobbies list<text>,
                                     skills set<text>,
                                     scores map<text, int>,
                                     create_time timestamp,
) with comment = 'user info table';


insert into users(id,username,age,height,birthday,is_vip,salt,ip,hobbies,skills,scores,create_time)values(1,'jack',26,135.5,'1996-10-26',true,uuid(),'192.168.1.1',['java', 'iOS'],{'eat', 'drink'},{'china': 80, 'english': 90},dateof(now()));
insert into users(id,username,age,height,birthday,is_vip,salt,ip,hobbies,skills,scores,create_time)values(2,'alice',26,135.5,'1997-10-26',true,uuid(),'192.168.1.1',['java', 'iOS'],{'eat', 'drink'},{'china': 80, 'english': 90},dateof(now()));
insert into users(id,username,age,height,birthday,is_vip,salt,ip,hobbies,skills,scores,create_time)values(3,'bob',26,135.5,'1998-10-26',true,uuid(),'192.168.1.1',['java', 'iOS'],{'eat', 'drink'},{'china': 80, 'english': 90},dateof(now()));
