--liquibase formatted sql
--changeset Anil:V20191227132255__CR_tb_swd_Scheme_Configuration_27122019.sql
drop table if exists tb_swd_Scheme_Configuration;
--liquibase formatted sql
--changeset Anil:V20191227132255__CR_tb_swd_Scheme_Configuration_271220191.sql
create table tb_swd_Scheme_Configuration(
CONFID bigint(12) NOT NULL,
SDSCH_ID bigint(12) NOT NULL,
FROM_DT Date NOT NULL,
TO_DT Date NOT NULL,
BENF_CNT bigint(12) NOT NULL,
ORGID bigint(12) NOT NULL,
CREATED_BY bigint(12) NOT NULL,
CREATION_DATE datetime NOT NULL,
LG_IP_MAC varchar(100) NOT NULL,
UPDATED_BY bigint(12) DEFAULT NULL,
UPDATED_DATE datetime DEFAULT NULL,
LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
PRIMARY KEY (CONFID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
--liquibase formatted sql
--changeset Anil:V20191227132255__CR_tb_swd_Scheme_Configuration_271220192.sql
drop table if exists tb_swd_Scheme_Configuration_hist;
--liquibase formatted sql
--changeset Anil:V20191227132255__CR_tb_swd_Scheme_Configuration_271220193.sql
create table tb_swd_Scheme_Configuration_hist(
CONFID_H bigint(12) NOT NULL,
CONFID bigint(12) NOT NULL,
SDSCH_ID bigint(12) NOT NULL,
FROM_DT Date NOT NULL,
TO_DT Date NOT NULL,
BENF_CNT bigint(12) NOT NULL,
ORGID bigint(12) NOT NULL,
CREATED_BY bigint(12) NOT NULL,
CREATION_DATE datetime NOT NULL,
LG_IP_MAC varchar(100) NOT NULL,
UPDATED_BY bigint(12) DEFAULT NULL,
UPDATED_DATE datetime DEFAULT NULL,
LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
H_STATUS varchar(2) DEFAULT NULL,
PRIMARY KEY (CONFID_H)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
