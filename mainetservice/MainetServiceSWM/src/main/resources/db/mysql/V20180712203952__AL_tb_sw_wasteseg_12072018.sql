--liquibase formatted sql
--changeset nilima:V20180712203952__AL_tb_sw_wasteseg_12072018.sql
ALTER TABLE tb_sw_wasteseg
ADD COLUMN VE_ID BIGINT(12) NULL COMMENT 'Vendor Name' AFTER DE_ID,
ADD COLUMN EMPID BIGINT(12) NULL COMMENT 'Superviser Name' AFTER VE_ID;

--liquibase formatted sql
--changeset nilima:V20180712203952__AL_tb_sw_wasteseg_120720181.sql
ALTER TABLE tb_sw_wasteseg_hist
ADD COLUMN VE_ID BIGINT(12) NULL COMMENT 'Vendor Name' AFTER DE_ID,
ADD COLUMN EMPID BIGINT(12) NULL COMMENT 'Superviser Name' AFTER VE_ID;
