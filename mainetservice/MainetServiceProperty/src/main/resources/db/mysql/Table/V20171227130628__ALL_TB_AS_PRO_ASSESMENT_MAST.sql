--liquibase formatted sql
--changeset nilima:V20171227130628__ALL_TB_AS_PRO_ASSESMENT_MAST1.sql
drop table IF EXISTS TB_AS_PRO_ASSESMENT_MAST;
--liquibase formatted sql
--changeset nilima:V20171227130628__ALL_TB_AS_PRO_ASSESMENT_MAST2.sql
create table TB_AS_PRO_ASSESMENT_MAST (
pro_ass_id	bigint(20)	not null	  comment '	primary key	',
pro_ass_no	varchar(20)	not null	  comment '	assesment number	',
tpp_approval_no	varchar(200)	not null	  comment '	building approval no	',
pro_ass_oldpropno	varchar(20)	not null	  comment '	old property number	',
tpp_plot_no_cs	varchar(50)	not null	  comment '	csn_khasara no	',
tpp_survey_number	varchar(25)	not null	  comment '	survey number	',
tpp_khata_no	varchar(50)	not null	  comment '	khata number	',
tpp_toji_no	varchar(50)	not null	  comment '	toji number	',
tpp_plot_no	varchar(50)	not null	  comment '	plot number	',
pro_ass_street_no	varchar(500)	not null	  comment '	street number_name	',
tpp_village_mauja	varchar(50)	not null	  comment '	village_mauja name	',
pro_ass_address	varchar(1000)	not null	  comment '	property address	',
loc_id	bigint(20)	not null	  comment '	location  foregin key(tb_location_mas)	',
pro_ass_pincode	int(11)	not null	  comment '	pincode	',
pro_ass_corr_address	varchar(1000)	not null	  comment '	correspondence address	',
pro_ass_corr_pincode	int(11)	not null	  comment '	correspondence address pincode	',
pro_ass_corr_email	varchar(25)	not null	  comment '	correspondence email	',
pro_ass_lp_receipt_no	varchar(25)	 null default null 	  comment '	last payment receipt no.	',
pro_ass_lp_receipt_amt	decimal(15,2)	 null default null 	  comment '	last payment receipt amount	',
pro_ass_lp_receipt_date	datetime	 null default null 	  comment '	last payment receipt date	',
pro_ass_lp_year	bigint(20)	 null default null 	  comment '	last payment billing cycle	',
pro_ass_lp_bill_cycle	bigint(20)	 null default null 	  comment '	bill cycle	',
bill_amount	decimal(15,2)	 null default null 	  comment '	bill amount	',
outstanding_amount	decimal(15,2)	 null default null 	  comment '	outstanding amount	',
pro_ass_acq_date	datetime	not null	  comment '	acquisition date	',
pro_ass_prop_type1	bigint(20)	not null	  comment '	property type	',
pro_ass_prop_type2	bigint(20)	 null default null 	  comment '	property subtype	',
pro_ass_prop_type3	bigint(20)	 null default null 	  comment '	property subtype	',
pro_ass_prop_type4	bigint(20)	 null default null 	  comment '	property subtype	',
pro_ass_prop_type5	bigint(20)	 null default null 	  comment '	property subtype	',
pro_ass_plot_area	decimal(15,2)	not null	  comment '	total plot area	',
pro_ass_buit_area_gr	decimal(15,2)	not null	  comment '	build up/constructed area on the ground floor	',
pro_ass_owner_type	bigint(20)	not null	  comment '	owner type (prefix owt)	',
pro_ass_ward1	bigint(20)	 null default null 	  comment '	ward1	',
pro_ass_ward2	bigint(20)	 null default null 	  comment '	ward2	',
pro_ass_ward3	bigint(20)	 null default null 	  comment '	ward3	',
pro_ass_ward4	bigint(20)	 null default null 	  comment '	ward4	',
pro_ass_ward5	bigint(20)	 null default null 	  comment '	ward5	',
pro_ass_gis_id	varchar(20)	 null default null 	  comment '	gis id	',
pro_ass_active	char(1)	not null	  comment '	flag to identify whether the record is deleted or not. ''y''  for not deleted (active) record and ''n'' for deleted (inactive) .	',
pro_ass_status	char(1)	not null	  comment '	assesment flow status	',
pro_ass_aut_status	char(1)	 null default null 	  comment '	authorisation status	',
pro_ass_aut_by	bigint(20)	 null default null 	  comment '	authorisation by (empid)	',
pro_ass_aut_date	datetime	 null default null 	  comment '	authorisation date	',
orgid	bigint(20)	not null	  comment '	orgnisation id	',
created_by	int(11)	not null	  comment '	user id who created the record	',
created_date	datetime	not null	  comment '	record creation date	',
updated_by	int(11)	 null default null 	  comment '	user id who update the data	',
updated_date	datetime	 null default null 	  comment '	date on which data is going to update	',
lg_ip_mac	varchar(100)	 null default null 	  comment '	client machine?s login name | ip address | physical address	',
lg_ip_mac_upd	varchar(100)	 null default null 	  comment '	client machine?s login name | ip address | physical address updated	',
apm_application_id	bigint(16)	 null default null 	  comment '	application id	',
pro_ass_email	varchar(25)	 null default null 	  comment '	property address : email id	',
sm_service_id	bigint(20)	 null default null 	  comment '	service id number	',
mn_indfy_reason	varchar(25)	 null default null 	  comment '	Reason to for creatation of property like Mutation - M, new - N, bifercation - B or amalgamation - A	' 			,
mn_child_prop	bigint(20)	 null default null 	  comment '	Property ID of child property in case of new property created due to Amalgamation	' 			,
mn_parent_prop	bigint(20)	 null default null 	  comment '	Property ID of parent property in case of new property created due to Mutation or Bifercation	' );

--liquibase formatted sql
--changeset nilima:V20171227130628__ALL_TB_AS_PRO_ASSESMENT_MAST3.sql
alter table TB_AS_PRO_ASSESMENT_MAST add constraint PK_PRO_ASS_ID primary key (PRO_ASS_ID);
--liquibase formatted sql
--changeset nilima:V20171227130628__ALL_TB_AS_PRO_ASSESMENT_MAST4.sql
alter table TB_AS_PRO_ASSESMENT_MAST add constraint FK_PRO_ASS_LOC_ID foreign key (LOC_ID) references TB_LOCATION_MAS (LOC_ID);
