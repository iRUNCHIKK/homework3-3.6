-- liquibase formatted sql

-- changeset ibutkova:1

create index student_name_index on student (name);

-- changeset ibutkova:2

create index faculty_name_and_color_index on faculty (name, color);