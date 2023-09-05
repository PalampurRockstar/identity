CREATE TABLE public.app_user (
	id varchar(255) NOT NULL,
	created_date varchar(255) NULL,
	date_of_birth varchar(255) NULL,
	first_name varchar(255) NULL,
	last_name varchar(255) NULL,
	"password" varchar(255) NULL,
	updated_date varchar(255) NULL,
	username varchar(255) NULL,
	created_at varchar(255) NULL,
	"type" int4 NULL,
	updated_at varchar(255) NULL,
	CONSTRAINT app_user_pkey PRIMARY KEY (id)
);