create table TB_AS_PRO_BILL_MAS
( pro_bm_idno               BIGINT not null comment 'Primary Id',
  pro_ass_id                BIGINT not null comment 'Assesment Id  of tb_as_assessment_mast',
  pro_bm_year               SMALLINT not null comment 'Bill Year',
  pro_bm_billdt             DATETIME comment 'Bill date',
  pro_bm_fromdt             DATETIME not null comment 'Bill From Date',
  pro_bm_todt               DATETIME not null comment 'Bill To Date',
  pro_bm_duedate            DATETIME comment 'Bill Due Date',
  pro_bm_total_amount       DECIMAL(15,2) comment 'Bill Total Amount',
  pro_bm_total_bal_amount   DECIMAL(15,2) comment 'Bill Balance Amount',
  pro_bm_total_arrears      DECIMAL(15,2) comment 'Bill Total Arrears',
  pro_bm_total_outstanding  DECIMAL(15,2) comment 'Bill Total OutStanding Amount',
  pro_bm_total_arr_wout_int DECIMAL(15,2) comment 'Bill Toatl Arrerrs without Interest',
  pro_bm_total_cum_int_arr  DECIMAL(15,2) comment 'Bill Total Cumulative interest arrears',
  pro_bm_toatl_int          DECIMAL(15,2) comment 'Bill Total Interest Amount',
  pro_bm_last_rcptamt       DECIMAL(12,2) comment 'Last receipt amt. for the Property at the time of Bill genertaion',
  pro_bm_last_rcptdt        DATETIME comment 'Last receipt Date for the Property at the time of Bill Generation',
  pro_bm_toatl_rebate       DECIMAL(15,2) comment 'Total Rebate Amount',
  pro_bm_paid_flag          VARCHAR(1) comment 'Bill Paid Flag',
  pro_flag_jv_post          CHAR(1) default 'N' comment 'Deas - JV Posting Flag - N for not jv posted and Y for jv posted',
  pro_bm_dist_date          DATETIME comment 'Bill Distribution Date',
  pro_bm_remarks            NVARCHAR(100) comment 'Bill remarks',
  pro_bm_printdate          DATETIME comment 'Bill print date',
  pro_arrears_bill          CHAR(1) comment 'Arrear bill or normal bill',
  pro_bm_totpayamt_aftdue   DECIMAL(15,2) comment 'Total payable amount (with interest) after due date.',
  pro_bm_intamt_aftdue      DECIMAL(15,2) comment 'Interest amount to be charged after due date.',
  pro_bm_entry_flag         VARCHAR(1) ,
  orgid                     BIGINT not null comment 'orgnisation id',
  created_by                BIGINT not null comment 'user id who created the record',
  created_date              DATETIME not null comment 'record creation date', 
  updated_by                INT comment 'user id who update the data',
  updated_date              DATETIME comment 'date on which data is going to update',
  lg_ip_mac                 VARCHAR(100) comment 'client machine?s login name | ip address | physical address',
  lg_ip_mac_upd             VARCHAR(100) comment 'updated client machine?s login name | ip address | physical address',
  pro_bm_int_from           DATETIME comment 'Interest from date',
  pro_bm_int_to             DATETIME comment 'Interest to date',
  pro_bm_no                 VARCHAR(16) not null comment 'Bill Number ',
  pro_bm_int_value          DECIMAL(15,3) comment 'Interest value to be charge if bill is not paid within due date. Value can be amount or percentage.');

  alter table TB_AS_PRO_BILL_MAS
  add constraint PK_PRO_BM_IDNO primary key (PRO_BM_IDNO);

  

;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
