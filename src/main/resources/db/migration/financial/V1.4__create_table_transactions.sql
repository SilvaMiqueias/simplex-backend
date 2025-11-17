CREATE TABLE transaction (
                              id SERIAL PRIMARY KEY ,
                              transaction_type SMALLINT NOT NULL,
                              category SMALLINT NOT NULL,
                              payment_method SMALLINT,
                              recurring BOOLEAN NOT NULL DEFAULT FALSE,
                              recurrence_type SMALLINT,
                              amount NUMERIC(15,2) NOT NULL,
                              description VARCHAR(255),
                              date_transaction timestamp NOT NULL,
                              users_id integer NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                              created_at   timestamp default CURRENT_TIMESTAMP,
                              update_at   timestamp default CURRENT_TIMESTAMP
);