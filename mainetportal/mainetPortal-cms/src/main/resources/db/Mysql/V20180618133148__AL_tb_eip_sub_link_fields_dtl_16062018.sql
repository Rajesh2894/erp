--liquibase formatted sql
--changeset nilima:V20180618133148__AL_tb_eip_sub_link_fields_dtl_16062018.sql
ALTER TABLE tb_eip_sub_link_fields_dtl 
ADD COLUMN VALIDITY_DATE DATE NULL AFTER CHEKER_FLAG;

--liquibase formatted sql
--changeset nilima:V20180618133148__AL_tb_eip_sub_link_fields_dtl_160620181.sql
ALTER TABLE tb_eip_sub_link_fields_dtl_hist 
ADD COLUMN VALIDITY_DATE DATE NULL AFTER CHEKER_FLAG;