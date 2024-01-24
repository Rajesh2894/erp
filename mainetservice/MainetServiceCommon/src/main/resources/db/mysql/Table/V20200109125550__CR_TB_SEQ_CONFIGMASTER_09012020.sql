--liquibase formatted sql
--changeset Anil:V20200109125550__CR_TB_SEQ_CONFIGMASTER_09012020.sql
drop table if exists TB_SEQ_CONFIGMASTER;
--liquibase formatted sql
--changeset Anil:V20200109125550__CR_TB_SEQ_CONFIGMASTER_090120201.sql
CREATE TABLE tb_seq_configmaster (
SEQ_CONFID bigint(12) NOT NULL COMMENT 'Primary Key',
SEQ_NAME varchar(30) NOT NULL COMMENT 'Sequence Name',
CAT_ID bigint(12) NOT NULL COMMENT 'Category',
DEPT_ID bigint(12) NOT NULL COMMENT 'Department Name',
SEQ_TYP bigint(12) NOT NULL COMMENT 'Sequence Type',
SEQ_SEP varchar(1) DEFAULT NULL COMMENT 'Separator',
SEQ_LENTH bigint(2) NOT NULL COMMENT 'Sequence Length',
CUST_SEQ char(1) NOT NULL COMMENT 'Custom Sequence',
SEQ_FRMNO bigint(12) DEFAULT NULL COMMENT 'Sequence Start Number',
SEQ_STATUS char(1) NOT NULL COMMENT 'Sequence Status',
ORGID bigint(19) NOT NULL,
CREATED_BY bigint(10) NOT NULL,
CREATED_DATE datetime NOT NULL COMMENT 'Date on which data is going to create',
LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'Client Machine?s Login Name | IP Address | Physical Address',
LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine?s Login Name | IP Address | Physical Address',
PRIMARY KEY (SEQ_CONFID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
