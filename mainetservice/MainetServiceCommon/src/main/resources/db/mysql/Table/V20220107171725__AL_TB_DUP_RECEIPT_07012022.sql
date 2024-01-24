--liquibase formatted sql
--changeset Kanchan:V20220107171725__AL_TB_DUP_RECEIPT_07012022.sql
alter table TB_DUP_RECEIPT modify column SM_SHORTDESC varchar(5)  DEFAULT NULL;
