--liquibase formatted sql
--changeset Kanchan:V20210623111343__AL_TB_WMS_OVERHEAD_DETAIL_23062021.sql
alter table TB_WMS_OVERHEAD_DETAIL add column  ME_REMARK varchar(200) ;
