--liquibase formatted sql
--changeset shamik:V20180828151501_AL_tb_ast_func_location_mas1.sql
ALTER TABLE tb_ast_func_location_mas change COLUMN START_POINT START_POINT varchar(100) DEFAULT NULL;
--liquibase formatted sql
--changeset shamik:V20180828151501_AL_tb_ast_func_location_mas2.sql
ALTER TABLE tb_ast_func_location_mas change COLUMN END_POINT END_POINT varchar(100) DEFAULT NULL;
--liquibase formatted sql
--changeset shamik:V20180828151501_AL_tb_ast_func_location_mas3.sql
ALTER TABLE tb_ast_func_location_mas_hist change COLUMN START_POINT START_POINT varchar(100) DEFAULT NULL;
--liquibase formatted sql
--changeset shamik:V20180828151501_AL_tb_ast_func_location_mas4.sql
ALTER TABLE tb_ast_func_location_mas_hist change COLUMN END_POINT END_POINT varchar(100) DEFAULT NULL;
