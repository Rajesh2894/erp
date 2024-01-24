--liquibase formatted sql
--changeset priya:V20180208111822__AL_tb_eip_sub_link_field_map_03022018_PORTAL.sql
ALTER TABLE tb_eip_sub_link_field_map 
CHANGE COLUMN SUB_LINK_FIELD_ID SUB_LINK_FIELD_ID BIGINT(12) NOT NULL COMMENT 'Primary key' ,
CHANGE COLUMN SUB_LINK_MAS_ID SUB_LINK_MAS_ID BIGINT(12) NULL COMMENT 'Sub link master id' ,
CHANGE COLUMN FIELD_TYPE_ID FIELD_TYPE_ID BIGINT(12) NOT NULL COMMENT 'Field type Id' ;
--liquibase formatted sql
--changeset priya:V20180208111822__AL_tb_eip_sub_link_field_map_03022018_PORTAL1.sql
ALTER TABLE tb_eip_sub_link_field_map 
CHANGE COLUMN FIELD_SEQ FIELD_SEQ BIGINT(12) NOT NULL COMMENT '' ;
