insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Liability', 'LI', 'A',  (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'ABT'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Liability', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Deposits', 'DE', 'A',  (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'ABT'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Deposits', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Advance', 'AD', 'A',  (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'ABT'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Advance', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Loan', 'LN', 'A',  (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'ABT'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Loan', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Adjustment', 'AJ', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'ABT'), 1, 1, to_date('27-07-2017 21:00:24', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Adjustment', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Receipt', 'REV', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'RV'), 1, 1, to_date('27-07-2017 21:00:24', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Receipt', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Payment Voucher Deletion', 'PVD', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'RV'), 1, 1, to_date('27-07-2017 21:00:24', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Payment Voucher Deletion', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Accounting Entry  - Direct posting', 'AED', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'AEP'), 1, 1, to_date('27-07-2017 21:00:15', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Accounting Entry  - Direct posting', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Accounting Entry - Using voucher entry posting form', 'AEV', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'AEP'), 1, 1, to_date('27-07-2017 21:00:16', 'dd-mm-yyyy hh24:mi:ss'), 'Y', null, null, 'Accounting Entry - Using voucher entry posting form', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Receipt', 'RP', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TOS'), 1, 1, to_date('27-07-2017 21:00:16', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Receipt', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Bill/Payment Dedcuation', 'BP', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TOS'), 1, 1, to_date('27-07-2017 21:00:16', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Bill/Payment Dedcuation', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Deposit Slip Entry', 'DSE', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TOS'), 3, 1, to_date('07-09-2017 12:09:10', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Deposit Slip Entry', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Bill Payment Entry', 'BPE', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TOS'), 3, 1, to_date('07-09-2017 12:09:10', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Bill Payment Entry', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Direct Payment Entry', 'DPE', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TOS'), 3, 1, to_date('07-09-2017 12:09:10', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Direct Payment Entry', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Deposit Refunded', 'DR', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'RDC'), 1, 1, to_date('27-07-2017 21:00:16', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Deposit Refunded', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Receipt Deleted', 'RD', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'RDC'), 1, 1, to_date('27-07-2017 21:00:16', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Receipt Deleted', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Cheque Dishonoured', 'CD', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'RDC'), 1, 1, to_date('27-07-2017 21:00:16', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Cheque Dishonoured', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Full Forfeited', 'FF', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'RDC'), 1, 1, to_date('27-07-2017 21:00:16', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Full Forfeited', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Deposit Open', 'DO', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'RDC'), 1, 1, to_date('27-07-2017 21:00:16', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Deposit Open', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Revenue Receipts', 'I', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'COA'), 1, 1, to_date('27-07-2017 21:00:16', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Revenue Receipts', '1', null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Revenue Expenditures', 'E', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'COA'), 1, 1, to_date('27-07-2017 21:00:16', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Revenue Expenditures', '2', null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Liability', 'L', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'COA'), 1, 1, to_date('27-07-2017 21:00:16', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Liability', '3', null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Asset', 'A', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'COA'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Asset', '4', null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'One Fund one Bank A/c', 'OO', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'BFV'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'Y', null, null, 'One Fund one Bank A/c', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'One Fund Many Bank A/c', 'OM', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'BFV'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'One Fund Many Bank A/c', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Many Fund one Bank A/c', 'MO', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'BFV'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Many Fund one Bank A/c', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Allow Cross Fund Cash Deposit', 'ACF', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'CFD'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Allow Cross Fund Cash Deposit', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Default deposit A/c for multi fund collection', 'DDC', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'CFD'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Default deposit A/c for multi fund collection', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Reconciliation on realization period', 'RRP', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'CFD'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Reconciliation on realization period', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Reconciliation on realization status', 'RRS', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'CFD'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Reconciliation on realization status', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Department Wise Deposit', 'DWR', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'CFD'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Department Wise Deposit', 'Y', null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Field Wise Remittance', 'FLR', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'CFD'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Field Wise Remittance', 'Y', null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Fund Wise', 'FN', 'I', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'OBC'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Fund Wise', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Field Wise', 'FL', 'I', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'OBC'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Field Wise', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Opening Balances', 'OB', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DUL'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Opening Balances', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Budget Entry', 'BE', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DUL'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Budget Entry', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Account Head', 'AH', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DUL'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Account Head', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Function Code', 'FC', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DUL'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Function Code', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Deposits Payables', 'DP', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DUL'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Deposits Payables', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Vendors Payables', 'VP', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DUL'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Vendors Payables', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Loans Payables', 'LP', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DUL'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Loans Payables', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Advance Receivables', 'AR', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DUL'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Advance Receivables', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Investment', 'IE', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DUL'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Investment', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Receipts', 'RE', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DUL'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Receipts', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Bills', 'BE', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DUL'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Bills', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Payments', 'PE', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DUL'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Payments', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Banks', 'BK', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'SAM'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Banks', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Vendors', 'VN', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'SAM'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Vendors', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Deposits', 'DP', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'SAM'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Deposits', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Advances', 'AD', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'SAM'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Advances', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Loans', 'LO', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'SAM'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Loans', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Investments', 'IN', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'SAM'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Investments', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Statutory Deductions', 'SD', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'SAM'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Statutory Deductions', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Assets', 'AS', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'SAM'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Assets', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Control Accounts for Mode', 'PAY', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'SAM'), 1, 1, to_date('27-07-2017 21:00:17', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Control Accounts for Mode', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Functionary Code', 'FRC', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TSH'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Functionary Code', 'Y', null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Fund Code', 'FDC', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TSH'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Fund Code', 'N', null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Function Code', 'FNC', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TSH'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Function Code', 'N', null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Field Code', 'FLC', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TSH'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Field Code', 'N', null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Secondary Head Code', 'SHC', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TSH'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Secondary Head Code', 'N', null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Deposit Slip Reversal', 'DSR', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TDP'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Deposit Slip Reversal', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Receipt Reversal Entry', 'RRE', 'A',  (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TDP'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Receipt Reversal Entry', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Dishonour Entry', 'DHE', 'A',  (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TDP'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Dishonour Entry', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Receipts ', 'RV', 'A',  (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TDP'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Receipts ', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Deposit Slip (Bank Remittance)', 'DS', 'A',  (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TDP'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Deposit Slip (Bank Remittance)', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Vendor Bill / Invoice Posting', 'BI', 'A',  (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TDP'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Vendor Bill / Invoice Posting', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Bank Transfer', 'BTE', 'A',  (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TDP'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Bank Transfer', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Cash Withdrawal', 'CWE', 'A',  (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TDP'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Cash Withdrawal', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Demand Posting (Receivables)', 'DMD', 'A',  (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TDP'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Demand Posting (Receivables)', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Bill Payments Posting - Vendor Bills', 'PVE', 'A',  (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TDP'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Bill Payments Posting - Vendor Bills', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Collection Against Demand - Control Account', 'WCC', 'A',  (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TDP'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Collection Against Demand - Control Account', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Demand Collection Posting - (Break-up)', 'WCB', 'A',  (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TDP'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Demand Collection Posting - (Break-up)', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Refunds / Remissions Payable', 'RRP', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TDP'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Refunds / Remissions Payable', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Provision For Doubtful Receivables', 'PDR', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TDP'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Provision For Doubtful Receivables', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Reversal of Vendor Bill / Invoice', 'RBI', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TDP'), 3, 1, to_date('04-09-2017 18:08:11', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Reversal of Vendor Bill / Invoice', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Reversal of Bill Payments', 'RBP', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TDP'), 3, 1, to_date('04-09-2017 18:08:11', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Reversal of Bill Payments', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Reversal of Direct Payments', 'RDP', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TDP'), 3, 1, to_date('04-09-2017 18:08:11', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Reversal of Direct Payments', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Functionary', 'FNC', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'BDP'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Functionary', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Field', 'FLD', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'BDP'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Field', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Secondary Head', 'SHD', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'BDP'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Secondary Head', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Fund', 'FND', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'BDP'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Fund', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, ' Fund Wise Budget', 'FWB', 'I', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'BHC'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, ' Fund Wise Budget', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Field Wise Budget', 'FLB', 'I', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'BHC'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Field Wise Budget', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Functionary Wise Budget', 'FNW', 'I', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'BHC'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Functionary Wise Budget', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Fund', 'AF', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'CMD'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Fund', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Function', 'AFN', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'CMD'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, '¿¿¿¿¿', 'Y', null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Field', 'AFD', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'CMD'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Field', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Primary Account Head', 'AHP', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'CMD'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Primary Account Head', 'Y', null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Secondary Account Head', 'AHS', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'CMD'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Secondary Account Head', 'Y', null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Vendor Account', 'VD', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'FTY'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Vendor Account', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Bank Account', 'BK', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'FTY'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Bank Account', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Others Account', 'OT', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'FTY'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Others Account', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Control Account', 'CA', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'FTY'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Control Account', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'In Use  ', 'U', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'BAS'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'Y', null, null, 'In Use  ', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Not in Used', 'N', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'BAS'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Not in Used', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Closed  ', 'C', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'BAS'), 1, 1, to_date('27-07-2017 21:00:18', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Closed  ', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Current', 'CUR', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'ACT'), 1, 1, to_date('27-07-2017 21:00:19', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Current', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Saving', 'SAV', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'ACT'), 1, 1, to_date('27-07-2017 21:00:19', 'dd-mm-yyyy hh24:mi:ss'), 'Y', null, null, 'Saving', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Fixed', 'FIX', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'ACT'), 1, 1, to_date('27-07-2017 21:00:19', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Fixed', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Recurring', 'REC', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'ACT'), 1, 1, to_date('27-07-2017 21:00:19', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Recurring', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Revenue', 'RV', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'FTP'), 1, 1, to_date('27-07-2017 21:00:19', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Revenue', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Capital', 'CP', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'FTP'), 1, 1, to_date('27-07-2017 21:00:19', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Capital', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Income tax', 'I', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TDS'), 1, 1, to_date('27-07-2017 21:00:19', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Income tax', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Work Contract Tax', 'S', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TDS'), 1, 1, to_date('27-07-2017 21:00:19', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Work Contract Tax', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Insurance', 'I', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TDS'), 1, 1, to_date('27-07-2017 21:00:20', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Insurance', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Retention Money', 'R', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TDS'), 1, 1, to_date('27-07-2017 21:00:20', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, ' Retention Money', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Royalty', 'RL', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'TDS'), 1, 1, to_date('27-07-2017 21:00:20', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Royalty', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'DR', 'DR', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DCR'), 1, 1, to_date('27-07-2017 21:00:20', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'DR', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'CR', 'CR', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DCR'), 1, 1, to_date('27-07-2017 21:00:20', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'CR', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Deposit Head Mapping', 'DHM', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'HDM'), 1, 1, to_date('27-07-2017 21:00:20', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Deposit Head Mapping', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Advance Head Mapping', 'AHM', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'HDM'), 1, 1, to_date('27-07-2017 21:00:20', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Advance Head Mapping', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Vehicle Purchase Advance', 'V', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'ATY'), 1, 1, to_date('27-07-2017 21:00:21', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Vehicle Purchase Advance', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Festival Advance', 'F', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'ATY'), 1, 1, to_date('27-07-2017 21:00:21', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Festival Advance', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Standing Advance', 'S', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'ATY'), 1, 1, to_date('27-07-2017 21:00:21', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Standing Advance', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Family Walfare Advance', 'W', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'ATY'), 1, 1, to_date('27-07-2017 21:00:21', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Family Walfare Advance', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Advance given to Employee', 'E', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'ATY'), 1, 1, to_date('27-07-2017 21:00:21', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Advance given to Employee', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Contractors Advance', 'C', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'ATY'), 1, 1, to_date('27-07-2017 21:00:21', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Contractors Advance', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Other Advance', 'O', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'ATY'), 1, 1, to_date('27-07-2017 21:00:22', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Other Advance', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Property Bill', 'PB', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'JV'), 1, 1, to_date('27-07-2017 21:00:22', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Property Bill', '4', null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Water Bill', 'WB', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'JV'), 1, 1, to_date('27-07-2017 21:00:22', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Water Bill', '4', null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Water Bill - Control', 'WBC', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'JV'), 1, 1, to_date('27-07-2017 21:00:22', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Water Bill - Control', '4', null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Property Bill (Arrears) 2015-2016', 'PBLT', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'JV'), 1, 1, to_date('27-07-2017 21:00:22', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Property Bill (Arrears) 2015-2016', '4', null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Reserve and Surplus', 'SPD', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'JV'), 1, 1, to_date('27-07-2017 21:00:22', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Reserve and Surplus', '3', null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Water Negative adjustment', 'WNA', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'JV'), 1, 1, to_date('27-07-2017 21:00:22', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Water Negative adjustment', '2', null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Assessment Prior period Expenses', 'APP', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'JV'), 1, 1, to_date('27-07-2017 21:00:22', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Assessment Prior period Expenses', '2', null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Contra entry - Cash', 'COC', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'CV'), 1, 1, to_date('27-07-2017 21:00:22', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Contra entry - Cash', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Contra entry - Bank ', 'CON', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'CV'), 1, 1, to_date('27-07-2017 21:00:22', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Contra entry - Bank ', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Permanent', 'PN', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'MTP'), 1, 1, to_date('27-07-2017 21:00:22', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Permanent', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Financial year wise', 'FYW', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'MTP'), 1, 1, to_date('27-07-2017 21:00:22', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Financial year wise', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Receipt', 'REV', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'REX'), 1, 1, to_date('27-07-2017 21:00:22', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Receipt', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Expenditure', 'EXP', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'REX'), 1, 1, to_date('27-07-2017 21:00:22', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Expenditure', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Both - Receipt and Expenditure', 'BTH', 'I',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'REX'), 1, 1, to_date('27-07-2017 21:00:22', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Both - Receipt and Expenditure', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Re-appropriation', 'TDP', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'PRV'), 1, 1, to_date('27-07-2017 21:00:22', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Re-appropriation', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Supplementary', 'ADP', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'PRV'), 1, 1, to_date('27-07-2017 21:00:22', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Supplementary', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'VIDEO', 'VD', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'EST'), 1, 1, to_date('27-07-2017 21:00:22', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'VIDEO', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Photo Gallery', 'PHOTO', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'EST'), 1, 1, to_date('27-07-2017 21:00:22', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Photo Gallery', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Section Grid', 'SEC', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'EST'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Section Grid', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Table Grid', 'TABLE', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'EST'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Table Grid', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'MAP', 'MAP', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'EST'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'MAP', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Label', 'LBL', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'EST'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Label', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Portrait', 'PORTRAIT', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'EST'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Portrait', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Journal Voucher', 'JV', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'VOT'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Journal Voucher', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Receipt Voucher', 'RV', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'VOT'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Receipt Voucher', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Contra Voucher', 'CV', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'VOT'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Contra Voucher', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Payment Voucher', 'PV', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'VOT'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Payment Voucher', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'One Thousand', '1000', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DEN'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'One Thousand', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Five Hundred  ', '500', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DEN'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Five Hundred  ', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Hundred', '100', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DEN'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Hundred', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Fifty', '50', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DEN'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Fifty', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Twenty', '20', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DEN'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Twenty', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Ten', '10', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DEN'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Ten', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Five', '5', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DEN'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Five', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Two', '2', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DEN'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Two', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'One', '1', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DEN'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'One', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, '50 Paise', '0.50', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'DEN'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, '50 Paise', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Miscellaneous', 'M', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'LTY'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Miscellaneous', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Works', 'W', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'LTY'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Works', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Supply', 'S', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'LTY'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Supply', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'DOUBLE ENTRY', 'D', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'SDA'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'Y', null, null, 'DOUBLE ENTRY', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'SINGLE ENTRY', 'S', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'SDA'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'SINGLE ENTRY', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Individual', 'I', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'PTY'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Individual', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (421, 1, 'Group', 'G', 'A',(select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'PTY'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'Y', null, null, 'Group', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Miscellaneous', 'MI', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'ABT'), 1, 1, to_date('27-07-2017 21:00:23', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Miscellaneous', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Primary Head', 'PAH', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'BHC'), 3, 1, to_date('09-08-2017 19:41:43', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Primary Head', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Object Head ( Primary and Secondary )', 'OHC', 'I', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'BHC'), 3, 1, to_date('09-08-2017 19:41:43', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Object Head ( Primary and Secondary )', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Function  ', 'FNR', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'BHC'), 3, 1, to_date('09-08-2017 19:41:43', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Function  ', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, ' Internal System', 'INS', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'VET'), 3, 1, to_date('04-09-2017 16:57:54', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, ' Internal System', null, null, null);

insert into TB_COMPARAM_DET (CPD_ID, ORGID, CPD_DESC, CPD_VALUE, CPD_STATUS, CPM_ID, USER_ID, LANG_ID, LMODDATE, CPD_DEFAULT, UPDATED_BY, UPDATED_DATE, CPD_DESC_MAR, CPD_OTHERS, LG_IP_MAC, LG_IP_MAC_UPD)
values (fn_java_sq_generation('AC','CPD_ID','TB_COMPARAM_DET',NULL,NULL), 1, 'Manual Entry', 'MAN', 'A', (select m.cpm_id from tb_comparam_mas m where m.cpm_prefix = 'VET'), 3, 1, to_date('04-09-2017 16:57:54', 'dd-mm-yyyy hh24:mi:ss'), 'N', null, null, 'Manual Entry', null, null, null);

commit;
