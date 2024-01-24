--liquibase formatted sql
--changeset nilima:V20180620174039__AL_tb_sw_veremen_mast_20062018.sql
ALTER TABLE tb_sw_veremen_mast 
ADD COLUMN VEM_EXPHEAD BIGINT(12) NULL AFTER VEM_READING;

--liquibase formatted sql
--changeset nilima:V20180620174039__AL_tb_sw_veremen_mast_200620181.sql
ALTER TABLE tb_sw_veremen_mast_hist 
ADD COLUMN VEM_EXPHEAD BIGINT(12) NULL AFTER VEM_READING;