-- Drop table
-- DROP TABLE public.request;
CREATE TABLE IF NOT EXISTS request (
	id serial4 NOT NULL,
	url varchar(1024) NULL,
	"method" varchar(20) NULL,
	jsonData text NULL,
	isFavorite bool NULL,
	CONSTRAINT request_pkey PRIMARY KEY (id)
);
-- Drop table
-- DROP TABLE public.body;
CREATE TABLE IF NOT EXISTS body (
	id serial4 NOT NULL,
	"content" text NOT NULL,
	contentType varchar(255) NOT NULL,
	requestId int4 NULL,
	CONSTRAINT body_pkey PRIMARY KEY (id)
);

-- public.body foreign keys
ALTER TABLE public.body ADD CONSTRAINT fk_request FOREIGN KEY (requestId) REFERENCES public.request(id) ON DELETE CASCADE ON UPDATE CASCADE;
-- Create the headers table
-- DROP TABLE public.header;
CREATE TABLE IF NOT EXISTS header (
    id SERIAL PRIMARY KEY,
    key VARCHAR(255) NOT NULL,
    value VARCHAR(255) NOT NULL,
    request_id BIGINT NOT NULL,
    CONSTRAINT fk_request
        FOREIGN KEY (request_id)
        REFERENCES request (id)
        ON DELETE CASCADE
);

-- Create the queryParam table
-- DROP TABLE public.queryParam;
CREATE TABLE IF NOT EXISTS queryParam (
    id SERIAL PRIMARY KEY,
    key VARCHAR(255) NOT NULL,
    value VARCHAR(255) NOT NULL,
    request_id BIGINT NOT NULL,
    CONSTRAINT fk_request
        FOREIGN KEY (request_id)
        REFERENCES request (id)
        ON DELETE CASCADE
);