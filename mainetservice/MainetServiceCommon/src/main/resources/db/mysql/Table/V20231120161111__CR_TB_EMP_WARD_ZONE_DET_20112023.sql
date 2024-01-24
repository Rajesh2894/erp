--liquibase formatted sql
--changeset PramodPatil:V20231120161111__CR_TB_EMP_WARD_ZONE_DET_20112023.sql
create table TB_EMP_WARD_ZONE_DET
(
EWZ_ID bigint(20) NOT NULL,
EMP_ID bigint(20) NOT NULL,
ZONES varchar(1000) NOT NULL,
WARDS varchar(1000) NOT NULL,
STATUS varchar(10) NOT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100),
primary key (EWZ_ID)
);