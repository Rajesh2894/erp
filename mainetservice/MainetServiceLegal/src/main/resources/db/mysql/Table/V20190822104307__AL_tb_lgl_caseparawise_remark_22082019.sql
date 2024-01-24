--liquibase formatted sql
--changeset Anil:V20190822104307__AL_tb_lgl_caseparawise_remark_22082019.sql
ALTER TABLE tb_lgl_caseparawise_remark 
ADD COLUMN ATD_ID BIGINT(12) NULL AFTER cse_id;
--liquibase formatted sql
--changeset Anil:V20190822104307__AL_tb_lgl_caseparawise_remark_220820191.sql
ALTER TABLE tb_lgl_caseparawise_remark_hist 
ADD COLUMN ATD_ID BIGINT(12) NULL AFTER cse_id;
