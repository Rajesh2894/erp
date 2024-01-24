--liquibase formatted sql
--changeset Anil:V20190917160114__CR_tb_adh_license_validity_history_17092019.sql
drop table if exists tb_adh_license_validity_history;
--liquibase formatted sql
--changeset Anil:V20190917160114__CR_tb_adh_license_validity_history_170920191.sql
CREATE TABLE tb_adh_license_validity_history(
LIC_ID_H bigint(12) NOT NULL COMMENT 'Primary key',
LIC_ID bigint(12) NOT NULL COMMENT 'LIC id',
DEPT_ID bigint(12) NOT NULL COMMENT 'Department id',
SERVICE_ID bigint(12) NOT NULL COMMENT 'Service id',
LIC_TYPE bigint(12) NOT NULL COMMENT 'LIC type',
LIC_DEPENDS_ON bigint(12) NOT NULL COMMENT 'LIC depnds on',
LIC_TENURE VARCHAR(50) NOT NULL COMMENT 'LIC tenure',
UNIT bigint(12) NOT NULL COMMENT 'unit',
H_STATUS varchar(2) DEFAULT NULL COMMENT 'history status',
ORGID bigint(12) NOT NULL COMMENT  'Organization id',
CREATED_BY bigint(12) NOT NULL COMMENT 'User Identity',
CREATED_DATE DATETIME NOT NULL COMMENT 'Last Modification Date',
LG_IP_MAC varchar(100) NOT NULL COMMENT 'Client Machine Login Name|IP Address|Physical Address',
LANG_ID bigint(12) NOT NULL COMMENT 'Language Identity',
UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'Updated User Identity',
UPDATED_DATE DATETIME DEFAULT NULL COMMENT 'Updated Modification Date',
LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine Login Name|IP Address|Physical Address',
PRIMARY KEY(LIC_ID_H)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
