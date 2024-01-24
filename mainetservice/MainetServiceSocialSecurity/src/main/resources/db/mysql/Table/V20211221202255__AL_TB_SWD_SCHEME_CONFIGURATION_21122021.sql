--liquibase formatted sql
--changeset Kanchan:V20211221202255__AL_TB_SWD_SCHEME_CONFIGURATION_21122021.sql
alter table TB_SWD_SCHEME_CONFIGURATION modify column BENF_CNT bigint(12) NULL DEFAULT NULL ;
