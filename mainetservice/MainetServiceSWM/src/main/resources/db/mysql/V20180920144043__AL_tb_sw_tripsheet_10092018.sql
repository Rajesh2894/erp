--liquibase formatted sql
--changeset nilima:V20180920144043__AL_tb_sw_tripsheet_10092018.sql
ALTER TABLE tb_sw_tripsheet
CHANGE COLUMN EMPID BEAT_ID BIGINT(12) NULL DEFAULT NULL COMMENT 'Beat No' ;

--liquibase formatted sql
--changeset nilima:V20180920144043__AL_tb_sw_tripsheet_100920181.sql
ALTER TABLE tb_sw_tripsheet_hist
CHANGE COLUMN EMPID BEAT_ID BIGINT(12) NULL DEFAULT NULL COMMENT 'Beat No' ;