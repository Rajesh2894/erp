--liquibase formatted sql
--changeset Kanchan:V20201008122412__CR_tb_lgl_comnt_revw_dtl_08102020.sql
drop table if exists tb_lgl_comnt_revw_dtl;
--liquibase formatted sql
--changeset Kanchan:V20201008122412__CR_tb_lgl_comnt_revw_dtl_081020201.sql
create table tb_lgl_comnt_revw_dtl
(
COMNT_ID BIGINT (12)  NOT NULL primary key,
CSE_ID BIGINT (12) NULL,
COMNT VARCHAR (400) NULL,
REVIEW VARCHAR (400) NULL,
ORGID BIGINT (12)  NOT NULL,
CR_FLAG VARCHAR (5) NULL,
CREATED_BY bigint(12) NOT NULL ,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(12) DEFAULT NULL ,
UPDATED_DATE datetime DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL, 
LG_IP_MAC_UPD varchar(100) DEFAULT NULL
);
