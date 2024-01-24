--liquibase formatted sql
--changeset Kanchan:V20230522191138__CR_TB_SFAC_FIN_RATIO_SHEET_MASTER_22052023.sql
create table TB_SFAC_FIN_RATIO_SHEET_MASTER
(
FR_ID bigint(20) NOT NULL,
CBBO_ID bigint(20) NOT NULL,
CBBO_NAME varchar(100) NULL DEFAULT NULL,
FPO_ID bigint(20) NOT NULL,
FPO_NAME varchar(100) NULL DEFAULT NULL,
FPO_ADDRESS Varchar(500) NULL DEFAULT NULL,
CRP_DT_FROM Date NULL DEFAULT NULL,
CRP_DT_TO Date NULL DEFAULT NULL,
PRP_DT_FROM Date NULL DEFAULT NULL,
PRP_DT_TO Date NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key (FR_ID)
);
--liquibase formatted sql
--changeset Kanchan:V20230522191138__CR_TB_SFAC_FIN_RATIO_SHEET_MASTER_220520231.sql
create table TB_SFAC_FIN_RATIO_KEY_PARAM
(
FRK_ID bigint(20) NOT NULL,
FR_ID bigint(20) NOT NULL,
KEY_PARAMETER bigint(20) NULL DEFAULT NULL,
KEY_PARAMETER_DESC Varchar(100) NULL DEFAULT NULL,
CRP_RATIO Decimal(15,2) NULL DEFAULT NULL,
PRP_RATIO Decimal(15,2) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key (FRK_ID),
KEY FK_KEY_FR_ID_1 (FR_ID),
CONSTRAINT FK_KEY_FR_ID_1 FOREIGN KEY (FR_ID) REFERENCES TB_SFAC_FIN_RATIO_SHEET_MASTER (FR_ID)
);