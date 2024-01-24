--liquibase formatted sql
--changeset Anil:V20190813145441__AL_tb_est_aquisn_13082019.sql
ALTER TABLE tb_est_aquisn DROP COLUMN LANG_ID;
