--liquibase formatted sql
--changeset Kanchan:V20220119164902__AL_TB_WMS_MEASUREMENT_DETAIL_19012022.sql
alter table TB_WMS_MEASUREMENT_DETAIL  add column  ME_LENGTH_FORMULA     varchar(100), add  ME_BREADTH_FORMULA   varchar(100),add ME_HEIGHT_FORMULA      varchar(100) DEFAULT NULL;
