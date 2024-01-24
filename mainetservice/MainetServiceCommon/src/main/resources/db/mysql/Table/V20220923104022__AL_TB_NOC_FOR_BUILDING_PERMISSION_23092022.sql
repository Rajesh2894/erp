--liquibase formatted sql
--changeset Kanchan:V20220923104022__AL_TB_NOC_FOR_BUILDING_PERMISSION_23092022.sql
alter table TB_NOC_FOR_BUILDING_PERMISSION modify column NOC_TYPEID bigint(12) NOT NULL;