--liquibase formatted sql
--changeset nilima:V20181124184128__AL_tb_sw_tripsheet_21112018.sql
ALTER TABLE TB_SW_TRIPSHEET
ADD COLUMN WAST_SAGRIGATED char(1) NULL AFTER TRIP_WESLIPNO;

--liquibase formatted sql
--changeset nilima:V20181124184128__AL_tb_sw_tripsheet_211120181.sql
ALTER TABLE TB_SW_TRIPSHEET_HIST
ADD COLUMN WAST_SAGRIGATED char(1) NULL AFTER TRIP_WESLIPNO;