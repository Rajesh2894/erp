--liquibase formatted sql
--changeset Anil:V20200109125558__CR_TB_SEQ_CONFIGDET_09012020.sql
drop table if exists TB_SEQ_CONFIGDET;
--liquibase formatted sql
--changeset Anil:V20200109125558__CR_TB_SEQ_CONFIGDET_090120201.sql
CREATE TABLE tb_seq_configdet (
SEQ_DETID bigint(12) NOT NULL COMMENT 'Primary Key',
SEQ_CONFID bigint(12) NOT NULL COMMENT 'Foreign Key (TB_SEQ_CONFIGMASTER)',
SEQ_FACTID bigint(12) NOT NULL COMMENT 'Sequence Factors',
PREFIX_ID bigint(12) NOT NULL COMMENT 'Prefix Name',
DISP_PVLE char(5) NOT NULL COMMENT 'Display Prefix Value',
SEQ_ORDER bigint(12) NOT NULL,
ORGID bigint(19) NOT NULL,
CREATED_BY bigint(10) NOT NULL,
CREATED_DATE datetime NOT NULL COMMENT 'Date on which data is going to create',
LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'Client Machine?s Login Name | IP Address | Physical Address',
LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine?s Login Name | IP Address | Physical Address',
PRIMARY KEY (SEQ_CONFID),
KEY FK_SEQ_CONFID (SEQ_CONFID),
CONSTRAINT FK_SEQ_CONFID FOREIGN KEY (SEQ_CONFID) REFERENCES tb_seq_configmaster (SEQ_CONFID) ON DELETE NO ACTION ON UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
