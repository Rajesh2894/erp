--liquibase formatted sql
--changeset Anil:V20200608153501__AL_tb_ml_owner_detail_08062020.sql
ALTER TABLE tb_ml_owner_detail ADD COLUMN TRO_AGE BIGINT(12) NULL;
--liquibase formatted sql
--changeset Anil:V20200608153501__AL_tb_ml_owner_detail_080620201.sql
ALTER TABLE tb_ml_owner_detail_hist ADD COLUMN TRO_AGE BIGINT(12) NULL;
