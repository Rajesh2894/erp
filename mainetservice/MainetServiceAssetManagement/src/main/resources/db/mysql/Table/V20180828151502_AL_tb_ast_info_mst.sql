--liquibase formatted sql
--changeset shamik:V20180828151502_AL_tb_ast_info_mst1.sql
alter table tb_ast_info_mst change column SERIAL_NO SERIAL_NO VARCHAR(100) NULL COMMENT '	X	';
--liquibase formatted sql
--changeset shamik:V20180828151502_AL_tb_ast_info_mst2.sql
alter table tb_ast_info_mst_hist change column SERIAL_NO SERIAL_NO VARCHAR(100) NULL COMMENT '	X	';
--liquibase formatted sql
--changeset shamik:V20180828151502_AL_tb_ast_info_mst3.sql
alter table TB_AST_INFO_MST_REV change column SERIAL_NO SERIAL_NO VARCHAR(100) NULL COMMENT '	X	';
