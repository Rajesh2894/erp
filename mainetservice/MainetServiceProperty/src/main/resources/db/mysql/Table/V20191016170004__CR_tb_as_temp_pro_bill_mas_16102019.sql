--liquibase formatted sql
--changeset Anil:V20191016170004__CR_tb_as_temp_pro_bill_mas_16102019.sql
drop table if exists tb_as_temp_pro_bill_mas;
--liquibase formatted sql
--changeset Anil:V20191016170004__CR_tb_as_temp_pro_bill_mas_161020191.sql
CREATE TABLE tb_as_temp_pro_bill_mas(
  Temp_pro_bm_idno bigint(12) NOT NULL COMMENT 'Primary Id',
  Temp_pro_ass_id bigint(12) DEFAULT NULL COMMENT 'Assesment Id  of tb_as_assessment_mast',
  Temp_pro_prop_no varchar(20) NOT NULL COMMENT 'Assesment No  of tb_as_assessment_mast',
  Temp_pro_bm_year bigint(20) NOT NULL COMMENT 'Bill Year',
  Temp_pro_bm_billdt datetime DEFAULT NULL COMMENT 'Bill date',
  Temp_pro_bm_fromdt datetime NOT NULL COMMENT 'Bill From Date',
  Temp_pro_bm_todt datetime NOT NULL COMMENT 'Bill To Date',
  Temp_pro_bm_duedate datetime DEFAULT NULL COMMENT 'Bill Due Date',
  Temp_pro_bm_total_amount decimal(15,2) DEFAULT NULL COMMENT 'Bill Total Amount',
  Temp_pro_bm_actual_arr_amount decimal(15,2) DEFAULT NULL COMMENT 'Bill ARREARS Amount',
  Temp_pro_gen_flag char(1) DEFAULT NULL COMMENT 'Bill genaration Flag',
  Temp_pro_bm_total_bal_amount decimal(15,2) DEFAULT NULL COMMENT 'Bill Balance Amount',
  Temp_pro_bm_total_arrears decimal(15,2) DEFAULT NULL COMMENT 'Bill Total Arrears',
  Temp_pro_bm_total_outstanding decimal(15,2) DEFAULT NULL COMMENT 'Bill Total OutStanding Amount',
  Temp_pro_bm_total_arr_wout_int decimal(15,2) DEFAULT NULL COMMENT 'Bill Total Arrears without Interest',
  Temp_pro_bm_total_cum_int_arr decimal(15,2) DEFAULT NULL COMMENT 'Bill Total Cumulative interest arrears',
  Temp_pro_bm_toatl_int decimal(15,2) DEFAULT NULL COMMENT 'Bill Total Interest Amount',
  Temp_pro_BM_TOTAL_PENALTY decimal(15,2) DEFAULT NULL COMMENT ' X ',
  Temp_pro_bm_last_rcptamt decimal(12,2) DEFAULT NULL COMMENT 'Last receipt amt. for the Property at the time of Bill generation',
  Temp_pro_bm_last_rcptdt datetime DEFAULT NULL COMMENT 'Last receipt Date for the Property at the time of Bill Generation',
  Temp_pro_bm_toatl_rebate decimal(15,2) DEFAULT NULL COMMENT 'Total Rebate Amount',
  Temp_pro_bm_paid_flag varchar(1) DEFAULT NULL COMMENT 'Bill Paid Flag',
  Temp_pro_flag_jv_post char(1) DEFAULT NULL COMMENT 'Deas - JV Posting Flag - N for not jv posted and Y for jv posted',
  Temp_pro_bm_dist_date datetime DEFAULT NULL COMMENT 'Bill Distribution Date',
  Temp_pro_bm_remarks varchar(100) DEFAULT NULL COMMENT 'Bill remarks',
  Temp_pro_bm_printdate datetime DEFAULT NULL COMMENT 'Bill print date',
  Temp_pro_arrears_bill char(1) DEFAULT NULL COMMENT 'Arrear bill or normal bill',
  Temp_pro_bm_totpayamt_aftdue decimal(15,2) DEFAULT NULL COMMENT 'Total payable amount (with interest) after due date.',
  Temp_pro_bm_intamt_aftdue decimal(15,2) DEFAULT NULL COMMENT 'Interest amount to be charged after due date.',
  Temp_pro_bm_entry_flag varchar(1) DEFAULT NULL COMMENT 'Entry flag',
  Temp_pro_GEN_DES char(1) DEFAULT NULL COMMENT 'IF BILL GENERTED BY DES(DATA ENTRY SUIT) THE FLAG WILL BE Y',
  Temp_pro_BILL_PRI_PATH varchar(200) DEFAULT NULL COMMENT 'BILL PRINTING PATH',
  Temp_pro_bm_int_from datetime DEFAULT NULL COMMENT 'Interest from date',
  Temp_pro_bm_int_to datetime DEFAULT NULL COMMENT 'Interest to date',
  Temp_pro_bm_no varchar(16) DEFAULT NULL COMMENT 'Bill Number ',
  Temp_pro_bm_int_value decimal(15,3) DEFAULT NULL COMMENT 'Interest value to be charge if bill is not paid within due date. Value can be amount or percentage.',
  Temp_pro_auth_bm_idno bigint(12) DEFAULT NULL COMMENT 'Auth bm_idno no',
  orgid bigint(12) NOT NULL COMMENT 'organization id',
  created_by bigint(12) NOT NULL COMMENT 'user id who created the record',
  created_date datetime NOT NULL COMMENT 'record creation date',
  updated_by bigint(12) DEFAULT NULL COMMENT 'user id who update the data',
  updated_date datetime DEFAULT NULL COMMENT 'date on which data is going to update',
  lg_ip_mac varchar(100) DEFAULT NULL COMMENT 'client machine?s login name | ip address | physical address',
  lg_ip_mac_upd varchar(100) DEFAULT NULL COMMENT 'updated client machine?s login name | ip address | physical address',
  PRIMARY KEY (Temp_pro_bm_idno),
  KEY INDX_Temp_pro_BILL_BM_NO (Temp_pro_bm_no),
  KEY INDX_Temp_pro_BILL_PROP_NO (Temp_pro_prop_no),
  KEY INDX_Temp_pro_BILL_ORGID (orgid),
  KEY INDX_Temp_pro_BILL_GEN_FLAG (Temp_pro_gen_flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
