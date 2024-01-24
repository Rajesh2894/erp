--liquibase formatted sql
--changeset Kanchan:V20230622162037__AL_TB_SFAC_FARMER_LAND_DETAILS_22062023.sql
Alter table TB_SFAC_FARMER_LAND_DETAILS modify column LAND_PROPORTION decimal(15,2) Null default null;