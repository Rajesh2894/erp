--liquibase formatted sql
--changeset shamik:V20180828151506_tb_ast_reval1.sql
ALTER TABLE tb_ast_reval ADD COLUMN REVAL_TYPE bigint(12) DEFAULT NULL COMMENT '      X             ' AFTER AMOUNT;
--liquibase formatted sql
--changeset shamik:V20180828151506_AL_tb_ast_reval2.sql
ALTER TABLE tb_ast_reval ADD COLUMN IMPRO_COST decimal(15,2) DEFAULT NULL COMMENT '           X             ' AFTER REVAL_TYPE;
--liquibase formatted sql
--changeset shamik:V20180828151506_AL_tb_ast_reval3.sql
ALTER TABLE tb_ast_reval ADD COLUMN IMPRO_DESC varchar(500) DEFAULT NULL COMMENT '              X            ' AFTER IMPRO_COST;
--liquibase formatted sql
--changeset shamik:V20180828151506_AL_tb_ast_reval4.sql
ALTER TABLE tb_ast_reval ADD COLUMN UPD_USEFULL_LIFE decimal(15,2) DEFAULT NULL AFTER IMPRO_DESC;
--liquibase formatted sql
--changeset shamik:V20180828151506_AL_tb_ast_reval5.sql
ALTER TABLE tb_ast_reval ADD COLUMN ORIG_USEFULL_LIFE decimal(15,2) DEFAULT NULL AFTER UPD_USEFULL_LIFE;
--liquibase formatted sql
--changeset shamik:V20180828151506_AL_tb_ast_reval6.sql
ALTER TABLE tb_ast_reval ADD COLUMN PAYMENT_ADV_NO varchar(100) DEFAULT NULL AFTER ORIG_USEFULL_LIFE;