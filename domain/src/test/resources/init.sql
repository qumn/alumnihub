create type trade_status as enum ('Pending', 'Reserved', 'Completed');
create type gender as enum ('FEMALE', 'MALE', 'UNKNOWN');

create table sys_user
(
    uid        serial8 primary key,
    name       varchar(60)                         not null,
    gender     gender    default 'UNKNOWN'::gender not null,
    birth_day  timestamp,
    phone      varchar(30),
    email      varchar(60),
    dept_id    bigint,
    created_by bigint    default 0,
    created_at timestamp default now()             not null,
    updated_at timestamp default now()             not null,
    deleted    boolean   default false             not null
);

create table biz_trade
(
    id                serial8 primary key,
    status            trade_status default 'Pending' not null,
    seller_id         bigint                         not null,
    goods             jsonb                          not null,
    buyer_id          bigint,
    desired_buyer_ids bigint[]     default '{}'      not null,
    reserved_at       timestamp    default null,
    completed_at      timestamp    default null,
    created_at        timestamp    default now()     not null,
    updated_at        timestamp    default now()     not null
);
