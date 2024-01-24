--liquibase formatted sql
--changeset Anil:V20200601111602__CR_TB_ML_LICFAMDTL_01062020.sql
drop table if exists TB_ML_LICFAMDTL;
--liquibase formatted sql
--changeset Anil:V20200601111602__CR_TB_ML_LICFAMDTL_010620201.sql
create table TB_ML_LICFAMDTL(
FAM_MEMID bigint(12) NOT NULL COMMENT 'Primary Key',
TRD_ID bigint(12) NOT NULL COMMENT 'FOREIGN KEY REFERENCES TABLE TB_ML_TRADE_MAST',
MEM_NAME varchar(200) DEFAULT NULL,      
MEM_UIDNO BigInt(12) DEFAULT NULL,  
MEM_AGE BigInt(2) DEFAULT NULL,
MEM_RELATION varchar(50) DEFAULT NULL,
ORGID bigint(12) NOT NULL COMMENT 'Organization id',
CREATED_BY bigint(12) NOT NULL COMMENT 'User Identity',
CREATED_DATE datetime NOT NULL COMMENT 'Last Modification Date',
LG_IP_MAC varchar(100) NOT NULL COMMENT 'Client Machine Login Name|IP Address|Physical Address',
UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'Updated User Identity',
UPDATED_DATE datetime DEFAULT NULL COMMENT 'Updated Modification Date',
LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine Login Name|IP Address|Physical Address',
PRIMARY KEY (FAM_MEMID),
KEY FK_TRD_ID_idx (TRD_ID) COMMENT 'foregin key',
CONSTRAINT FK_TRD_ID FOREIGN KEY (TRD_ID) REFERENCES TB_ML_TRADE_MAST(TRD_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);

