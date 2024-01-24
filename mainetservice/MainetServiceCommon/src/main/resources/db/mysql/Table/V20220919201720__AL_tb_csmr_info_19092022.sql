--liquibase formatted sql
--changeset Kanchan:V20220919201720__AL_tb_csmr_info_19092022.sql
ALTER TABLE tb_csmr_info add column DISTRICT_BILLING bigint(20) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220919201720__AL_tb_csmr_info_190920221.sql
ALTER TABLE tb_csmr_info add column COB_DWZID1 bigint(20) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220919201720__AL_tb_csmr_info_190920222.sql
ALTER TABLE tb_csmr_info add column COB_DWZID2 bigint(20) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220919201720__AL_tb_csmr_info_190920223.sql
ALTER TABLE tb_csmr_info add column COB_DWZID3 bigint(20) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220919201720__AL_tb_csmr_info_190920224.sql
ALTER TABLE tb_csmr_info add column COB_DWZID4 bigint(20) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220919201720__AL_tb_csmr_info_190920225.sql
ALTER TABLE tb_csmr_info add column COB_DWZID5 bigint(20) DEFAULT NULL;