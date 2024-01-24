--liquibase formatted sql
--changeset Kanchan:V20220302164705__AL_TB_SW_TRIPSHEET_GDET_02032022.sql
alter table TB_SW_TRIPSHEET_GDET modify column TRIP_VOLUME decimal(15,2) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220302164705__AL_TB_SW_TRIPSHEET_GDET_020320221.sql
alter table TB_SW_TRIPSHEET_GDET modify column WAST_TYPE bigint(12) NULL DEFAULT NULL;

