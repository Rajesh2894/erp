--liquibase formatted sql
--changeset nilima:V20180123183945__TB_AS_PRO_ASSESMENT_DETAIL1.sql
Drop table IF EXISTS TB_AS_PRO_ASSESMENT_DETAIL;

--liquibase formatted sql
--changeset nilima:V20180123183945__TB_AS_PRO_ASSESMENT_DETAIL2.sql
create table TB_AS_PRO_ASSESMENT_DETAIL (
`pro_assd_id`	bigint(12)	 NOT NULL	  COMMENT '	primary key	',
`pro_ass_id`	bigint(12)	 NOT NULL	  COMMENT '	foregin key Foregin Key(TB_AS_PRO_ASSESMENT_MAST)	',
`pro_assd_unit_type_id`	bigint(20)	 NOT NULL	  COMMENT '	Unit Type	',
`pro_assd_floor_no`	bigint(3)	 NOT NULL	  COMMENT '	Floor No (Prefix ''IDE'')	',
`pro_assd_buildup_area`	decimal(15,2)	 NOT NULL	  COMMENT '	Floorwise Build Up Area	',
`pro_assd_usagetype1`	bigint(12)	 NULL DEFAULT NULL 	  COMMENT '	Usage Type (Prefix ''USA'')	',
`pro_assd_usagetype2`	bigint(12)	 NULL DEFAULT NULL 	  COMMENT '	Usage SubType (Prefix ''USA'')	',
`pro_assd_usagetype3`	bigint(12)	 NULL DEFAULT NULL 	  COMMENT '	Usage SubType (Prefix ''USA'')	',
`pro_assd_usagetype4`	bigint(12)	 NULL DEFAULT NULL 	  COMMENT '	Usage SubType (Prefix ''USA'')	',
`pro_assd_usagetype5`	bigint(12)	 NULL DEFAULT NULL 	  COMMENT '	Usage SubType (Prefix ''USA'')	',
`pro_assd_constru_type`	bigint(12)	 NOT NULL	  COMMENT '	Construction Type (Prefix ''CSC'')	',
`pro_assd_year_construction`	datetime	 NOT NULL	  COMMENT '	Construction complection date	',
`pro_assd_occupancy_type`	bigint(12)	 NOT NULL	  COMMENT '	Occupancy Type (Prefix ''OCS'')	',
`pro_assd_assesment_date`	datetime	 NOT NULL	  COMMENT '	Assessment Date	',
`pro_assd_annual_rent`	decimal(15,2)	 NULL DEFAULT NULL 	  COMMENT '	Annual Rent of Unit	',
`pro_assd_std_rate`	decimal(15,2)	 NULL DEFAULT NULL 	  COMMENT '	Standard Rate	',
`pro_assd_alv`	decimal(15,2)	 NULL DEFAULT NULL 	  COMMENT '	Annual Rate Value	',
`pro_assd_rv`	decimal(15,2)	 NULL DEFAULT NULL 	  COMMENT '	Rateable Value	',
`pro_assd_cv`	decimal(15,2)	 NULL DEFAULT NULL 	  COMMENT '	Capital Value	',
`pro_assd_active`	char(1)	 NOT NULL  DEFAULT 'Y'	  COMMENT '	flag to identify whether the record is deleted or not. ''y''  for not deleted (active) record and ''n'' for deleted (inactive) . 	',
`pro_assd_road_factor`	bigint(12) NULL DEFAULT NULL 	  COMMENT '	ROAD FACTOR	',
`pro_assd_unit_no`	bigint(12) NULL DEFAULT NULL 	  COMMENT '	UNIT NUMBER	',
`pro_assd_occupier_name` varchar(500)	 NULL DEFAULT NULL 	  COMMENT '	occupier name	',
`pro_assd_monthly_rent` decimal(15,2)	 NULL DEFAULT NULL 	  COMMENT '	Monthly Rent	',
`orgid`	bigint(12)	 NOT NULL	  COMMENT '	orgnisation id	',
`created_by`	bigint(12)	 NOT NULL	  COMMENT '	user id who created the record	',
`created_date`	datetime	 NOT NULL	  COMMENT '	record creation date	',
`updated_by`	bigint(12)	 NULL DEFAULT NULL 	  COMMENT '	user id who update the data	',
`updated_date`	datetime	 NULL DEFAULT NULL 	  COMMENT '	date on which data is going to update	',
`lg_ip_mac`	varchar(100)	 NULL DEFAULT NULL 	  COMMENT '	client machine?s login name | ip address | physical address	',
`lg_ip_mac_upd`	varchar(100)	 NULL DEFAULT NULL 	  COMMENT '	updated client machine?s login name | ip address | physical address	',
`pro_FA_YEARID`	bigint(12)	 NULL DEFAULT NULL 	  COMMENT '	Latest Yearid	');

--liquibase formatted sql
--changeset nilima:V20180123183945__TB_AS_PRO_ASSESMENT_DETAIL3.sql
alter table TB_AS_PRO_ASSESMENT_DETAIL add constraint PK_PRO_ASSD_ID primary key (PRO_ASSD_ID);

--liquibase formatted sql
--changeset nilima:V20180123183945__TB_AS_PRO_ASSESMENT_DETAIL4.sql
alter table TB_AS_PRO_ASSESMENT_DETAIL   add constraint FK_PRO_ASS_ID foreign key (PRO_ASS_ID)
references TB_AS_PRO_ASSESMENT_MAST (PRO_ASS_ID);
