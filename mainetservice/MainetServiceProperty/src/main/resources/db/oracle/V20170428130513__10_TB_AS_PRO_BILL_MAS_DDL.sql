create table TB_AS_PRO_BILL_MAS
( pro_bm_idno               NUMBER(12) not null,
  pro_ass_id                NUMBER(12) not null,
  pro_bm_year               NUMBER(4) not null,
  pro_bm_billdt             DATE,
  pro_bm_fromdt             DATE not null,
  pro_bm_todt               DATE not null,
  pro_bm_duedate            DATE,
  pro_bm_total_amount       NUMBER(15,2),
  pro_bm_total_bal_amount   NUMBER(15,2),
  pro_bm_total_arrears      NUMBER(15,2),
  pro_bm_total_outstanding  NUMBER(15,2),
  pro_bm_total_arr_wout_int NUMBER(15,2),
  pro_bm_total_cum_int_arr  NUMBER(15,2),
  pro_bm_toatl_int          NUMBER(15,2),
  pro_bm_last_rcptamt       NUMBER(12,2),
  pro_bm_last_rcptdt        DATE,
  pro_bm_toatl_rebate       NUMBER(15,2),
  pro_bm_paid_flag          VARCHAR2(1),
  pro_flag_jv_post          CHAR(1) default 'N',
  pro_bm_dist_date          DATE,
  pro_bm_remarks            NVARCHAR2(100),
  pro_bm_printdate          DATE,
  pro_arrears_bill          CHAR(1),
  pro_bm_totpayamt_aftdue   NUMBER(15,2),
  pro_bm_intamt_aftdue      NUMBER(15,2),
  pro_bm_entry_flag         VARCHAR2(1),
  orgid                     NUMBER(12) not null,
  created_by                NUMBER(12) not null,
  created_date              DATE not null,
  updated_by                NUMBER(7),
  updated_date              DATE,
  lg_ip_mac                 VARCHAR2(100),
  lg_ip_mac_upd             VARCHAR2(100),
  pro_bm_int_from           DATE,
  pro_bm_int_to             DATE,
  pro_bm_no                 VARCHAR2(16) not null,
  pro_bm_int_value          NUMBER(15,3)
);
comment on column TB_AS_PRO_BILL_MAS.pro_bm_idno
  is 'Primary Id';
comment on column TB_AS_PRO_BILL_MAS.pro_ass_id
  is 'Assesment Id  of tb_as_assessment_mast';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_year
  is 'Bill Year';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_billdt
  is 'Bill date';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_fromdt
  is 'Bill From Date';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_todt
  is 'Bill To Date';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_duedate
  is 'Bill Due Date';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_total_amount
  is 'Bill Total Amount';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_total_bal_amount
  is 'Bill Balance Amount';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_total_arrears
  is 'Bill Total Arrears';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_total_outstanding
  is 'Bill Total OutStanding Amount';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_total_arr_wout_int
  is 'Bill Toatl Arrerrs without Interest';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_total_cum_int_arr
  is 'Bill Total Cumulative interest arrears';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_toatl_int
  is 'Bill Total Interest Amount';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_last_rcptamt
  is 'Last receipt amt. for the Property at the time of Bill genertaion';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_last_rcptdt
  is 'Last receipt Date for the Property at the time of Bill Generation';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_toatl_rebate
  is 'Total Rebate Amount';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_paid_flag
  is 'Bill Paid Flag';
comment on column TB_AS_PRO_BILL_MAS.pro_flag_jv_post
  is 'Deas - JV Posting Flag - N for not jv posted and Y for jv posted';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_dist_date
  is 'Bill Distribution Date';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_remarks
  is 'Bill remarks';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_printdate
  is 'Bill print date';
comment on column TB_AS_PRO_BILL_MAS.pro_arrears_bill
  is 'Arrear bill or normal bill';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_totpayamt_aftdue
  is 'Total payable amount (with interest) after due date.';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_intamt_aftdue
  is 'Interest amount to be charged after due date.';
comment on column TB_AS_PRO_BILL_MAS.orgid
  is 'orgnisation id';
comment on column TB_AS_PRO_BILL_MAS.created_by
  is 'user id who created the record';
comment on column TB_AS_PRO_BILL_MAS.created_date
  is 'record creation date';
comment on column TB_AS_PRO_BILL_MAS.updated_by
  is 'user id who update the data';
comment on column TB_AS_PRO_BILL_MAS.updated_date
  is 'date on which data is going to update';
comment on column TB_AS_PRO_BILL_MAS.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_AS_PRO_BILL_MAS.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_int_from
  is 'Interest from date';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_int_to
  is 'Interest to date';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_no
  is 'Bill Number ';
comment on column TB_AS_PRO_BILL_MAS.pro_bm_int_value
  is 'Interest value to be charge if bill is not paid within due date. Value can be amount or percentage.';
alter table TB_AS_PRO_BILL_MAS
  add constraint PK_PRO_BM_IDNO primary key (PRO_BM_IDNO)
  using index;
