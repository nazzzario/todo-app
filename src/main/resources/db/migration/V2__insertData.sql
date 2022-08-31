insert into roles(role_name)
values ('ROLE_ADMIN'),
       ('ROLE_USER');

insert into users(first_name, last_name, password, email)
values ('Nazar',
        'Krasnovoronka',
        '$2a$10$ehBcOQMwWawO9Ei05EAdMOyNgrwGp3W0BZBwYMS9ZCUH81bXWXoca',
        'krasnovoronka@email.com'),
       ('Ivan', 'Mazepa', '$2a$10$ehBcOQMwWawO9Ei05EAdMOyNgrwGp3W0BZBwYMS9ZCUH81bXWXoca',
        'mazepa@email.com');

insert into user_roles
values (1, 1),
       (2, 2);

insert into projects (project_name, owner_id)
values ('Test Project', 1);

insert into user_projects(project_id, user_id)
values (1, 2);

insert into todos (title, description, status, finish_date, project_id, creator_id)
values ('Test todo', 'i`m test todo', 'CREATED', '2023-01-08', 1, 1);

insert into todo_coloborants(todo_id, user_id)
values (1, 2);

