--liquibase formatted sql
--changeset nilima:V20180222154948_TB_AS_PRO_ASSESMENT_OWNER_DTL1.sql
drop table IF EXISTS TB_AS_PRO_ASSESMENT_OWNER_DTL;

--liquibase formatted sql
--changeset nilima:V20180123183850__TB_AS_PRO_ASSESMENT_OWNER_DTL2.sql
create table TB_AS_PRO_ASSESMENT_OWNER_DTL (
pro_asso_id	bigint(12)	 NOT NULL	  COMMENT '	primary key	',
pro_ass_id	bigint(12)	 NOT NULL	  COMMENT '	foregin key Foregin Key(TB_AS_ASSESMENT_MAST)	',
pro_asso_owner_name	varchar(500)	 NOT NULL	  COMMENT '	Owner Name/Company Trust Name	',
pro_gender_id	bigint(12)	 NULL DEFAULT NULL 	  COMMENT '	Owner Gender (prefix ''GEN'')	',
pro_relation_id	bigint(12)	 NULL DEFAULT NULL 	  COMMENT '	Owner Husband Father Name/Contact Person Name	',
pro_asso_guardian_name	varchar(500)	 NOT NULL	  COMMENT 'Owner Guardian Name',
pro_asso_mobileno	varchar(20)	 NOT NULL	  COMMENT '	Owner/Company Mobile No	',
pro_asso_addharno	bigint(20)	 NULL	  COMMENT '	Owner Addhar  No	',
pro_asso_panno	varchar(10)	 NULL DEFAULT NULL 	  COMMENT '	Company Pan No.	',
pro_asso_type	char(1)	 NOT NULL	  COMMENT '	O->Owner  T->Other	',
pro_asso_otype	char(1)	 NULL DEFAULT NULL 	  COMMENT '	P->Primary Owner S->Secondary Owner	',
pro_property_share	bigint(20)	 NULL DEFAULT NULL 	  COMMENT '		',
pro_asso_active	char(1)	 NOT NULL  DEFAULT 'Y' 	  COMMENT '	flag to identify whether the record is deleted or not. ''y''  for not deleted (active) record and ''n'' for deleted (inactive) . 	',
orgid	bigint(12)	 NOT NULL	  COMMENT '	orgnisation id	',
created_by	bigint(12)	 NOT NULL	  COMMENT '	user id who created the record	',
created_date	datetime	 NOT NULL	  COMMENT '	record creation date	',
updated_by	bigint(12)	 NULL DEFAULT NULL 	  COMMENT '	user id who update the data	',
updated_date	datetime	 NULL DEFAULT NULL 	  COMMENT '	date on which data is going to update	',
lg_ip_mac	varchar(100)	 NULL DEFAULT NULL 	  COMMENT '	client machine?s login name | ip address | physical address	',
lg_ip_mac_upd	varchar(100)	 NULL DEFAULT NULL 	  COMMENT '	updated client machine?s login name | ip address | physical address	',
PRO_ASSO_START_DATE	date	 NULL DEFAULT NULL 	  COMMENT '	Owner start date, Initial date will be first filing date against the PID, in case of mutation the transfer date will be treat as start date for new owner	' 			,
PRO_ASSO_END_DATE	date	 NULL DEFAULT NULL 	  COMMENT '	Owner End date, initial date will be blank and in case of mutation the transfer date will treat as end date',
FA_YEARID BIGINT(12)	 NULL DEFAULT NULL 	  COMMENT '	FINANCIAL ID');

--liquibase formatted sql
--changeset nilima:V20180222154948__TB_AS_PRO_ASSESMENT_OWNER_DTL3.sql
alter table TB_AS_PRO_ASSESMENT_OWNER_DTL add constraint PK_pro_asso_ID primary key (pro_asso_ID);

--liquibase formatted sql
--changeset nilima:V20180222154948__TB_AS_PRO_ASSESMENT_OWNER_DTL4.sql
alter table TB_AS_PRO_ASSESMENT_OWNER_DTL add constraint FK_pro_asso_ASS_ID foreign key (PRO_ASS_ID)
  references TB_AS_PRO_ASSESMENT_MAST (PRO_ASS_ID);