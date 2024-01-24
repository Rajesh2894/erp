--liquibase formatted sql
--changeset Kanchan:V20201222191255__CR_TB_LGL_COMNT_REVW_DTL_22122020.sql
create table TB_LGL_COMNT_REVW_DTL
(
COMNT_ID BIGINT (12) primary key,
CSE_ID BIGINT (12) null,
COMNT VARCHAR (400) null,
REVIEW VARCHAR (400) null,
ORGID BIGINT (12) not null,
CR_FLAG VARCHAR (5) null
);
--liquibase formatted sql
--changeset Kanchan:V20201222191255_CR__TB_LGL_COMNT_REVW_DTL_221220201.sql
create table TB_LGL_COMNT_REVW_DTL_HIST
(
COMNT_ID_HIS BIGINT (12) primary key,
COMNT_ID BIGINT (12) null,
CSE_ID BIGINT (12) null,
COMNT VARCHAR (400) null,
REVIEW VARCHAR (400) null,
ORGID BIGINT (12) not null,
CR_FLAG VARCHAR (5) null,
H_STATUS  CHAR (1) null
);
