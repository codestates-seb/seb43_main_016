create table if not exists member
(
    id            bigint auto_increment,
    uuid          varchar(50),
    email         varchar(100),
    nick_name     varchar(100),
    profile_image blob,
    password      varchar(100),
    address       varchar(200),
    grade         varchar(50),
    about_me      TEXT,
    with_me       TEXT,
    member_status varchar(50),
    provider      varchar(50),
    modified_at   timestamp,
    created_at    timestamp,

    constraint pk_member_id primary key (id),
    constraint uk_member_email unique (email),
    constraint uk_member_nick_name unique (nick_name)
);

create table if not exists member_roles
(
    member_id bigint not null,
    roles     varchar(30)
);

create table if not exists studygroup_join
(
    id            bigint auto_increment,
    is_approved   boolean default false,

    member_id     bigint,
    studygroup_id bigint,

    constraint pk_studygroup_join_id primary key (id)
);

create table if not exists studygroup
(
    id                 bigint auto_increment,
    study_name         varchar(50),
    studygroup_image   blob,
    address            varchar(200),
    days_of_week       varchar(50),
    study_period_start varchar(100),
    study_period_end   varchar(100),
    study_time_start   varchar(100),
    study_time_end     varchar(100),
    introduction       TEXT,
    member_count_min   int,
    member_count_max   int,
    platform           varchar(50),
    is_requited        boolean,
    modified_at        timestamp,
    created_at         timestamp,

    leader_member_id       bigint,

    constraint pk_studygroup_id primary key (id)
);

create table if not exists search_tag
(
    id            bigint auto_increment,
    tag_key       varchar(20),
    tag_value     varchar(100),

    studygroup_id bigint,

    constraint pk_search_tag_id primary key (id)
);

create table if not exists studygroup_post_comment
(
    id            bigint auto_increment,
    content       varchar(200),
    modified_at   timestamp,
    created_at    timestamp,

    studygroup_id bigint,
    member_id     bigint,

    constraint pk_studygroup_post_comment_id primary key (id)
);

create table if not exists time_schedule
(
    id            bigint auto_increment,
    title         varchar(100),
    content       varchar(200),
    start_time    timestamp,
    end_time      timestamp,

    member_id     bigint,
    studygroup_id bigint,

    constraint pk_time_schedule_id primary key (id)
);

alter table member_roles
    add constraint fk_member_roles_member_id
        foreign key (member_id) references member (id);

alter table studygroup_post_comment
    add constraint fk_studygroup_post_comment_member_id
        foreign key (member_id) references member (id);
alter table studygroup_post_comment
    add constraint fk_studygroup_post_comment_studygroup_id
        foreign key (studygroup_id) references studygroup (id);

alter table studygroup_join
    add constraint fk_studygroup_join_member_id
        foreign key (member_id) references member (id);
alter table studygroup_join
    add constraint fk_studygroup_join_studygroup_id
        foreign key (studygroup_id) references studygroup (id);

alter table search_tag
    add constraint fk_search_tag_studygroup_id
        foreign key (studygroup_id) references studygroup (id);

alter table time_schedule
    add constraint fk_time_schedule_member_id
        foreign key (member_id) references member (id);
alter table time_schedule
    add constraint fk_time_schedule_studygroup_id
        foreign key (studygroup_id) references studygroup (id);

alter table studygroup
    add constraint fk_studygroup_leader_member_id
        foreign key (leader_member_id) references member (id);