--liquibase formatted sql
--changeset Kanchan:V20210819120316__AL_tb_ast_info_mst_19082021.sql
ALTER TABLE `tb_ast_info_mst`
ADD COLUMN `PRINTER_TYPE_ID` BIGINT(12) NULL AFTER `group_ref_id`;
--liquibase formatted sql
--changeset Kanchan:V20210819120316__AL_tb_ast_info_mst_190820211.sql
ALTER TABLE `tb_ast_info_mst_hist`
ADD COLUMN `PRINTER_TYPE_ID` BIGINT(12) NULL AFTER `group_ref_id`;
