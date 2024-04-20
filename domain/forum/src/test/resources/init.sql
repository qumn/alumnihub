create table "biz_post"
(
    id         serial8 primary key,
    title      varchar(20)    default ''    not null,
    content    text           default ''    not null,
    tags       varchar(20)[]  default '{}'  not null,
    imgs       varchar(100)[] default '{}'  not null,
    thumbup_by bigint[]       default '{}',
    shared_by  bigint[]       default '{}',
    deleted    boolean        default false,
    created_by bigint         default 0,
    created_at timestamp      default now() not null,
    updated_at timestamp      default now() not null
);