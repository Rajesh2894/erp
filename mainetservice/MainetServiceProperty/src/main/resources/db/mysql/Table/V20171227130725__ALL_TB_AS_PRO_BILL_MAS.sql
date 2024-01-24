--liquibase formatted sql
--changeset nilima:V20171227130725__ALL_TB_AS_PRO_BILL_MAS1.sql
DROP TABLE IF EXISTS TB_AS_PRO_BILL_MAS;
--liquibase formatted sql
--changeset nilima:V20171227130725__ALL_TB_AS_PRO_BILL_MAS2.sql
create table TB_AS_PRO_BILL_MAS (
pro_bm_idno	bigint(20)	 NOT NULL	  COMMENT '	Primary Id	',
pro_ass_id	bigint(20)	 NOT NULL	  COMMENT '	Assesment Id  of tb_as_assessment_mast	',
pro_prop_no	varchar(20)	 NOT NULL	  COMMENT '	Assesment No  of tb_as_assessment_mast	',
pro_bm_year	smallint(6)	 NOT NULL	  COMMENT '	Bill Year	',
pro_bm_billdt	datetime	 NULL DEFAULT NULL 	  COMMENT '	Bill date	',
pro_bm_fromdt	datetime	 NOT NULL	  COMMENT '	Bill From Date	',
pro_bm_todt	datetime	 NOT NULL	  COMMENT '	Bill To Date	',
pro_bm_duedate	datetime	 NULL DEFAULT NULL 	  COMMENT '	Bill Due Date	',
pro_bm_total_amount	decimal(15,2)	 NULL DEFAULT NULL 	  COMMENT '	Bill Total Amount	',
pro_bm_total_bal_amount	decimal(15,2)	 NULL DEFAULT NULL 	  COMMENT '	Bill Balance Amount	',
pro_bm_total_arrears	decimal(15,2)	 NULL DEFAULT NULL 	  COMMENT '	Bill Total Arrears	',
pro_bm_total_outstanding	decimal(15,2)	 NULL DEFAULT NULL 	  COMMENT '	Bill Total OutStanding Amount	',
pro_bm_total_arr_wout_int	decimal(15,2)	 NULL DEFAULT NULL 	  COMMENT '	Bill Total Arrears without Interest	',
pro_bm_total_cum_int_arr	decimal(15,2)	 NULL DEFAULT NULL 	  COMMENT '	Bill Total Cumulative interest arrears	',
pro_bm_toatl_int	decimal(15,2)	 NULL DEFAULT NULL 	  COMMENT '	Bill Total Interest Amount	',
pro_bm_last_rcptamt	decimal(12,2)	 NULL DEFAULT NULL 	  COMMENT '	Last receipt amt. for the Property at the time of Bill generation	',
pro_bm_last_rcptdt	datetime	 NULL DEFAULT NULL 	  COMMENT '	Last receipt Date for the Property at the time of Bill Generation	',
pro_bm_toatl_rebate	decimal(15,2)	 NULL DEFAULT NULL 	  COMMENT '	Total Rebate Amount	',
pro_bm_paid_flag	varchar(1)	 NULL DEFAULT NULL 	  COMMENT '	Bill Paid Flag	',
pro_flag_jv_post	char(1)	 NULL DEFAULT NULL 	  COMMENT '	Deas - JV Posting Flag - N for not jv posted and Y for jv posted	',
pro_bm_dist_date	datetime	 NULL DEFAULT NULL 	  COMMENT '	Bill Distribution Date	',
pro_bm_remarks	varchar(100)	 NULL DEFAULT NULL 	  COMMENT '	Bill remarks	',
pro_bm_printdate	datetime	 NULL DEFAULT NULL 	  COMMENT '	Bill print date	',
pro_arrears_bill	char(1)	 NULL DEFAULT NULL 	  COMMENT '	Arrear bill or normal bill	',
pro_bm_totpayamt_aftdue	decimal(15,2)	 NULL DEFAULT NULL 	  COMMENT '	Total payable amount (with interest) after due date.	',
pro_bm_intamt_aftdue	decimal(15,2)	 NULL DEFAULT NULL 	  COMMENT '	Interest amount to be charged after due date.	',
pro_bm_entry_flag	varchar(1)	 NULL DEFAULT NULL 	  COMMENT '		',
orgid	bigint(20)	 NOT NULL	  COMMENT '	organization id	',
created_by	bigint(20)	 NOT NULL	  COMMENT '	user id who created the record	',
created_date	datetime	 NOT NULL	  COMMENT '	record creation date	',
updated_by	int(12)	 NULL DEFAULT NULL 	  COMMENT '	user id who update the data	',
updated_date	datetime	 NULL DEFAULT NULL 	  COMMENT '	date on which data is going to update	',
lg_ip_mac	varchar(100)	 NULL DEFAULT NULL 	  COMMENT '	client machine?s login name | ip address | physical address	',
lg_ip_mac_upd	varchar(100)	 NULL DEFAULT NULL 	  COMMENT '	updated client machine?s login name | ip address | physical address	',
pro_bm_int_from	datetime	 NULL DEFAULT NULL 	  COMMENT '	Interest from date	',
pro_bm_int_to	datetime	 NULL DEFAULT NULL 	  COMMENT '	Interest to date	',
pro_bm_no	varchar(16)	 NULL DEFAULT NULL 	  COMMENT '	Bill Number 	',
pro_bm_int_value	decimal(15,3)	 NULL DEFAULT NULL 	  COMMENT '	Interest value to be charge if bill is not paid within due date. Value can be amount or percentage.	'
);

--liquibase formatted sql
--changeset nilima:V20171227130725__ALL_TB_AS_PRO_BILL_MAS3.sql
alter table TB_AS_PRO_BILL_MAS add constraint PK_pro_bm_idno primary key (pro_bm_idno);