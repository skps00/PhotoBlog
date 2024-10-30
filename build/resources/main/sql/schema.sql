drop table if exists comment;
drop table if exists photo;
drop table if exists user_roles;
drop table if exists users;

CREATE TABLE  if not exists users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    description varchar(255),
    tel VARCHAR(50),
    email VARCHAR(50),
    create_time timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    update_time timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (username)
);
CREATE TABLE IF NOT EXISTS user_roles (
    user_role_id INTEGER GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    create_time timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    update_time timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (user_role_id),
    FOREIGN KEY (username) REFERENCES users(username)
);
create table if not exists photo (
    id uuid default random_uuid() not null,
    description varchar(255),  name varchar(255),
    content blob,
    content_type varchar(255),
    filename varchar(255),
    create_time timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    update_time timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    primary key (id),
    FOREIGN KEY (name) REFERENCES users(username)
);
create table if not exists comment (
    id INTEGER GENERATED ALWAYS AS IDENTITY,
    content varchar(255),
    photo_id uuid,
    username VARCHAR(50),
    create_time timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    update_time timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    primary key (id),
    FOREIGN KEY (username) REFERENCES users(username),
    FOREIGN KEY (photo_id) REFERENCES photo(id)
);
