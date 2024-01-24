--liquibase formatted sql
--changeset nilima:V20181016150500_AL_TB_AS_PRO_BILL_MAS1.sql
ALTER TABLE TB_AS_PRO_BILL_MAS ADD COLUMN PRO_GEN_DES CHAR(1) NULL COMMENT'IF BILL GENERTED BY DES(DATA ENTRY SUIT) THE FLAG WILL BE Y' AFTER PRO_BM_ENTRY_FLAG;
--liquibase formatted sql
--changeset nilima:V20181016150500_AL_TB_AS_PRO_BILL_MAS2.sql
ALTER TABLE TB_AS_PRO_BILL_MAS_HIST ADD COLUMN PRO_GEN_DES CHAR(1) NULL COMMENT'IF BILL GENERTED BY DES(DATA ENTRY SUIT) THE FLAG WILL BE Y' AFTER PRO_BM_ENTRY_FLAG;
--liquibase formatted sql
--changeset nilima:V20181016150500_AL_TB_AS_PRO_BILL_MAS3.sql
ALTER TABLE TB_AS_BILL_MAS_HIST ADD COLUMN MN_GEN_DES CHAR(1) NULL COMMENT'IF BILL GENERTED BY DES(DATA ENTRY SUIT) THE FLAG WILL BE Y' AFTER MN_ENTRY_FLAG;
--liquibase formatted sql
--changeset nilima:V20181016150500_AL_TB_AS_PRO_BILL_MAS4.sql
ALTER TABLE TB_AS_BILL_MAS ADD COLUMN MN_GEN_DES CHAR(1) NULL COMMENT'IF BILL GENERTED BY DES(DATA ENTRY SUIT) THE FLAG WILL BE Y' AFTER MN_ENTRY_FLAG;
--liquibase formatted sql
--changeset nilima:V20181016150500_AL_TB_AS_PRO_BILL_MAS5.sql
ALTER TABLE TB_AS_BILL_MAS_HIST CHANGE COLUMN MN_ASS_ID MN_ASS_ID BIGINT(12) NULL COMMENT' ASSEMENT NO';
