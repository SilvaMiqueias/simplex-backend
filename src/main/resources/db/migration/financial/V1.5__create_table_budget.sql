CREATE TABLE budget (
                              id SERIAL PRIMARY KEY ,
                              category SMALLINT NOT NULL,
                              amount NUMERIC(15,2) NOT NULL,
                              description VARCHAR(255),
                              date_reference timestamp NOT NULL,
                              users_id integer NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                              created_at   timestamp default CURRENT_TIMESTAMP,
                              updated_at   timestamp default CURRENT_TIMESTAMP
);