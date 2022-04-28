CREATE table if not exists post (
    id serial PRIMARY KEY,
    name TEXT,
    city_id int,
    visible boolean,
    description TEXT,
    created timestamp
);

CREATE TABLE if not exists candidate (
    id serial primary key,
    name TEXT,
    photo bytea
);

CREATE TABLE if not exists users(
    id SERIAL PRIMARY KEY,
    name TEXT,
    email VARCHAR unique,
    password TEXT
);