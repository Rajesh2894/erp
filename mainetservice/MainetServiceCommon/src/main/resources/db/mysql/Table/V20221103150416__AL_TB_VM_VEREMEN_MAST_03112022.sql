--liquibase formatted sql
--changeset Kanchan:V20221103150416__AL_TB_VM_VEREMEN_MAST_03112022.sql
alter table TB_VM_VEREMEN_MAST  

add column requestDate date NULL DEFAULT NULL,

add driverName bigint(20) NULL DEFAULT NULL,

add inspectedBy bigint(20) NULL DEFAULT NULL,

add inspectionDet varchar(100) NULL DEFAULT NULL,

add acceptDate date NULL DEFAULT NULL,

add maintEndReading decimal(22,1) NULL DEFAULT NULL,

add maintCompDate date NULL DEFAULT NULL,

add locId bigint(20) NULL DEFAULT NULL,

add requestNo varchar(100) NULL DEFAULT NULL,

add maintCategory bigint(12) NULL DEFAULT NULL;