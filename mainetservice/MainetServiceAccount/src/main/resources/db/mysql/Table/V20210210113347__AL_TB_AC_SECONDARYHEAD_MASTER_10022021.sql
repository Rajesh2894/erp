--liquibase formatted sql
--changeset Kanchan:V20210210113347__AL_TB_AC_SECONDARYHEAD_MASTER_10022021.sql
alter table TB_AC_SECONDARYHEAD_MASTER  modify column SAC_HEAD_OLD Varchar(100) NUll;
--liquibase formatted sql
--changeset Kanchan:V20210210113347__AL_TB_AC_SECONDARYHEAD_MASTER_100220211.sql
alter table TB_AC_SECONDARYHEAD_MAS_HIST  modify column SAC_HEAD_OLD Varchar(100) NUll;
