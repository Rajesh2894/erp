--liquibase formatted sql
--changeset Kanchan:V20220204155309__AL_TB_CSMRRCMD_MAS_04022022.sql
alter table TB_CSMRRCMD_MAS add column HOLE_MAN varchar(200) NULL DEFAULT NULL;
