drop table if exists member CASCADE;

create table member
(   name varchar(255),
    owner varchar(255),
    description varchar(255),
    level bigint generated by default as identity,
    address varchar(255),
    phone varchar(255),
    primary key (level)
);

create table businessTimes
(   level bigint,
    day varchar(10),
    open varchar(10),
    close varchar(10),
    primary key (level),
    FOREIGN KEY (level) REFERENCES member (level)
);

create table holidays
(   level bigint,
    holiday varchar(10),
    primary key (level)
);
