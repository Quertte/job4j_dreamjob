CREATE table post (
    id serial PRIMARY KEY,
    name TEXT,
    city_id int,
    visible boolean,
    description TEXT,
    created timestamp
)