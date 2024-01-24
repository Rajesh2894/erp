--liquibase formatted sql
--changeset nilima:V20180405180506__TB_AS_OWNER_DTL_HIST1.sql 
drop table IF EXISTS TB_AS_OWNER_DTL_HIST;
--liquibase formatted sql
--changeset nilima:V20180405180506__TB_AS_OWNER_DTL_HIST2.sql 
create table TB_AS_OWNER_DTL_HIST (
`MN_asso_HIST_ID` BIGINT(12),
`MN_asso_id`          BIGINT(12) not null,
`MN_ass_id`           BIGINT(12) not null,
`MN_asso_owner_name`  VARCHAR(500) not null,
`MN_gender_id`        BIGINT(12),
`MN_relation_id`      BIGINT(12),
`MN_asso_guardian_name` VARCHAR(500) not null,
`MN_asso_mobileno`    VARCHAR(20) not null,
`MN_asso_addharno`    BIGINT  null,
`MN_asso_panno`       VARCHAR(10),
`MN_asso_type`        CHAR(1) not null,
`MN_asso_otype`       CHAR(1),
`MN_property_share`   BIGINT(12) ,
`MN_asso_active`      CHAR(1) default 'Y' not null,
`orgid`            BIGINT(12)  not null,
`created_by`       BIGINT(12) not null,
`created_date`     DATETIME not null,
`updated_by`       BIGINT(12),
`updated_date`     DATETIME,
`lg_ip_mac`        VARCHAR(100),
`lg_ip_mac_upd`    VARCHAR(100),
`MN_ASSO_START_DATE`	date,
`MN_ASSO_END_DATE`	date,
`MN_ASS_no`              VARCHAR(20) not null,
`H_STATUS` char(1)  NULL DEFAULT NULL 	  COMMENT '	X	');

--liquibase formatted sql
--changeset nilima:V20180405180506__TB_AS_OWNER_DTL_HIST3.sql 
  alter table TB_AS_OWNER_DTL_HIST add constraint PK_MN_asso_HIST_ID primary key (MN_asso_HIST_ID);
