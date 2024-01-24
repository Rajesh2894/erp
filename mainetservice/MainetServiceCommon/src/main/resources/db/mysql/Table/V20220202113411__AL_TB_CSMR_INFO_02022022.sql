--liquibase formatted sql
--changeset Kanchan:V20220202113411__AL_TB_CSMR_INFO_02022022.sql
alter table TB_CSMR_INFO add column HOLE_MAN VARCHAR(200) DEFAULT NULL; 
