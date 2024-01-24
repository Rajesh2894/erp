--liquibase formatted sql
--changeset Anil:V20190920173004__CR_tb_lgl_legal_opinion_20092019.sql
drop table if exists tb_lgl_legal_opinion;
--liquibase formatted sql
--changeset Anil:V20190920173004__CR_tb_lgl_legal_opinion_200920191.sql
CREATE TABLE tb_lgl_legal_opinion(
ID bigint(12) NOT NULL,
CSE_ID bigint(12) NOT NULL,
LOC_ID bigint(12) NOT NULL,
DEPT_ID bigint(12) NOT NULL,
MATTER_OF_DISPUTE varchar(200) NOT NULL,
OPINION varchar(200) NOT NULL,
SECTION_ACT_APPLIED varchar(100) NOT NULL,
REMARK varchar(200) DEFAULT NULL,
DEPARTMENT_REMARK varchar(200) NOT NULL,
APM_APPLICATION_ID bigInt(12) NOT NULL,
SM_SERVICE_ID bigInt(12) NOT NULL,
ORGID bigint(12) NOT NULL,
CREATED_BY bigint(12) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(12) DEFAULT NULL,
UPDATED_DATE datetime DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
PRIMARY KEY(ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

