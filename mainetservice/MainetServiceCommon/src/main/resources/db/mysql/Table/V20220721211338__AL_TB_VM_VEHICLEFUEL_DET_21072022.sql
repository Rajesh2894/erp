--liquibase formatted sql
--changeset Kanchan:V20220721211338__AL_TB_VM_VEHICLEFUEL_DET_21072022.sql
Alter table TB_VM_VEHICLEFUEL_DET modify column VEFD_QUANTITY  Decimal(3,2) NULL DEFAULT NULL;