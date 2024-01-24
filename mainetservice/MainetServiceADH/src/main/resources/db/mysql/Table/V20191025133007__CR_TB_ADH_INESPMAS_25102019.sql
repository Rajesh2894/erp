--liquibase formatted sql
--changeset Anil:V20191025133007__CR_TB_ADH_INESPMAS_25102019.sql
drop table if exists TB_ADH_INESPMAS ;
--liquibase formatted sql
--changeset Anil:V20191025133007__CR_TB_ADH_INESPMAS_251020191.sql
create table TB_ADH_INESPMAS(	
INES_ID	bigint(12) NOT NULL,	
ADH_ID bigint(12) NOT NULL,	
INES_DT	date NOT NULL,	
INES_EMPID bigint(12) NOT NULL,	
ORGID bigint(12) NOT NULL,	
CREATED_BY bigint(12) NOT NULL,	
CREATED_DATE datetime NOT NULL,	
LG_IP_MAC varchar(100) NOT NULL,
UPDATED_BY bigint(12) DEFAULT NULL,	
UPDATED_DATE datetime DEFAULT NULL,	
LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
primary key(INES_ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
