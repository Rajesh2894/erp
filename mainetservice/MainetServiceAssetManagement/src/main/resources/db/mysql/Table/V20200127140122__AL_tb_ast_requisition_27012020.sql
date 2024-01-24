--liquibase formatted sql
--changeset Anil:V20200127140122__AL_tb_ast_requisition_27012020.sql
ALTER TABLE tb_ast_requisition
CHANGE COLUMN CREATION_DATE CREATION_DATE DATETIME NOT NULL,
CHANGE COLUMN UPDATED_DATE UPDATED_DATE DATETIME NULL DEFAULT NULL ;
--liquibase formatted sql
--changeset Anil:V20200127140122__AL_tb_ast_requisition_270120201.sql
ALTER TABLE tb_ast_annual_plan_det
CHANGE COLUMN CREATED_DATE CREATED_DATE DATETIME NOT NULL ,
CHANGE COLUMN UPDATED_DATE UPDATED_DATE DATETIME NULL DEFAULT NULL ;
--liquibase formatted sql
--changeset Anil:V20200127140122__AL_tb_ast_requisition_270120202.sql
ALTER TABLE tb_ast_annual_plan_mst 
CHANGE COLUMN CREATED_DATE CREATED_DATE DATETIME NOT NULL ,
CHANGE COLUMN UPDATED_DATE UPDATED_DATE DATETIME NULL DEFAULT NULL ;

