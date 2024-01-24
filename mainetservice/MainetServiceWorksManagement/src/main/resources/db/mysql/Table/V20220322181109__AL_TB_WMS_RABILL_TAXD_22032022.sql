--liquibase formatted sql
--changeset Kanchan:V20220322181109__AL_TB_WMS_RABILL_TAXD_22032022.sql
alter table TB_WMS_RABILL_TAXD add column RA_REMARK varchar(50) NULL DEFAULT NULL;
