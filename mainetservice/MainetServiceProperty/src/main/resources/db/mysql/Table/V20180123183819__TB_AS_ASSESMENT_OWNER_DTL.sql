--liquibase formatted sql
--changeset nilima:V20180123183819__TB_AS_ASSESMENT_OWNER_DTL1.sql 
drop table IF EXISTS TB_AS_ASSESMENT_OWNER_DTL;

--liquibase formatted sql
--changeset nilima:V20180123183819__TB_AS_ASSESMENT_OWNER_DTL2.sql 
create table TB_AS_ASSESMENT_OWNER_DTL
(
  MN_asso_id          BIGINT(12) not null,
  MN_ass_id           BIGINT(12) not null,
  MN_asso_owner_name  VARCHAR(500) not null,
  MN_gender_id        BIGINT(12),
  MN_relation_id      BIGINT(12),
  MN_asso_guardian_name VARCHAR(500) not null,
  MN_asso_mobileno    VARCHAR(20) not null,
  MN_asso_addharno    BIGINT  not null,
  MN_asso_panno       VARCHAR(10),
  MN_asso_type        CHAR(1) not null,
  MN_asso_otype       CHAR(1),
  MN_property_share   BIGINT(12) ,
  MN_asso_active      CHAR(1) default 'Y' not null,
  orgid            BIGINT(12)  not null,
  created_by       BIGINT(12) not null,
  created_date     DATETIME not null,
  updated_by       BIGINT(12),
  updated_date     DATETIME,
  lg_ip_mac        VARCHAR(100),
  lg_ip_mac_upd    VARCHAR(100),
  MN_ASSO_START_DATE	date,
  MN_ASSO_END_DATE	date,
  FA_YEARID BIGINT(12)	 NULL DEFAULT NULL 	  COMMENT '	FINANCIAL ID');

--liquibase formatted sql
--changeset nilima:V20180123183819__TB_AS_ASSESMENT_OWNER_DTL3.sql 
  alter table TB_AS_ASSESMENT_OWNER_DTL add constraint PK_owner_MN_asso_id primary key (MN_asso_id);
  
--liquibase formatted sql
--changeset nilima:V20180123183819__TB_AS_ASSESMENT_OWNER_DTL4.sql
alter table TB_AS_ASSESMENT_OWNER_DTL add constraint FK_owner_MN_ass_id foreign key (MN_ass_id)
  references TB_AS_ASSESMENT_MAST (MN_ass_id);