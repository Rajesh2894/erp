--liquibase formatted sql
--changeset nilima:V20190509173016_AL_TB_CSMR_INFO1.sql
ALTER TABLE TB_CSMR_INFO CHANGE COLUMN `CS_ADD` `CS_ADD` varchar(1000) DEFAULT NULL COMMENT 'Address of the consumer';
--liquibase formatted sql
--changeset nilima:V20190509173016_AL_TB_CSMR_INFO2.sql
ALTER TABLE TB_CSMR_INFO CHANGE COLUMN `CS_BADD` `CS_BADD` varchar(1000) DEFAULT NULL COMMENT 'Billing address ';
--liquibase formatted sql
--changeset nilima:V20190509173016_AL_TB_CSMR_INFO3.sql
ALTER TABLE TB_CSMR_INFO CHANGE COLUMN `CS_OADD` `CS_OADD` varchar(1000) DEFAULT NULL COMMENT 'Address of property owner';
--liquibase formatted sql
--changeset nilima:V20190509173016_AL_TB_CSMR_INFO4.sql
ALTER TABLE TB_CSMR_INFO CHANGE COLUMN `CS_OBLDPLT` `CS_OBLDPLT` varchar(1000) DEFAULT NULL COMMENT 'Owner Building / Plot no. Of the Consumer';
--liquibase formatted sql
--changeset nilima:V20190509173016_AL_TB_CSMR_INFO5.sql
ALTER TABLE TB_CSMR_INFO CHANGE COLUMN `CS_OLANEAR` `CS_OLANEAR` varchar(1000) DEFAULT NULL COMMENT 'Owner lane Of the Consumer';
--liquibase formatted sql
--changeset nilima:V20190509173016_AL_TB_CSMR_INFO6.sql
ALTER TABLE TB_CSMR_INFO CHANGE COLUMN `CS_ORDCROSS` `CS_ORDCROSS` varchar(1000) DEFAULT NULL COMMENT 'Owner Road Of the Consumer';
--liquibase formatted sql
--changeset nilima:V20190509173016_AL_TB_CSMR_INFO7.sql
ALTER TABLE TB_CSMR_INFO_HIST CHANGE COLUMN `CS_ADD` `CS_ADD` varchar(1000) DEFAULT NULL COMMENT 'Address of the consumer';
--liquibase formatted sql
--changeset nilima:V20190509173016_AL_TB_CSMR_INFO8.sql
ALTER TABLE TB_CSMR_INFO_HIST CHANGE COLUMN `CS_BADD` `CS_BADD` varchar(1000) DEFAULT NULL COMMENT 'Billing address ';
--liquibase formatted sql
--changeset nilima:V20190509173016_AL_TB_CSMR_INFO9.sql
ALTER TABLE TB_CSMR_INFO_HIST CHANGE COLUMN `CS_OADD` `CS_OADD` varchar(1000) DEFAULT NULL COMMENT 'Address of property owner';
--liquibase formatted sql
--changeset nilima:V20190509173016_AL_TB_CSMR_INFO10.sql
ALTER TABLE TB_CSMR_INFO_HIST CHANGE COLUMN `CS_OBLDPLT` `CS_OBLDPLT` varchar(1000) DEFAULT NULL COMMENT 'Owner Building / Plot no. Of the Consumer';
--liquibase formatted sql
--changeset nilima:V20190509173016_AL_TB_CSMR_INFO11.sql
ALTER TABLE TB_CSMR_INFO_HIST CHANGE COLUMN `CS_OLANEAR` `CS_OLANEAR` varchar(1000) DEFAULT NULL COMMENT 'Owner lane Of the Consumer';
--liquibase formatted sql
--changeset nilima:V20190509173016_AL_TB_CSMR_INFO12.sql
ALTER TABLE TB_CSMR_INFO_HIST CHANGE COLUMN `CS_ORDCROSS` `CS_ORDCROSS` varchar(1000) DEFAULT NULL COMMENT 'Owner Road Of the Consumer';

