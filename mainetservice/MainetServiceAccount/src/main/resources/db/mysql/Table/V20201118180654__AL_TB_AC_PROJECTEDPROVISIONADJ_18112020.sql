--liquibase formatted sql
--changeset Kanchan:V20201118180654__AL_TB_AC_PROJECTEDPROVISIONADJ_18112020.sql
alter table TB_AC_PROJECTEDPROVISIONADJ modify column FIELD_ID bigint(12) NULL;
