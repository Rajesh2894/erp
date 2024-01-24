--liquibase formatted sql
--changeset Anil:V20190812145846__CR_TB_HOARDING_MAS_12082019.sql
drop table if exists TB_HOARDING_MAS;
--liquibase formatted sql
--changeset Anil:V20190812145846__CR_TB_HOARDING_MAS_120820191.sql
CREATE TABLE TB_HOARDING_MAS(
HRD_ID bigint(12) NOT NULL COMMENT 'Primary key',
HRD_NO varchar(50) NOT NULL COMMENT 'Hoarding number',
HRD_OLDNO varchar(100) DEFAULT NULL COMMENT 'legacy system number',
HRD_REGDATE date NOT NULL COMMENT 'Registration date',
GIS_ID varchar(20) DEFAULT NULL COMMENT 'Number',
HRD_LOCID bigint(12) NOT NULL COMMENT 'location id',
HRD_ZONE1 bigint(12) NOT NULL COMMENT 'Prefix id',
HRD_ZONE2 bigint(12) DEFAULT NULL COMMENT 'Prefix id',
HRD_ZONE3 bigint(12) DEFAULT NULL COMMENT 'Prefix id',
HRD_ZONE4 bigint(12) DEFAULT NULL COMMENT 'Prefix id',
HRD_ZONE5 bigint(12) DEFAULT NULL COMMENT 'Prefix id',
HRD_TYPE_ID1 bigint(12) NOT NULL COMMENT 'Prefix Id Level 1',
HRD_TYPE_ID2 bigint(12) DEFAULT NULL COMMENT 'Prefix Id Level 2',
HRD_TYPE_ID3 bigint(12) DEFAULT NULL COMMENT 'Prefix Id Level 3',
HRD_TYPE_ID4 bigint(12) DEFAULT NULL COMMENT 'Prefix Id Level 4',
HRD_TYPE_ID5 bigint(12) DEFAULT NULL COMMENT 'Prefix Id Level 5',
DISPL_TYPID bigint(12) NOT NULL COMMENT 'Prefix id',
HRD_DESC varchar(100) NOT NULL COMMENT 'Text',
HRD_LENGTH decimal(12,2) NOT NULL COMMENT 'Text',
HRD_HEIGHT decimal(12,2) NOT NULL COMMENT 'Text',
HRD_AREA decimal(12,2) NOT NULL COMMENT 'Text',
PROPERTY_TYPID bigint(12) NOT NULL COMMENT 'Private/Govt-Prefix',
PROPERTY_ID varchar(20) DEFAULT NULL COMMENT 'Property No',
PT_OWNER_NAME varchar(100) DEFAULT NULL COMMENT 'Property owner name',
UID_NO bigint(12) DEFAULT NULL COMMENT 'Aadhar number',
HRD_FLAG char(1) NOT NULL COMMENT 'U-Unauthorized/A-Authorized',
HRD_STATUS bigint(12) NOT NULL COMMENT 'HRD status(A-Available,N-Inactive,B-Booked)',
HRD_REMARK varchar(100) DEFAULT NULL COMMENT 'Remark',
ORGID bigint(12) NOT NULL COMMENT  'Organization id',
LANG_ID bigint(2) NOT NULL COMMENT 'Language Identity',
CREATED_BY bigint(12) NOT NULL COMMENT 'User Identity',
CREATED_DATE DATETIME NOT NULL COMMENT 'Last Modification Date',
LG_IP_MAC varchar(100) NOT NULL COMMENT 'Client Machine Login Name|IP Address|Physical Address',
UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'Updated User Identity',
UPDATED_DATE DATETIME DEFAULT NULL COMMENT 'Updated Modification Date',
LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine Login Name|IP Address|Physical Address',
PRIMARY KEY(HRD_ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


