--liquibase formatted sql
--changeset Anil:V20200529142005__AL_tb_ml_owner_detail_29052020.sql
ALTER TABLE tb_ml_owner_detail 
ADD COLUMN TRO_EDQL VARCHAR(45) NULL,
ADD COLUMN TRO_CAST BIGINT(12) NULL;
--liquibase formatted sql
--changeset Anil:V20200529142005__AL_tb_ml_owner_detail_290520201.sql
ALTER TABLE tb_ml_owner_detail_hist 
ADD COLUMN TRO_EDQL VARCHAR(45) NULL,
ADD COLUMN TRO_CAST BIGINT(12) NULL;
