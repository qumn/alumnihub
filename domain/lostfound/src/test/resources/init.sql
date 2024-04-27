create type lostfound_status as enum ('Missing', 'Found', 'Returning', 'Returned');

create table biz_lostfound
(
    "id"           serial8                                              not null primary key,
    "publisher_id" bigint                                               not null,
    "owner_id"     bigint,
    "status"       lostfound_status default 'Missing'::lostfound_status not null,
    "location"     varchar(80)                                          not null,
    "item_desc"    varchar(80)                                          not null,
    "item_imgs"    varchar(80)[]    default '{}'                        not null,
    "pickup_time"  timestamp        default now()                       not null,
    "created_at"   timestamp        default now()                       not null,
    "updated_at"   timestamp        default now()                       not null
);

create table biz_lostfound_question
(
    "id"      serial8      not null primary key,
    "lf_id"   bigint       not null,
    "order"   int          not null,
    "content" varchar(100) not null,
    "answer"  varchar(100) not null
);