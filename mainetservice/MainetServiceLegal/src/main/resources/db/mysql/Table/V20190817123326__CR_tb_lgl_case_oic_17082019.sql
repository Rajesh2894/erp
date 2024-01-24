--liquibase formatted sql
--changeset Anil:V20190817123326__CR_tb_lgl_case_oic_17082019.sql
drop table if exists tb_lgl_case_oic;
--liquibase formatted sql
--changeset Anil:V20190817123326__CR_tb_lgl_case_oic_170820191.sql
create table tb_lgl_case_oic(
OIC_ID bigint(12) NOT NULL COMMENT 'Primary Key',
CSE_ID bigint(12) NOT NULL COMMENT 'foregin key tb_lgl_case_mas',
OIC_Name Varchar(200) NOT NULL COMMENT 'Office Incharge Name',
OIC_DSG Varchar(200) NOT NULL COMMENT 'Office Incharge Designation',
OIC_PHONENO varchar(50) NOT NULL COMMENT 'Office Incharge Phoneno',
OIC_EMAILID Varchar(250) NOT NULL COMMENT 'Office Incharge Email id',
ORGID bigint(12) NOT NULL COMMENT 'organization id',
CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
PRIMARY KEY(OIC_ID),
KEY FK_OICCSEID_idx(CSE_ID),
CONSTRAINT FK_OICCSEID FOREIGN KEY(CSE_ID) REFERENCES tb_lgl_case_mas(cse_id) ON DELETE NO ACTION ON UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
