create table TB_AS_BILL_MAS
(
  bm_idno                      NUMBER(12) not null,
  ass_id                       NUMBER(12) not null,
  bm_year                      NUMBER(4) not null,
  bm_billdt                    DATE,
  bm_fromdt                    DATE not null,
  bm_todt                      DATE not null,
  bm_duedate                   DATE,
  bm_total_amount              NUMBER(15,2),
  bm_total_bal_amount          NUMBER(15,2),
  bm_total_arrears             NUMBER(15,2),
  bm_total_outstanding         NUMBER(15,2),
  bm_total_arrears_without_int NUMBER(15,2),
  bm_total_cum_int_arrears     NUMBER(15,2),
  bm_toatl_int                 NUMBER(15,2),
  bm_last_rcptamt              NUMBER(12,2),
  bm_last_rcptdt               DATE,
  bm_toatl_rebate              NUMBER(15,2),
  bm_paid_flag                 VARCHAR2(1),
  flag_jv_post                 CHAR(1) default 'N',
  bm_dist_date                 DATE,
  bm_remarks                   NVARCHAR2(100),
  bm_printdate                 DATE,
  arrears_bill                 CHAR(1),
  bm_totpayamt_aftdue          NUMBER(15,2),
  bm_intamt_aftdue             NUMBER(15,2),
  bm_entry_flag                VARCHAR2(1),
  orgid                        NUMBER(12) not null,
  created_by                   NUMBER(12) not null,
  created_date                 DATE not null,
  updated_by                   NUMBER(7),
  updated_date                 DATE,
  lg_ip_mac                    VARCHAR2(100),
  lg_ip_mac_upd                VARCHAR2(100),
  bm_int_from                  DATE,
  bm_int_to                    DATE,
  bm_no                        VARCHAR2(16) not null,
  bm_int_value                 NUMBER(15,3)
);
comment on column TB_AS_BILL_MAS.bm_idno
  is 'Primary Id';
comment on column TB_AS_BILL_MAS.ass_id
  is 'Assesment Id  of tb_as_assessment_mast';
comment on column TB_AS_BILL_MAS.bm_year
  is 'Bill Year';
comment on column TB_AS_BILL_MAS.bm_billdt
  is 'Bill date';
comment on column TB_AS_BILL_MAS.bm_fromdt
  is 'Bill From Date';
comment on column TB_AS_BILL_MAS.bm_todt
  is 'Bill To Date';
comment on column TB_AS_BILL_MAS.bm_duedate
  is 'Bill Due Date';
comment on column TB_AS_BILL_MAS.bm_total_amount
  is 'Bill Total Amount';
comment on column TB_AS_BILL_MAS.bm_total_bal_amount
  is 'Bill Balance Amount';
comment on column TB_AS_BILL_MAS.bm_total_arrears
  is 'Bill Total Arrears';
comment on column TB_AS_BILL_MAS.bm_total_outstanding
  is 'Bill Total OutStanding Amount';
comment on column TB_AS_BILL_MAS.bm_total_arrears_without_int
  is 'Bill Toatl Arrerrs without Interest';
comment on column TB_AS_BILL_MAS.bm_total_cum_int_arrears
  is 'Bill Total Cumulative interest arrears';
comment on column TB_AS_BILL_MAS.bm_toatl_int
  is 'Bill Total Interest Amount';
comment on column TB_AS_BILL_MAS.bm_last_rcptamt
  is 'Last receipt amt. for the Property at the time of Bill genertaion';
comment on column TB_AS_BILL_MAS.bm_last_rcptdt
  is 'Last receipt Date for the Property at the time of Bill Generation';
comment on column TB_AS_BILL_MAS.bm_toatl_rebate
  is 'Total Rebate Amount';
comment on column TB_AS_BILL_MAS.bm_paid_flag
  is 'Bill Paid Flag';
comment on column TB_AS_BILL_MAS.flag_jv_post
  is 'Deas - JV Posting Flag - N for not jv posted and Y for jv posted';
comment on column TB_AS_BILL_MAS.bm_dist_date
  is 'Bill Distribution Date';
comment on column TB_AS_BILL_MAS.bm_remarks
  is 'Bill remarks';
comment on column TB_AS_BILL_MAS.bm_printdate
  is 'Bill print date';
comment on column TB_AS_BILL_MAS.arrears_bill
  is 'Arrear bill or normal bill';
comment on column TB_AS_BILL_MAS.bm_totpayamt_aftdue
  is 'Total payable amount (with interest) after due date.';
comment on column TB_AS_BILL_MAS.bm_intamt_aftdue
  is 'Interest amount to be charged after due date.';
comment on column TB_AS_BILL_MAS.orgid
  is 'orgnisation id';
comment on column TB_AS_BILL_MAS.created_by
  is 'user id who created the record';
comment on column TB_AS_BILL_MAS.created_date
  is 'record creation date';
comment on column TB_AS_BILL_MAS.updated_by
  is 'user id who update the data';
comment on column TB_AS_BILL_MAS.updated_date
  is 'date on which data is going to update';
comment on column TB_AS_BILL_MAS.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_AS_BILL_MAS.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
comment on column TB_AS_BILL_MAS.bm_int_from
  is 'Interest from date';
comment on column TB_AS_BILL_MAS.bm_int_to
  is 'Interest to date';
comment on column TB_AS_BILL_MAS.bm_no
  is 'Bill Number ';
comment on column TB_AS_BILL_MAS.bm_int_value
  is 'Interest value to be charge if bill is not paid within due date. Value can be amount or percentage.';
alter table TB_AS_BILL_MAS
  add constraint PK_BM_IDNO primary key (BM_IDNO);
