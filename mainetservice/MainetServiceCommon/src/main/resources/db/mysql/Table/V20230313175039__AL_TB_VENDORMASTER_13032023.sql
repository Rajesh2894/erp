--liquibase formatted sql
--changeset Kanchan:V20230313175039__AL_TB_VENDORMASTER_13032023.sql
ALTER TABLE TB_VENDORMASTER ADD ADD_MOBILE_NO varchar(20) null default null;