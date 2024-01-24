--liquibase formatted sql
--changeset Kanchan:V20210511200436__AL_TB_ADH_MAS_11052021.sql
alter table TB_ADH_MAS add column property_address varchar(200) default null;
