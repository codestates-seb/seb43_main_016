create table member (
    id               bigint auto_increment,
    nick_name        varchar(100) not null,
    profile_image    blob null,
    password         varchar(100) not null,
    grade            varchar(50),
    roles            varchar(50),
    about_me         TEXT,
    with_me          TEXT,
    member_status    varchar(50),
    modified_at      timestamp,
    created_at       timestamp,

    fk_location_info_id bigint,

    constraint pk_member_id primary key (id)
);

create table studygroup_join (
  id            bigint auto_increment,
  member_id     bigint,
  studygroup_id bigint,
  approve       boolean,

  constraint pk_studygroup_join_id primary key (id)
);

create table studygroup (
    id                bigint auto_increment,
    study_name        varchar (50),
    introduction      TEXT,
    maxClassmateCount int,
    platform          varchar(50),
    start_day         timestamp,
    end_day           timestamp,
    modified_at       timestamp,
    created_at        timestamp,
    member_id         bigint,

    constraint pk_studygroup_id primary key (id)
);

create table study_post_comment
(
    id            bigint auto_increment,
    content       varchar(200),
    modified_at   timestamp,
    created_at    timestamp,
    member_id     bigint,
    studygroup_id bigint,

    constraint pk_study_post_comment_id primary key (id)
);

create table search_tag (
    id            bigint auto_increment,
    studygroup_id bigint,
    tag_key       varchar(20),
    tag_value     varchar(100),

    constraint pk_search_tag_id primary key (id)
);

create table time_schedule (
    id            bigint auto_increment,
    studygroup_id bigint,
    day           varchar(10),
    start_time    timestamp,
    end_time      timestamp,

    constraint pk_time_schedule_id primary key (id)
);

create table private_time_schedule (
    id         bigint auto_increment,
    member_id  bigint,
    title      varchar(100),
    day        varchar(10),
    start_time timestamp,
    end_time   timestamp,

    constraint pk_private_time_schedule_id primary key (id)
);

create table member_location (
    id        bigint auto_increment,
    address   varchar(200),
    latitude  float,
    longitude float,
    member_id bigint,

    constraint pk_member_location_id primary key (id)
);

create table study_location (
    id            bigint auto_increment,
    address       varchar(200),
    latitude      float,
    longitude     float,
    studygroup_id bigint,

    constraint pk_study_location_id primary key (id)
);

alter table study_post_comment
    add constraint fk_member_id
        foreign key (member_id) references member (id);
alter table study_post_comment
    add constraint fk_studygroup_id
        foreign key (studygroup_id) references studygroup (id);
alter table studygroup_join
    add constraint fk_studygroup_id
        foreign key (studygroup_id) references studygroup (id);
alter table studygroup_join
    add constraint fk_member_id
        foreign key (member_id) references member (id);
alter table studygroup
    add constraint fk_member_id
        foreign key (member_id) references member (id);
alter table time_schedule
    add constraint fk_studygroup_id
        foreign key (studygroup_id) references studygroup (id);
alter table search_tag
    add constraint fk_studygroup_id
        foreign key (studygroup_id) references studygroup (id);
alter table member_location
    add constraint fk_member_id
        foreign key (member_id) references member (id);
alter table study_location
    add constraint fk_studygroup_id
        foreign key (studygroup_id) references studygroup (id);
alter table private_time_schedule
    add constraint fk_member_id
        foreign key (member_id) references member (id);