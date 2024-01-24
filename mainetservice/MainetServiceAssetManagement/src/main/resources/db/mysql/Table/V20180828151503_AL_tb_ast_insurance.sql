--liquibase formatted sql
--changeset shamik:V20180828151503_AL_tb_ast_insurance1.sql
ALTER TABLE tb_ast_insurance change column INSURANCE_NO INSURANCE_NO VARCHAR(20) NOT NULL COMMENT '         X             ' ;
--liquibase formatted sql
--changeset shamik:V20180828151503_AL_tb_ast_insurance2.sql
ALTER TABLE tb_ast_insurance_hist change column INSURANCE_NO INSURANCE_NO VARCHAR(20) NOT NULL COMMENT '    X             ' ;
--liquibase formatted sql
--changeset shamik:V20180828151503_AL_tb_ast_insurance3.sql
ALTER TABLE tb_ast_insurance_rev change column INSURANCE_NO INSURANCE_NO VARCHAR(20) NOT NULL COMMENT '     X             ' ;
