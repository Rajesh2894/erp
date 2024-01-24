--liquibase formatted sql
--changeset Kanchan:V20210622150244__AL_TB_AS_ASSESMENT_DETAIL_HIST_22062021.sql
alter table TB_AS_ASSESMENT_DETAIL_HIST
add column MN_CARPET_AREA decimal(12,2) NULL DEFAULT NULL,
add column MN_AGE decimal(12,2) NULL DEFAULT NULL,
add column MN_CONSTRUCT_PERMISSION_NUMBER varchar(100) NULL DEFAULT NULL,
add column MN_PERMISSION_USE_NO varchar(100) NULL DEFAULT NULL,
add column MN_ASSESSMENT_REMARK varchar(100) NULL DEFAULT NULL,
add column MN_LEGAL varchar(100) NULL DEFAULT NULL,
add column MN_OCCUPIER_NAME_REG varchar(100) NULL DEFAULT NULL,
add column MN_ASSE_MOBILENO varchar(100) NULL DEFAULT NULL,
add column MN_OCCUPIER_EMAIL varchar(100) NULL DEFAULT NULL,
add column MN_ACTUAL_RENT decimal(12,2) NULL DEFAULT NULL,
add column MN_FLAT_NO varchar(50) NULL DEFAULT NULL;
