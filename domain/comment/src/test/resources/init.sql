create type subject_type as enum ('Trade', 'Forum', 'LostFound');

create table biz_comment
(
    id           serial8 primary key,
    parent_id    bigint    default null,
    commented_by bigint                  not null,
    subject_type subject_type            not null,
    subject_id   serial8                 not null,
    content      varchar(200)            not null,
    created_at   timestamp default now() not null
);

create table biz_comment_like
(
    cid        bigint,
    uid        bigint,
    created_at timestamp default now() not null,
    primary key (cid, uid)
)