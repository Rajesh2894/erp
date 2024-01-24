--liquibase formatted sql
--changeset Anil:V20200724115055__AL_tb_ml_renewal_mast_24072020.sql
ALTER TABLE tb_ml_renewal_mast ADD COLUMN TRE_RNW_PRD BIGINT(2) NULL;
--liquibase formatted sql
--changeset Anil:V20200724115055__AL_tb_ml_renewal_mast_240720201.sql
ALTER TABLE tb_ml_renewal_mast_hist ADD COLUMN TRE_RNW_PRD BIGINT(2) NULL;
