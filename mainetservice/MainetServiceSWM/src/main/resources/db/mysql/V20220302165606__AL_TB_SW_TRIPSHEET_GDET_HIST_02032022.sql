--liquibase formatted sql
--changeset Kanchan:V20220302165606__AL_TB_SW_TRIPSHEET_GDET_HIST_02032022.sql
alter table  TB_SW_TRIPSHEET_GDET_HIST modify column TRIP_VOLUME decimal(15,2) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220302165606__AL_TB_SW_TRIPSHEET_GDET_HIST_020320221.sql
alter table TB_SW_TRIPSHEET_GDET_HIST modify column WAST_TYPE bigint(12) NULL DEFAULT NULL;
