--liquibase formatted sql
--changeset Anil:V20191223132751__AL_tb_ac_primaryhead_master_23122019.sql
ALTER TABLE tb_ac_primaryhead_master CHANGE COLUMN SUBLEDGER_FLAG budget_flag CHAR(1) NULL DEFAULT NULL COMMENT 'Group behaves like a sub- ledger';
--liquibase formatted sql
--changeset Anil:V20191223132751__AL_tb_ac_primaryhead_master_231220191.sql
ALTER TABLE tb_ac_primaryhead_master ADD COLUMN PAC_HEAD_DESC_REG VARCHAR(1000) NULL AFTER SAM_STATUS;
--liquibase formatted sql
--changeset Anil:V20191223132751__AL_tb_ac_primaryhead_master_231220192.sql
ALTER TABLE tb_ac_primaryhead_master_hist ADD COLUMN budget_flag CHAR(1) NULL AFTER H_STATUS;
--liquibase formatted sql
--changeset Anil:V20191223132751__AL_tb_ac_primaryhead_master_231220193.sql
ALTER TABLE tb_ac_primaryhead_master_hist ADD COLUMN PAC_HEAD_DESC_REG VARCHAR(1000) NULL AFTER budget_flag;
