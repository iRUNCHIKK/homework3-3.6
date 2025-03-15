create table person (
    id serial primary key,
    name text,
    age int,
    is_license boolean
);

create table car (
    id serial primary key,
    brand text,
    model text,
    cost int,
    owner_id bigint references person(id)
);