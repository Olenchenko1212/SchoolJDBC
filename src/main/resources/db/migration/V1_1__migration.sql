CREATE TABLE IF NOT EXISTS courses
(
    course_id integer NOT NULL,
    course_name character(255) COLLATE pg_catalog."default" NOT NULL,
    course_description character(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT courses_pkey PRIMARY KEY (course_id)
);