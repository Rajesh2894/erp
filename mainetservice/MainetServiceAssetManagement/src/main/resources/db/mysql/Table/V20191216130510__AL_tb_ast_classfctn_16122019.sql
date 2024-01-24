--liquibase formatted sql
--changeset Anil:V20191216130510__AL_tb_ast_classfctn_16122019.sql
ALTER TABLE tb_ast_classfctn 
DROP COLUMN ADDRESS,
DROP COLUMN NORTH,
DROP COLUMN SOUTH,
DROP COLUMN WEST,
DROP COLUMN EAST;
--liquibase formatted sql
--changeset Anil:V20191216130510__AL_tb_ast_classfctn_161220191.sql
ALTER TABLE tb_ast_classfctn_hist
DROP COLUMN ADDRESS,
DROP COLUMN NORTH,
DROP COLUMN SOUTH,
DROP COLUMN WEST,
DROP COLUMN EAST;
--liquibase formatted sql
--changeset Anil:V20191216130510__AL_tb_ast_classfctn_161220192.sql
ALTER TABLE tb_ast_classfctn ADD COLUMN SURVEY_NO VARCHAR(200) NULL AFTER LG_IP_MAC_UPD;
--liquibase formatted sql
--changeset Anil:V20191216130510__AL_tb_ast_classfctn_161220193.sql
ALTER TABLE tb_ast_classfctn_hist ADD COLUMN SURVEY_NO VARCHAR(200) NULL AFTER LG_IP_MAC_UPD;
