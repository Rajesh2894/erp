--liquibase formatted sql
--changeset Kanchan:V20230227182459__CR_TB_SFAC_FPO_ASS_MASTER_27022023.sql
create table TB_SFAC_FPO_ASS_MASTER
(
ASS_ID bigint(20) NOT NULL,
ASSESSMENT_NO Varchar(50) NOT NULL,
APPLICATION_ID bigint(20) NULL DEFAULT NULL,
FPO_ID bigint(20) NULL DEFAULT NULL,
FPO_NAME varchar(500) NULL DEFAULT NULL,
FIN_YR_ID bigint(20) NULL DEFAULT NULL,
ASS_STATUS Varchar(50) NULL DEFAULT NULL,
REMARK varchar(100) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(ASS_ID)
);
--liquibase formatted sql
--changeset Kanchan:V20230227182459__CR_TB_SFAC_FPO_ASS_MASTER_270220231.sql
create table TB_SFAC_FPO_ASS_MAST_HIST
(
ASS_ID_H bigint(20) NOT NULL,
ASS_ID bigint(20) NOT NULL,
ASSESSMENT_NO varchar(50) NULL DEFAULT NULL,
APPLICATION_ID bigint(20) NULL DEFAULT NULL,
FPO_ID bigint(20) NULL DEFAULT NULL,
FPO_NAME varchar(500) NULL DEFAULT NULL,
FIN_YR_ID bigint(20) NULL DEFAULT NULL,
ASS_STATUS varchar(50) NULL DEFAULT NULL,
REMARK varchar(100) NULL DEFAULT NULL,
H_STATUS char(1) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(ASS_ID_H)
);
--liquibase formatted sql
--changeset Kanchan:V20230227182459__CR_TB_SFAC_FPO_ASS_MASTER_270220232.sql
create table TB_SFAC_FPO_ASS_KEY_PARAM
(
ASS_KID bigint(20) NOT NULL,
ASS_ID bigint(20) NOT NULL,
KEY_PARAMETER bigint(20) NULL DEFAULT NULL,
WEIGHTAGE bigint(20) NULL DEFAULT NULL,
KEY_PARAMETER_DESC varchar(100) NULL DEFAULT NULL,
APPLICATION_ID bigint(20) NULL DEFAULT NULL,
ASS_STATUS varchar(50) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(ASS_KID),
KEY ASS_ID_fk1 ( ASS_ID ),
CONSTRAINT CNT_ASS_ID_fk_1 FOREIGN KEY ( ASS_ID ) REFERENCES TB_SFAC_FPO_ASS_MASTER ( ASS_ID )
);
--liquibase formatted sql
--changeset Kanchan:V20230227182459__CR_TB_SFAC_FPO_ASS_MASTER_270220233.sql
create table TB_SFAC_FPO_ASS_KEY_PARAM_HIST
(
ASS_KID_H bigint(20) NOT NULL,
ASS_ID_H bigint(20) NOT NULL,
ASS_KID bigint(20) NULL DEFAULT NULL,
H_STATUS Char(1) NULL DEFAULT NULL,
KEY_PARAMETER bigint(20) NULL DEFAULT NULL,
WEIGHTAGE bigint(20) NULL DEFAULT NULL,
KEY_PARAMETER_DESC varchar(100) NULL DEFAULT NULL,
APPLICATION_ID bigint(20) NULL DEFAULT NULL,
ASS_STATUS varchar(50) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(ASS_KID_H),
KEY ASS_ID_H_fk1 ( ASS_ID_H ),
CONSTRAINT CNT_ASS_ID_H_fk_1 FOREIGN KEY ( ASS_ID_H ) REFERENCES TB_SFAC_FPO_ASS_MAST_HIST ( ASS_ID_H )
);
--liquibase formatted sql
--changeset Kanchan:V20230227182459__CR_TB_SFAC_FPO_ASS_MASTER_270220234.sql
create table TB_SFAC_FPO_ASS_SUB_PARAM
(
ASS_SID bigint(20) NOT NULL,
ASS_KID bigint(20) NOT NULL,
SUB_PARAMETER bigint(20) NULL DEFAULT NULL,
SUB_PARAMETER_DESC Varchar(500) NULL DEFAULT NULL,
APPLICATION_ID bigint(20) NULL DEFAULT NULL,
ASS_STATUS varchar(50) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(ASS_SID),
KEY ASS_KID_fk3 ( ASS_KID ),
CONSTRAINT CNT_ASS_KID_fk_3 FOREIGN KEY ( ASS_KID ) REFERENCES TB_SFAC_FPO_ASS_KEY_PARAM ( ASS_KID )
);
--liquibase formatted sql
--changeset Kanchan:V20230227182459__CR_TB_SFAC_FPO_ASS_MASTER_270220235.sql
create table TB_SFAC_FPO_ASS_SUB_PARAM_HIST
(
ASS_SID_H bigint(20) NOT NULL,
ASS_KID_H bigint(20) NOT NULL,
ASS_SID bigint(20) NULL DEFAULT NULL,
H_STATUS Char(1) NULL DEFAULT NULL,
SUB_PARAMETER bigint(20) NULL DEFAULT NULL,
SUB_PARAMETER_DESC Varchar(500) NULL DEFAULT NULL,
APPLICATION_ID bigint(20) NULL DEFAULT NULL,
ASS_STATUS varchar(50) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(ASS_SID_H),
KEY ASS_KID_H_fk5 ( ASS_KID_H ),
CONSTRAINT CNT_ASS_KID_H_fk_5 FOREIGN KEY ( ASS_KID_H ) REFERENCES TB_SFAC_FPO_ASS_KEY_PARAM_HIST ( ASS_KID_H )
);
--liquibase formatted sql
--changeset Kanchan:V20230227182459__CR_TB_SFAC_FPO_ASS_MASTER_270220236.sql
create table TB_SFAC_FPO_ASS_SUB_PARAM_DET
(
ASS_SP_ID bigint(20) NOT NULL,
ASS_SID bigint(20) NOT NULL,
CONDITIONS bigint(20) NULL DEFAULT NULL,
CONDITION_DESC varchar(200) NULL DEFAULT NULL,
SUB_WEIGHTAGE decimal(15,2) NULL DEFAULT NULL,
MARKS_AWARDED bigint(20) NULL DEFAULT NULL,
SCORE decimal(15,2) NULL DEFAULT NULL,
REMARK Varchar(500) NULL DEFAULT NULL,
APPLICATION_ID bigint(20) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(ASS_SP_ID),
KEY ASS_SID_fk4 ( ASS_SID ),
CONSTRAINT CNT_ASS_SID_fk_4 FOREIGN KEY ( ASS_SID ) REFERENCES TB_SFAC_FPO_ASS_SUB_PARAM ( ASS_SID )
);
--liquibase formatted sql
--changeset Kanchan:V20230227182459__CR_TB_SFAC_FPO_ASS_MASTER_270220237.sql
create table TB_SFAC_FPO_ASS_SUB_PARAM_DET_HIST
(
ASS_SP_ID_H bigint(20) NOT NULL,
ASS_SID_H bigint(20) NOT NULL,
ASS_SP_ID bigint(20) NULL DEFAULT NULL,
CONDITIONS bigint(20) NULL DEFAULT NULL,
CONDITION_DESC varchar(200) NULL DEFAULT NULL,
SUB_WEIGHTAGE bigint(20) NULL DEFAULT NULL,
MARKS_AWARDED bigint(20) NULL DEFAULT NULL,
SCORE decimal(15,2) NULL DEFAULT NULL,
REMARK Varchar(500) NULL DEFAULT NULL,
APPLICATION_ID bigint(20) NULL DEFAULT NULL,
H_STATUS char(1) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(ASS_SP_ID_H),
KEY ASS_SID_H_fk6 ( ASS_SID_H ),
CONSTRAINT CNT_ASS_SID_H_fk_6 FOREIGN KEY ( ASS_SID_H ) REFERENCES TB_SFAC_FPO_ASS_SUB_PARAM_HIST ( ASS_SID_H )
);
