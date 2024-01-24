--liquibase formatted sql
--changeset Kanchan:V20221004113903__AL_TB_WMS_ROAD_CUTTING_04102022.sql
alter table TB_WMS_ROAD_CUTTING add column APPLICANT_M_NAME varchar(50) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20221004113903__AL_TB_WMS_ROAD_CUTTING_041020221.sql
alter table TB_WMS_ROAD_CUTTING add column APPLICANT_L_NAME varchar(50) NULL DEFAULT NULL;