--liquibase formatted sql
--changeset nilima:V20180712203922__AL_tb_sw_tripsheet_12072018.sql
ALTER TABLE tb_sw_tripsheet
ADD COLUMN EMPID BIGINT(12) NULL COMMENT 'Superviser Name' AFTER `DE_ID`;

--liquibase formatted sql
--changeset nilima:V20180712203922__AL_tb_sw_tripsheet_120720181.sql
ALTER TABLE tb_sw_tripsheet_hist
ADD COLUMN EMPID BIGINT(12) NULL COMMENT 'Superviser Name' AFTER `DE_ID`;
