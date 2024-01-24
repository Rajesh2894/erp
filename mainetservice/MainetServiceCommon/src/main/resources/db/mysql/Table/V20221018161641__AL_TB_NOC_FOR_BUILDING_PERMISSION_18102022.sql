--liquibase formatted sql
--changeset Kanchan:V20221018161641__AL_TB_NOC_FOR_BUILDING_PERMISSION_18102022.sql
alter table TB_NOC_FOR_BUILDING_PERMISSION add column REF_NOC VARCHAR(200) null;