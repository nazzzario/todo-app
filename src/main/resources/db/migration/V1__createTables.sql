create table if not exists users
(
    id         bigserial
        constraint users_pk
            primary key,
    first_name varchar(50) not null,
    last_name  varchar(50) not null,
    password   varchar(255) not null,
    email      varchar(255) not null,
    user_status varchar(255) not null,
    created_at timestamp default now(),
    updated_at timestamp,
    activation_code varchar(255)
);

create unique index if not exists users_email_uindex
    on users (email);

create unique index if not exists users_id_uindex
    on users (id);

create table if not exists roles
(
    id        bigserial
        constraint roles_pk
            primary key,
    role_name varchar(50) not null
);

create table if not exists user_roles
(
    user_id bigint
        constraint user_roles_users_id_fk
            references users,
    role_id bigint
        constraint user_roles_roles_id_fk
            references roles
);

create unique index if not exists roles_role_name_uindex
    on roles (role_name);

create unique index if not exists roles_id_uindex
    on roles (id);

create table if not exists projects
(
    id           bigserial
        constraint projects_pk
            primary key,
    project_name varchar(255) not null,
    owner_id     bigint       not null
        constraint projects_users_id_fk
            references users
            on delete cascade
);

create table if not exists user_projects
(
    project_id bigint
        constraint user_projects_projects_id_fk
            references projects,
    user_id    bigint
        constraint user_projects_users_id_fk
            references users
);

create table if not exists todos
(
    id          bigserial,
    title       varchar(50)  not null,
    description varchar(500),
    todo_status     varchar(255) not null,
    finish_date timestamp,
    project_id  bigint
        constraint todos_projects_id_fk
            references projects,
    creator_id  bigint
        constraint todos_users_id_fk
            references users
);

create unique index if not exists todos_id_uindex
    on todos (id);

create table if not exists todo_coloborants
(
    todo_id bigint
        constraint todo_coloborants_todos_id_fk
            references todos (id),
    user_id bigint
        constraint todo_coloborants_users_id_fk
            references users
);

create table if not exists refresh_tokens
(
    id         bigserial
        primary key,
    token      text   not null
        unique,
    user_id    bigint not null
        references users,
    expires_at timestamp
);
