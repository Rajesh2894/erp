--liquibase formatted sql
--changeset nilima:V20181001125505_tb_ast_info_mst1.sql
alter table tb_ast_info_mst change column ASSET_GROUP ASSET_GROUP bigint(20) NULL COMMENT '	X	';
--liquibase formatted sql
--changeset nilima:V20181001125505_tb_ast_info_mst2.sql
alter table tb_ast_info_mst change column ASSET_TYPE ASSET_TYPE bigint(20) NULL COMMENT '	X	';
--liquibase formatted sql
--changeset nilima:V20181001125505_tb_ast_info_mst3.sql
alter table tb_ast_info_mst_hist change column ASSET_GROUP ASSET_GROUP bigint(20) NULL COMMENT '	X	';
--liquibase formatted sql
--changeset nilima:V20181001125505_tb_ast_info_mst4.sql
alter table tb_ast_info_mst_hist change column ASSET_TYPE ASSET_TYPE bigint(20) NULL COMMENT '	X	';
--liquibase formatted sql
--changeset nilima:V20181001125505_tb_ast_info_mst5.sql
alter table tb_ast_info_mst_rev change column ASSET_GROUP ASSET_GROUP bigint(20) NULL COMMENT '	X	';
--liquibase formatted sql
--changeset nilima:V20181001125505_tb_ast_info_mst6.sql
alter table tb_ast_info_mst_rev change column ASSET_TYPE ASSET_TYPE bigint(20) NULL COMMENT '	X	';
--liquibase formatted sql
--changeset nilima:V20181001125505_tb_ast_info_mst7.sql
alter table TB_AST_REALSTD change column ASSESSMENT_NO ASSESSMENT_NO VARCHAR(50) NULL DEFAULT NULL COMMENT '	X	';
--liquibase formatted sql
--changeset nilima:V20181001125505_tb_ast_info_mst8.sql
alter table TB_AST_REALSTD_HIST change column ASSESSMENT_NO ASSESSMENT_NO VARCHAR(50) NULL DEFAULT NULL COMMENT '	X	';
--liquibase formatted sql
--changeset nilima:V20181001125505_tb_ast_info_mst9.sql
alter table tb_ast_realstd_rev change column ASSESSMENT_NO ASSESSMENT_NO VARCHAR(50) NULL DEFAULT NULL COMMENT '	X	';
--liquibase formatted sql
--changeset nilima:V20181001125505_tb_ast_info_mst10.sql
alter table TB_AST_REALSTD change column TAX_CODE TAX_CODE VARCHAR(50) NULL DEFAULT NULL COMMENT '	X	';
--liquibase formatted sql
--changeset nilima:V20181001125505_tb_ast_info_mst11.sql
alter table TB_AST_REALSTD_HIST change column TAX_CODE TAX_CODE VARCHAR(50) NULL DEFAULT NULL COMMENT '	X	';
--liquibase formatted sql
--changeset nilima:V20181001125505_tb_ast_info_mst12.sql
alter table tb_ast_realstd_rev change column TAX_CODE TAX_CODE VARCHAR(50) NULL DEFAULT NULL COMMENT '	X	';