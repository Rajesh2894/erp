--liquibase formatted sql
--changeset Anil:V20200623191148__AL_tb_lgl_case_mas_23062020.sql
ALTER TABLE tb_lgl_case_mas ADD COLUMN ORDER_DATE DATE NULL;
--liquibase formatted sql
--changeset Anil:V20200623191148__AL_tb_lgl_case_mas_230620201.sql
ALTER TABLE tb_lgl_case_mas_hist ADD COLUMN ORDER_DATE DATE NULL;
