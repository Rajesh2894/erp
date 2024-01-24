--liquibase formatted sql
--changeset priya:V20180220113624__AL_tb_eip_sub_link_fields_dtl_20022018.sql
ALTER TABLE tb_eip_sub_link_fields_dtl 
CHANGE COLUMN TXT_03_EN_NNCLOB TXT_03_EN_NNCLOB LONGTEXT NULL DEFAULT NULL ;
--liquibase formatted sql
--changeset priya:V20180220113624__AL_tb_eip_sub_link_fields_dtl_200220181.sql
ALTER TABLE tb_eip_sub_link_fields_dtl_hist 
CHANGE COLUMN TXT_03_EN_NNCLOB TXT_03_EN_NNCLOB LONGTEXT NULL DEFAULT NULL ;

