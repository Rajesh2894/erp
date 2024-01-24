--liquibase formatted sql
--changeset Kanchan:V20220830153407__AL_TB_VM_VEHICLEFUEL_DET_30082022.sql
 Alter table TB_VM_VEHICLEFUEL_DET modify column VEFD_QUANTITY decimal(6,2) null default null;