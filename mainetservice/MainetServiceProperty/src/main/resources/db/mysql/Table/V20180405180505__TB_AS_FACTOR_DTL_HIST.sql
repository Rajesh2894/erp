--liquibase formatted sql
--changeset nilima:V20180405180505__TB_AS_FACTOR_DTL_HIST1.sql
drop table IF EXISTS TB_AS_FACTOR_DTL_HIST;

--liquibase formatted sql
--changeset nilima:V20180405180505__TB_AS_FACTOR_DTL_HIST2.sql
create table TB_AS_FACTOR_DTL_HIST (
`MN_assf_HIST_ID` BIGINT(12),
`MN_assf_id`          BIGINT(12) not null,
`MN_assd_id`           BIGINT(12) not null,
`MN_ass_id`            BIGINT(12) not null,
`MN_assf_factor`       BIGINT not null,
`MN_assf_factor_id`    BIGINT not null,
`MN_assf_factor_value_id` BIGINT not null,
`MN_assf_active`       CHAR(1) default 'Y' not null,
`orgid`             BIGINT(12) not null,
`created_by`        BIGINT(12) not null,
`created_date`      DATETIME not null,
`updated_by`        BIGINT(12),
`updated_date`      DATETIME,
`lg_ip_mac`         VARCHAR(100),
`lg_ip_mac_upd`     VARCHAR(100),
`MN_ASSO_START_DATE`	date,
`MN_ASSO_END_DATE`	date,
`H_STATUS` char(1)  NULL DEFAULT NULL 	  COMMENT '	X	');
  
--liquibase formatted sql
--changeset nilima:V20180405180505__TB_AS_FACTOR_DTL_HIST3.sql
  alter table TB_AS_FACTOR_DTL_HIST  add constraint PK_MN_assf_HIST_ID primary key (MN_assf_HIST_ID);
