--liquibase formatted sql
--changeset Anil:V20200127110219__AL_tb_ast_annual_plan_mst_27012020.sql
ALTER TABLE tb_ast_annual_plan_mst CHANGE COLUMN LOCID LOC_ID BIGINT(12) NOT NULL ;
--liquibase formatted sql
--changeset Anil:V20200127110219__AL_tb_ast_annual_plan_mst_270120201.sql
ALTER TABLE tb_ast_annual_plan_det CHANGE COLUMN AST_ANN_PLAN__DET_ID AST_ANN_PLAN_DET_ID BIGINT(12) NOT NULL COMMENT 'Primary Key' ;
