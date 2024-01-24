--liquibase formatted sql
--changeset Anil:V20200812154239__AL_tb_ast_info_mst_12082020.sql
ALTER TABLE tb_ast_info_mst ADD COLUMN ASSET_APP_NO VARCHAR(50) NULL;
--liquibase formatted sql
--changeset Anil:V20200812154239__AL_tb_ast_info_mst_120820201.sql
ALTER TABLE tb_ast_info_mst_hist ADD COLUMN ASSET_APP_NO VARCHAR(50) NULL;
