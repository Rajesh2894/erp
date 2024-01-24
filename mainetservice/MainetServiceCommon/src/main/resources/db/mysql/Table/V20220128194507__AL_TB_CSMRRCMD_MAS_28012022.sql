--liquibase formatted sql
--changeset Kanchan:V20220128194507__AL_TB_CSMRRCMD_MAS_28012022.sql
alter table TB_CSMRRCMD_MAS add column DIAMETER DECIMAL(15,2) DEFAULT NULL; 
