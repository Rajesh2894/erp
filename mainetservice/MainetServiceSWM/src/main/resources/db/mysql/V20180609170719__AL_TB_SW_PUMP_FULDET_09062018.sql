--liquibase formatted sql
--changeset nilima:V20180609170719__AL_TB_SW_PUMP_FULDET_09062018.sql
ALTER TABLE TB_SW_PUMP_FULDET
ADD COLUMN PU_ACTIVE CHAR(1) NOT NULL COMMENT 'Applicable Y and N Not Applicable' AFTER pu_fuunit;
