--liquibase formatted sql
--changeset nilima:V20180613154520__AL_TB_CONTRACT_DETAIL_12062018.sql
ALTER TABLE TB_CONTRACT_DETAIL
ADD COLUMN CONT_DEFECTLIABILITYPER BIGINT(12) AFTER CONT_INSTALLMENT_PERIOD,
ADD COLUMN CONT_DEFECTLIABILITYUNI BIGINT(12) AFTER CONT_DEFECTLIABILITYPER,
ADD COLUMN CONT_TIMEEXTPER BIGINT(3) NULL COMMENT 'Contract Time Exten' AFTER LG_IP_MAC_UPD,
ADD COLUMN CONT_TIMEEXTUNIT BIGINT(12) NULL COMMENT 'Contract Time Extension Unit' AFTER CONT_TIMEEXTPER,
ADD COLUMN CONT_TIMEEXTEMPID BIGINT(12) NULL AFTER CONT_TIMEEXTUNIT;
