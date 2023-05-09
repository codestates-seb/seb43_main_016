create table if not exists edusync.calendar_info
(
    id             bigint auto_increment,
    start          datetime(6) null,
    end            datetime(6) null,

    constraint pk_calendar_info_id primary key (id)
);

create table if not exists edusync.location_info
(
    id        bigint auto_increment,
    address   varchar(200) not null,
    latitude  float        null,
    longitude float        null,

    constraint pk_location_info_id primary key (id)
);

create table if not exists edusync.member
(
    id            bigint auto_increment,
    email         varchar(100) not null,
    nick_name     varchar(30)  not null,
    password      varchar(100) null,
    grade         varchar(50)  null,
    about_me      text         null,
    with_me       text         null,
    member_status varchar(20)  not null,
    profile_image blob         null,
    created_at    datetime(6)  null,
    modified_at   datetime(6)  null,

    constraint pk_member_id
        primary key (id),
    constraint uk_member_nick_name
        unique (nick_name),
    constraint uk_member_email
        unique (email)
);

create table if not exists edusync.member_roles
(
    member_id   bigint      not null,
    roles       varchar(30) null,

    constraint fk_member_roles_member_id
        foreign key (member_id) references edusync.member (id)
);

create table if not exists edusync.classmate
(
    id              bigint auto_increment,

    location_info   bigint null,
    member_id       bigint null,
    studygroup_id   bigint null,
    classmate_id    bigint null,

    constraint pk_classmate_id
        primary key (id),
    constraint fk_classmate_location_info
        foreign key (location_info) references edusync.location_info (id),
    constraint fk_classmate_member_id
        foreign key (member_id) references edusync.member (id)
);

create table if not exists edusync.studygroup
(
    id                  bigint auto_increment,
    introduction        text        not null,
    max_classmate_count int         null,
    platform            varchar(50) not null,
    study_name          varchar(50) not null,
    created_at          datetime(6) null,
    modified_at         datetime(6) null,

    calendar_info       bigint      null,
    location_info       bigint      null,
    classmate_leader_id bigint      null,

    constraint pk_studygroup_id
        primary key (id),
    constraint fk_studygroup_location_info
        foreign key (location_info) references edusync.location_info (id),
    constraint fk_studygroup_calendar_info
        foreign key (calendar_info) references edusync.calendar_info (id)
);

create table if not exists edusync.calendar_classmate
(
    id           bigint auto_increment,
    classmate_id bigint null,
    fk_time_schedules_id bigint null,

    constraint pk_calendar_classmate_id
        primary key (id),
    constraint fk_calendar_classmate_classmate_id
        foreign key (classmate_id) references edusync.classmate (id)
);

create table if not exists edusync.calendar_studygroup
(
    id            bigint auto_increment,
    studygroup    bigint null,

    constraint pk_calendar_studygroup_id
        primary key (id),
    constraint fk_calendar_studygroup_studygroup
        foreign key (studygroup) references edusync.studygroup (id)
);

create table if not exists edusync.search_tag
(
    id               bigint auto_increment,
    tag_key          varchar(20)  null,
    tag_value        varchar(100) null,

    studygroup_id    bigint       null,

    constraint pk_search_tag_id
        primary key (id),
    constraint fk_search_tag_studygroup_id
        foreign key (studygroup_id) references edusync.studygroup (id)
);

create table if not exists edusync.studygroup_join
(
    id            bigint auto_increment,

    classmate_id  bigint null,
    studygroup_id bigint null,

    constraint pk_studygroup_join_id
        primary key (id),
    constraint fk_studygroup_join_classmate_id
        foreign key (studygroup_id) references edusync.studygroup (id),
    constraint fk_studygroup_join_studygroup_id
        foreign key (classmate_id) references edusync.classmate (id)
);

create table if not exists edusync.studygroup_post_comment
(
    id               bigint auto_increment,
    content          varchar(200) null,
    created_at       datetime(6)  null,
    modified_at      datetime(6)  null,

    member_id        bigint       null,
    studygroup_id    bigint       null,

    constraint pk_studygroup_post_comment_id
        primary key (id),
    constraint fk_studygroup_post_comment_member_id
        foreign key (member_id) references edusync.member (id),
    constraint fk_studygroup_post_comment_studygroup_id
        foreign key (studygroup_id) references edusync.studygroup (id)
);

create table if not exists edusync.time_schedule
(
    id                     bigint auto_increment,
    end_time               datetime(6) null,
    start_time             datetime(6) null,
    created_at             datetime(6) null,
    modified_at            datetime(6) null,

    studygroup_id          bigint      null,
    calendar_studygroup_id bigint      null,

    constraint pk_time_schedule_id
        primary key (id),
    constraint fk_time_schedule_studygroup_id
        foreign key (studygroup_id) references edusync.studygroup (id),
    constraint fk_time_schedule_calendar_studygroup_id
        foreign key (calendar_studygroup_id) references edusync.calendar_studygroup (id)
);
