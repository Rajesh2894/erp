--liquibase formatted sql
--changeset Kanchan:V20210309122557__AL_TB_MTL_NOTICE_MAS_09032021.sql
alter table TB_MTL_NOTICE_MAS add column LG_IP_MAC_UPD Varchar(100);
