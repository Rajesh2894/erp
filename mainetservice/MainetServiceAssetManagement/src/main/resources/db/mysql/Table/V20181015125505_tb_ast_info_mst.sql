--liquibase formatted sql
--changeset nilima:V20181015125505_tb_ast_info_mst1.sql
alter table tb_ast_info_mst change column SERIAL_NO SERIAL_NO VARCHAR(100) NULL COMMENT '	X	';