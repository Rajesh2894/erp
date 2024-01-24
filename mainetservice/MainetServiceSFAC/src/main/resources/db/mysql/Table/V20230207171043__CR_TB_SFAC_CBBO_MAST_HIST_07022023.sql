--liquibase formatted sql
--changeset Kanchan:V20230207171043__CR_TB_SFAC_CBBO_MAST_HIST_07022023.sql
create table TB_SFAC_CBBO_MAST_HIST
(
CBBO_ID_H bigint(20) NOT NULL,
CBBO_ID bigint(20) NOT NULL,
CBBO_NAME varchar(200) NOT NULL,
CBBO_UNIQUE_ID varchar(50) NOT NULL,
IA_NAME varchar(200) NULL DEFAULT NULL,
CBBO_ONBOARDING_YEAR datetime NULL DEFAULT NULL,
SDB1 varchar(250) NULL DEFAULT NULL,
IA_ID bigint(20) NULL DEFAULT NULL,
PAN_NO varchar(10) NULL DEFAULT NULL,
ADDRESS varchar(350) NULL DEFAULT NULL,
PIN_CODE varchar(20) NULL DEFAULT NULL,
TYPE_OF_PROM_AGE bigint(20) NULL DEFAULT NULL,
DEPTID bigint(20) NULL DEFAULT NULL,
CBBO_APPOINTMENT_YEAR bigint(20) NULL DEFAULT NULL,
APPROVED char(1) NULL DEFAULT NULL,
APPL_PENDING_DATE datetime NULL DEFAULT NULL,
H_STATUS char(1) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(CBBO_ID_H)
);
--liquibase formatted sql
--changeset Kanchan:V20230207171043__CR_TB_SFAC_CBBO_MAST_HIST_070220231.sql
create table TB_SFAC_CBBO_DET_HIST
(
CBBOD_ID_H bigint(20) NOT NULL,
CBBO_ID_H bigint(20) NOT NULL,
CBBOD_ID bigint(20) NOT NULL,
DSG_ID bigint(20) NULL DEFAULT NULL,
TITLE_ID bigint(20) NULL DEFAULT NULL,
F_NAME varchar(250) NULL DEFAULT NULL,
M_NAME varchar(250) NULL DEFAULT NULL,
L_NAME varchar(250) NULL DEFAULT NULL,
EMAIL_ID varchar(250) NULL DEFAULT NULL,
CONTACT_NO varchar(20) NULL DEFAULT NULL,
STATUS char(1) NULL DEFAULT NULL,
H_STATUS char(1) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(CBBOD_ID_H),
KEY FK_CBBO_ID_H_idx_67 (CBBO_ID_H),
CONSTRAINT FK_CBBO_ID_H_67 FOREIGN KEY (CBBO_ID_H) REFERENCES TB_SFAC_CBBO_MAST_HIST (CBBO_ID_H)
);
--liquibase formatted sql
--changeset Kanchan:V20230207171043__CR_TB_SFAC_CBBO_MAST_HIST_070220232.sql
create table TB_SFAC_IA_MAST_HIST
(
IAH_ID bigint(20) NOT NULL,
IA_ID bigint(20) NOT NULL,
IA_NAME varchar(200) NOT NULL,
IA_ONBOARDING_YEAR bigint(20) NULL DEFAULT NULL,
IA_ADDRESS varchar(350) NULL DEFAULT NULL,
IA_PIN_CODE varchar(20) NULL DEFAULT NULL,
DEPTID bigint(20) NULL DEFAULT NULL,
LEVEL char(1) NULL DEFAULT NULL,
STATE bigint(20) NULL DEFAULT NULL,
IA_SHORT_NAME varchar(200) NULL DEFAULT NULL,
H_STATUS char(1) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(IAH_ID)
);
--liquibase formatted sql
--changeset Kanchan:V20230207171043__CR_TB_SFAC_CBBO_MAST_HIST_070220233.sql
create table TB_SFAC_IA_DET_HIST
(
IAD_ID_H bigint(20) NOT NULL,
IAH_ID bigint(20) NOT NULL,
IAD_ID bigint(20) NULL DEFAULT NULL,
DSGID varchar(100) NULL DEFAULT NULL,
TITLE_ID bigint(20) NULL DEFAULT NULL,
F_NAME varchar(250) NULL DEFAULT NULL,
M_NAME varchar(250) NULL DEFAULT NULL,
L_NAME varchar(250) NULL DEFAULT NULL,
EMAIL_ID varchar(250) NULL DEFAULT NULL,
CONTACT_NO varchar(20) NULL DEFAULT NULL,
H_STATUS char(1) NULL DEFAULT NULL,
STATUS char(1) NULL DEFAULT NULL,
EMAIL_ID_SEC varchar(50) NULL DEFAULT NULL,
remarks varchar(500) NULL DEFAULT NULL,
ROLE bigint(20) NULL DEFAULT NULL,
STATE bigint(20) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(IAD_ID_H),
KEY FK_IAH_ID_idx_67 (IAH_ID),
CONSTRAINT FK_IAH_ID_67 FOREIGN KEY (IAH_ID) REFERENCES TB_SFAC_IA_MAST_HIST (IAH_ID)
);