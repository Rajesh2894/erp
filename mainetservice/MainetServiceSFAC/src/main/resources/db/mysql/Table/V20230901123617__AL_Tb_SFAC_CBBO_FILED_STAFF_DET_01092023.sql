--liquibase formatted sql
--changeset PramodPatil:V20230901123617__AL_Tb_SFAC_CBBO_FILED_STAFF_DET_01092023.sql
Alter table Tb_SFAC_CBBO_FILED_STAFF_DET
add column ED_QUALIFICATION varchar(200) Null default null,
add column EXPERIENCE varchar(50) Null default null;