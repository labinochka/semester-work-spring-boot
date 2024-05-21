create table if not exists beer
(
    uuid    uuid default uuid_generate_v4() not null
        constraint beer_uuid_pk
            primary key,
    sort    varchar                         not null,
    type    varchar                         not null
        constraint beer_type_uc
            unique,
    image   varchar                         not null,
    content varchar                         not null
);

create table if not exists role
(
    id   serial
        constraint role_id_pk
            primary key,
    name varchar not null
);

create table if not exists account
(
    uuid     uuid    default uuid_generate_v4()                                                            not null
        constraint account_uuid_pk
            primary key,
    username varchar                                                                                       not null
        constraint account_username_uq
            unique,
    name     varchar                                                                                       not null,
    lastname varchar                                                                                       not null,
    birthday timestamp(6)                                                                                  not null,
    email    varchar                                                                                       not null
        constraint account_email_uc
            unique,
    password varchar                                                                                       not null,
    avatar   varchar default 'https://mirtex.ru/wp-content/uploads/2023/04/unnamed.jpg'::character varying not null,
    about    varchar default '-'::character varying                                                        not null,
    role_id  integer default 2                                                                             not null
        constraint account_role_fk
            references role,
    verified boolean default false
);

create table if not exists post
(
    uuid                uuid default uuid_generate_v4() not null
        constraint post_uuid_pk
            primary key,
    author_uuid         uuid                            not null
        constraint post_author_fk
            references account,
    title               varchar                         not null,
    content             varchar                         not null,
    image               varchar                         not null,
    date_of_publication timestamp(6)                    not null
);

create table if not exists comment
(
    uuid                uuid default uuid_generate_v4() not null
        constraint comment_uuid_pk
            primary key,
    author_uuid         uuid                            not null
        constraint comment_author_uuid
            references account,
    post_uuid           uuid                            not null
        constraint comment_post_uuid
            references post,
    content             varchar                         not null,
    date_of_publication timestamp(6)                    not null
);

create table if not exists verification_token
(
    uuid         uuid default uuid_generate_v4() not null
        constraint verification_token_uuid_pk
            primary key,
    token        varchar                         not null,
    account_uuid uuid                            not null
        constraint verification_token_account_fr
            references account,
    expiry_date  timestamp(6)                    not null
);


