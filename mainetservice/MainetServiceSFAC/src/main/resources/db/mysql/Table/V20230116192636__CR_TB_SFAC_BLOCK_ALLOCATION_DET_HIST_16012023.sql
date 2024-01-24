--liquibase formatted sql
--changeset Kanchan:V20230116192636__CR_TB_SFAC_BLOCK_ALLOCATION_DET_HIST_16012023.sql
create table TB_SFAC_BLOCK_ALLOCATION_DET_HIST
(
BD_ID_H bigint(20) NOT NULL,
BLCK_ID_H bigint(20) NOT NULL,
BD_ID bigint(20) NOT NULL,
BLCK_ID bigint(20) NULL DEFAULT NULL,
SDB1 bigint(20) NULL DEFAULT NULL,
SDB2 bigint(20) NULL DEFAULT NULL,
SDB3 bigint(20) NULL DEFAULT NULL,
SDB4 bigint(20) NULL DEFAULT NULL,
SDB5 bigint(20) NULL DEFAULT NULL,
ALLOCATION_CATEGORY bigint(20) NULL DEFAULT NULL,
ALLOCATION_SUB_CATEGORY bigint(20) NULL DEFAULT NULL,
ALLOCATION_YEAR_TO_CBBO bigint(20) NULL DEFAULT NULL,
APPLICATION_ID bigint(20) NULL DEFAULT NULL,
STATUS Varchar(50) NULL DEFAULT NULL,
REASON varchar(200) NULL DEFAULT NULL,
CBBO_ID bigint(20) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
H_STATUS char(1) NULL DEFAULT NULL,
primary key(BD_ID_H),
KEY FK_KEY_BLCK_ID_H_2(BLCK_ID_H),
CONSTRAINT FK_KEY_BLCK_ID_H_2 FOREIGN KEY (BLCK_ID_H) REFERENCES Tb_SFAC_Block_Allocation_Hist (BLCK_ID_H)
);