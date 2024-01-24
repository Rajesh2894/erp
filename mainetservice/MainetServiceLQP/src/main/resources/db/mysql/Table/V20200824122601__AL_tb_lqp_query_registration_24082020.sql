--liquibase formatted sql
--changeset Anil:V20200824122601__AL_tb_lqp_query_registration_24082020.sql
ALTER TABLE tb_lqp_query_registration ADD COLUMN IS_ATTACH_DOC VARCHAR(1) NULL;
--liquibase formatted sql
--changeset Anil:V20200824122601__AL_tb_lqp_query_registration_240820201.sql
ALTER TABLE tb_lqp_query_registration_hist ADD COLUMN IS_ATTACH_DOC VARCHAR(1) NULL;
