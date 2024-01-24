--liquibase formatted sql
--changeset Kanchan:V20220308104246__AL_TB_NOC_FOR_BUILDING_PERMISSION_08032022.sql
alter table TB_NOC_FOR_BUILDING_PERMISSION modify column SEX varchar(10) NULL DEFAULT NULL;
