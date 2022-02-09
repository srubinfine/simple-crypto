DROP TABLE IF EXISTS smplc.order;
CREATE TABLE smplc.order (
     id VARCHAR(64) CONSTRAINT firstkey PRIMARY KEY
    ,client_id VARCHAR(32)
    ,symbol VARCHAR(16)
    ,side VARCHAR(32)
    ,price NUMERIC(40,8)
    ,shares NUMERIC(40,8)
);