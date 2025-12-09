CREATE TABLE goal (
                              id SERIAL PRIMARY KEY ,
                              category SMALLINT NOT NULL,
                              amount NUMERIC(15,2) NOT NULL,
                              description VARCHAR(255),
                              date_start timestamp NOT NULL,
                              date_end timestamp NOT NULL,
                              users_id integer NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                              created_at   timestamp default CURRENT_TIMESTAMP,
                              updated_at   timestamp default CURRENT_TIMESTAMP
);