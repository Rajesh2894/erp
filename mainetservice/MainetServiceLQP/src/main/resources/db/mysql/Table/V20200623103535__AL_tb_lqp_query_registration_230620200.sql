--liquibase formatted sql
--changeset Anil:V20200623103535__AL_tb_lqp_query_registration_230620200.sql
ALTER TABLE tb_lqp_query_registration CHANGE COLUMN STATUS STATUS VARCHAR(50) NOT NULL;
--liquibase formatted sql
--changeset Anil:V20200623103535__AL_tb_lqp_query_registration_230620200.sql
ALTER TABLE tb_lqp_query_registration_hist CHANGE COLUMN STATUS STATUS VARCHAR(50) NOT NULL;
