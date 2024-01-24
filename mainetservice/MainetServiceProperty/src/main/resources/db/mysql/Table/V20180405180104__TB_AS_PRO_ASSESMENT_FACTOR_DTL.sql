--liquibase formatted sql
--changeset nilima:V20180405180104__TB_AS_PRO_ASSESMENT_FACTOR_DTL1.sql
drop table  IF EXISTS TB_AS_PRO_ASSESMENT_FACTOR_DTL;

--liquibase formatted sql
--changeset nilima:V20180405180104__TB_AS_PRO_ASSESMENT_FACTOR_DTL2.sql
create table TB_AS_PRO_ASSESMENT_FACTOR_DTL (
pro_assf_id bigint(20) NOT NULL COMMENT '	primary key	',
pro_assd_id bigint(20) NOT NULL COMMENT '	Foregin Key(TB_AS_PRO_ASSESMENT_DETAIL)	',
pro_ass_id bigint(20) NOT NULL COMMENT '	Foregin Key(TB_AS_PRO_ASSESMENT_MAST)	',
pro_assf_factor bigint(20) NOT NULL COMMENT '	Factors (PREFIX ''FCT'')	',
pro_assf_factor_id bigint(20) NOT NULL COMMENT '	Store CPM ID of prefixes present in ''FCT'' prefix	',
pro_assf_factor_value_id bigint(20) NOT NULL COMMENT '	Factors value	',
pro_assf_active char(1) NOT NULL DEFAULT 'Y' COMMENT '	flag to identify whether the record is deleted or not. ''y''  for not deleted (active) record and ''n'' for deleted (inactive) . 	',
orgid bigint(12) NOT NULL COMMENT '	orgnisation id	',
created_by bigint(12) NOT NULL COMMENT '	user id who created the record	',
created_date datetime NOT NULL COMMENT '	record creation date	',
updated_by bigint(11) NULL DEFAULT NULL COMMENT '	user id who update the data	',
updated_date datetime NULL DEFAULT NULL COMMENT '	date on which data is going to update	',
lg_ip_mac varchar(100) NULL DEFAULT NULL COMMENT '	client machine?s login name | ip address | physical address	',
lg_ip_mac_upd varchar(100) NULL DEFAULT NULL COMMENT '	updated client machine?s login name | ip address | physical address	',
PRO_ASSO_START_DATE date NULL DEFAULT NULL,
PRO_ASSO_END_DATE date NULL DEFAULT NULL);


--liquibase formatted sql
--changeset nilima:V20180405180104__TB_AS_PRO_ASSESMENT_FACTOR_DTL3.sql
alter table TB_AS_PRO_ASSESMENT_FACTOR_DTL add constraint PK_PRO_ASSF_ID primary key (PRO_ASSF_ID);

--liquibase formatted sql
--changeset nilima:V20180405180104__TB_AS_PRO_ASSESMENT_FACTOR_DTL4.sql
alter table TB_AS_PRO_ASSESMENT_FACTOR_DTL add constraint FK_PRO_ASSF_ASSD_ID foreign key (PRO_ASSD_ID) references TB_AS_PRO_ASSESMENT_DETAIL (PRO_ASSD_ID);

--liquibase formatted sql
--changeset nilima:V20180405180104__TB_AS_PRO_ASSESMENT_FACTOR_DTL5.sql
alter table TB_AS_PRO_ASSESMENT_FACTOR_DTL add constraint FK_FACTOR_PRO_ASS_ID FOREIGN KEY (PRO_ASS_ID) references TB_AS_PRO_ASSESMENT_MAST (PRO_ASS_ID);

