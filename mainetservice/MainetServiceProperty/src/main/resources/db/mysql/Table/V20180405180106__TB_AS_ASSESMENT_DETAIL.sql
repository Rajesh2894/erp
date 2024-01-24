--liquibase formatted sql
--changeset nilima:V20180405180106__TB_AS_ASSESMENT_DETAIL1.sql
drop table IF EXISTS  TB_AS_ASSESMENT_DETAIL;

--liquibase formatted sql
--changeset nilima:V20180405180106__TB_AS_ASSESMENT_DETAIL2.sql
create table TB_AS_ASSESMENT_DETAIL
(MN_assd_id BIGINT(12) not null,
MN_ass_id BIGINT(12) not null,
MN_assd_unit_type_id BIGINT(12) not null,
MN_assd_floor_no BIGINT(3) not null,
MN_assd_buildup_area DECIMAL(15,2) not null,
MN_assd_usagetype1 BIGINT(12),
MN_assd_usagetype2 BIGINT(12),
MN_assd_usagetype3 BIGINT(12),
MN_assd_usagetype4 BIGINT(12),
MN_assd_usagetype5 BIGINT(12),
MN_assd_constru_type BIGINT(12) not null,
MN_assd_year_construction DATETIME not null,
MN_assd_occupancy_type BIGINT(12) not null,
MN_assd_assesment_date DATETIME not null,
MN_assd_annual_rent DECIMAL(15,2),
MN_assd_std_rate DECIMAL(15,2),
MN_assd_alv DECIMAL(15,2),
MN_assd_rv DECIMAL(15,2),
MN_assd_cv DECIMAL(15,2),
MN_assd_active CHAR(1) default 'Y' not null,
MN_assd_road_factor bigint(12) NULL DEFAULT NULL COMMENT '	ROAD FACTOR	',
MN_assd_unit_no bigint(12) NULL DEFAULT NULL COMMENT '	UNIT NUMBER	',
MN_assd_occupier_name varchar(500)	 NULL DEFAULT NULL COMMENT '	occupier name	',
MN_assd_monthly_rent decimal(15,2)	 NULL DEFAULT NULL COMMENT '	Monthly Rent	',
orgid BIGINT(12) not null,
created_by BIGINT(12) not null,
created_date DATETIME not null,
updated_by BIGINT(12),
updated_date DATETIME,
lg_ip_mac VARCHAR(100),
lg_ip_mac_upd VARCHAR(100),
MN_FA_YEARID BIGINT(12));

--liquibase formatted sql
--changeset nilima:V20180405180106__TB_AS_ASSESMENT_DETAIL3.sql
alter table TB_AS_ASSESMENT_DETAIL   add constraint PK_MN_assd_id primary key (MN_assd_id) ;

--liquibase formatted sql
--changeset nilima:V20180405180106__TB_AS_ASSESMENT_DETAIL4.sql
alter table TB_AS_ASSESMENT_DETAIL add constraint FK_DETAIL_MN_ASS_id foreign key (MN_ASS_ID) references TB_AS_ASSESMENT_MAST (MN_ASS_id);
