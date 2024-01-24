--liquibase formatted sql
--changeset PramodPatil:V20230725181415__AL_TB_SFAC_FARMER_ON_OFF_DET_25072023.sql
 Alter table TB_SFAC_FARMER_ON_OFF_DET add column STATUS varchar(10) Null Default 'A';
 
--liquibase formatted sql
--changeset PramodPatil:V20230725181415__AL_TB_SFAC_FARMER_ON_OFF_DET_250720231.sql
 Alter table TB_SFAC_FARMER_BANK_DETAILS add column STATUS varchar(10) Null Default 'A';
 
--liquibase formatted sql
--changeset PramodPatil:V20230725181415__AL_TB_SFAC_FARMER_ON_OFF_DET_250720232.sql
 Alter table TB_SFAC_FARMER_LAND_DETAILS add column STATUS varchar(10) Null Default 'A';