-- public.request definition

-- Drop table

-- DROP TABLE public.request;

CREATE TABLE public.request (
	id serial4 NOT NULL,
	url varchar(255) NOT NULL,
	"method" varchar(50) NOT NULL,
	is_favorite bool DEFAULT true NULL,
	CONSTRAINT request_pkey PRIMARY KEY (id)
);


-- public.body definition

-- Drop table

-- DROP TABLE public.body;

CREATE TABLE public.body (
	id serial4 NOT NULL,
	"content" text NOT NULL,
	content_type varchar(100) NOT NULL,
	request_id int8 NULL,
	CONSTRAINT body_pkey PRIMARY KEY (id),
	CONSTRAINT body_request_id_fkey FOREIGN KEY (request_id) REFERENCES public.request(id) ON DELETE CASCADE
);


-- public."header" definition

-- Drop table

-- DROP TABLE public."header";

CREATE TABLE public."header" (
	id serial4 NOT NULL,
	"key" varchar(255) NOT NULL,
	value varchar(255) NOT NULL,
	request_id int8 NULL,
	CONSTRAINT header_pkey PRIMARY KEY (id),
	CONSTRAINT header_request_id_fkey FOREIGN KEY (request_id) REFERENCES public.request(id) ON DELETE CASCADE
);


-- public.queryparam definition

-- Drop table

-- DROP TABLE public.queryparam;

CREATE TABLE public.queryparam (
	id serial4 NOT NULL,
	"key" varchar(255) NOT NULL,
	value varchar(255) NOT NULL,
	request_id int8 NULL,
	CONSTRAINT queryparam_pkey PRIMARY KEY (id),
	CONSTRAINT queryparam_request_id_fkey FOREIGN KEY (request_id) REFERENCES public.request(id) ON DELETE CASCADE
);