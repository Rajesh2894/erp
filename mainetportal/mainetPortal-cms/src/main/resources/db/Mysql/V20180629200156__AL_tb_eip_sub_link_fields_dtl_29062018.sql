--liquibase formatted sql
--changeset nilima:V20180629200156__AL_tb_eip_sub_link_fields_dtl_29062018.sql
ALTER TABLE tb_eip_sub_link_fields_dtl
CHANGE COLUMN TXT_03_EN_BLOB TXT_03_EN_BLOB LONGTEXT NULL DEFAULT NULL 

--liquibase formatted sql
--changeset nilima:V20180629200156__AL_tb_eip_sub_link_fields_dtl_290620181.sql
ALTER TABLE tb_eip_sub_link_fields_dtl_hist
CHANGE COLUMN TXT_03_EN_BLOB TXT_03_EN_BLOB LONGTEXT NULL DEFAULT NULL 


