delete from TB_AC_ADVANCE;
delete from TB_AC_BANK_DEPOSITSLIP_DENOM;
delete from TB_AC_BANK_DEPOSITSLIP_MASTER;
delete from TB_AC_BILL_DEDUCTION_DETAIL;
delete from tb_ac_bill_deduction_det_hist;
delete from TB_AC_BILL_EXP_DETAIL;
delete from tb_ac_bill_exp_detail_hist;
delete from tb_ac_bill_mas_hist;
delete from TB_AC_BUDGETALLOCATION;
delete from TB_AC_BUDGETALLOCATION_HIST;
delete from TB_AC_BUDGETORY_ESTIMATE;
delete from TB_AC_BUDGETORY_REVISED;
delete from TB_AC_BUGOPEN_BALANCE;
delete from tb_ac_bugopen_balance_hist;
delete from TB_AC_CHEQUEBOOKLEAF_DET;
delete from TB_AC_CHEQUEBOOKLEAF_DET_HIST;
delete from TB_AC_CHEQUEBOOKLEAF_MAS;
delete from TB_AC_DEPOSITS;
delete from TB_AC_LIABILITY_BOOKING;
delete from TB_AC_LIABILITY_BOOKING_DET;
delete from TB_AC_PAYMENT_DET;
delete from TB_AC_PAYMENT_MAS;
delete from TB_AC_PAY_TO_BANK;
delete from TB_AC_PROJECTEDPROVISIONADJ;
delete from TB_AC_PROJECTEDPROVISIONADJ_TR;
delete from TB_AC_PROJECTEDREVENUE;
delete from tb_ac_projectedrevenue_hist;
delete from TB_AC_PROJECTED_EXPENDITURE;
delete from tb_ac_projected_expendi_hist
delete from TB_AC_VOUCHERTEMPLATE_DET;
delete from TB_AC_VOUCHERTEMPLATE_MAS;
delete from TB_AC_VOUCHER_DET;
delete from tb_ac_voucher_det_hist;
delete from TB_AC_VOUCHER;
delete from tb_ac_voucher_hist;
delete from TB_AC_VOUCHER_POST_DETAIL;
delete from TB_AC_VOUCHER_POST_MASTER;
delete from TB_TAX_BUDGET_CODE;
delete from TB_AC_TDS_TAXHEADS;
delete from TB_AC_TDS_TAXHEADS_HIST;
delete from TB_AC_TENDER_DET;
delete from TB_AC_TENDER_MASTER;
delete from TB_BANK_ACCOUNT;
delete from TB_AC_BUDGETCODE_MAS;
delete from tb_tax_ac_mapping;
delete from tb_tax_ac_mapping_hist;
delete from tb_ac_bank_tds_details;
delete from TB_AC_SECONDARYHEAD_MASTER;
delete from tb_ac_secondaryhead_mas_hist;
/*delete from TB_AC_PRIMARYHEAD_MASTER;*/
/*delete from TB_AC_PRIMARYHEAD_MASTER_HIST;*/
/*delete from TB_AC_FUNCTION_MASTER;*/
/*delete from TB_AC_FUND_MASTER;*/
/*delete from TB_AC_FUND_MASTER_HIST;*/
delete from TB_LOCATION_REVENUE_WARDZONE;
/*delete from TB_AC_FIELD_MASTER;*/
/*delete from TB_AC_FIELD_MASTER_HIST;*/
/*delete from TB_AC_CODINGSTRUCTURE_DET;
delete from TB_AC_CODINGSTRUCTURE_DET_HIST;*/
/*delete from TB_AC_CODINGSTRUCTURE_MAS;*/
/*delete from TB_AC_CODINGSTRUCTURE_MAS_HIST;*/
delete from TB_ULB_BANK;
delete from TB_AC_BILL_MAS;
commit;

delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_ADVANCE');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BANK_DEPOSITSLIP_DENOM');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BANK_DEPOSITSLIP_MASTER');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BILL_DEDUCTION_DETAIL');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BILL_EXP_DETAIL');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BILL_MAS');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUDGETALLOCATION');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUDGETALLOCATION_HIST');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUDGETCODE_MAS');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUDGETORY_ESTIMATE');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUDGETORY_REVISED');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUGOPEN_BALANCE');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CHEQUEBOOKLEAF_DET');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CHEQUEBOOKLEAF_DET_HIST');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CHEQUEBOOKLEAF_MAS');
/*delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CODINGSTRUCTURE_DET');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CODINGSTRUCTURE_DET_HIST');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CODINGSTRUCTURE_MAS');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CODINGSTRUCTURE_MAS_HIST');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CODINGSTRUC_DET_12072017');*/
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_DEPOADV_MAS');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_DEPOSITS');
/*delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_FIELD_MASTER');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_FIELD_MASTER_HIST');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_FUNCTION_MASTER');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_FUND_MASTER');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_FUND_MASTER_HIST');*/
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_LIABILITY_BOOKING');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_LIABILITY_BOOKING_DET');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PAYMENT_DET');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PAYMENT_MAS');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PAY_TO_BANK');
/*delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PRIMARYHEAD_MASTER');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PRIMARYHEAD_MASTER_HIST');*/
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PROJECTEDPROVISIONADJ');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PROJECTEDPROVISIONADJ_TR');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PROJECTEDREVENUE');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PROJECTED_EXPENDITURE');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_SECONDARYHEAD_MASTER');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_TDS_TAXHEADS');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_TDS_TAXHEADS_HIST');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_TENDER_DET');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_TENDER_MASTER');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHER');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHERTEMPLATE_DET');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHERTEMPLATE_MAS');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHER_DET');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHER_POST_DETAIL');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHER_POST_MASTER');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_BANK_ACCOUNT');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_TAX_BUDGET_CODE');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_ULB_BANK');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_tax_ac_mapping');
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_tax_ac_mapping_hist');;
delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_bank_tds_details');;
commit;

delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_ADVANCE';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BANK_DEPOSITSLIP_DENOM';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BANK_DEPOSITSLIP_MASTER';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BILL_DEDUCTION_DETAIL';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BILL_EXP_DETAIL';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BILL_MAS';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUDGETALLOCATION';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUDGETALLOCATION_HIST';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUDGETCODE_MAS';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUDGETORY_ESTIMATE';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUDGETORY_REVISED';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUGOPEN_BALANCE';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CHEQUEBOOKLEAF_DET';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CHEQUEBOOKLEAF_DET_HIST';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CHEQUEBOOKLEAF_MAS';
/*delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CODINGSTRUCTURE_DET';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CODINGSTRUCTURE_DET_HIST';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CODINGSTRUCTURE_MAS';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CODINGSTRUCTURE_MAS_HIST';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CODINGSTRUC_DET_12072017';*/
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_DEPOADV_MAS';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_DEPOSITS';
/*delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_FIELD_MASTER';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_FIELD_MASTER_HIST';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_FUNCTION_MASTER';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_FUND_MASTER';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_FUND_MASTER_HIST';*/
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_LIABILITY_BOOKING';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_LIABILITY_BOOKING_DET';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PAYMENT_DET';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PAYMENT_MAS';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PAY_TO_BANK';
/*delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PRIMARYHEAD_MASTER';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PRIMARYHEAD_MASTER_HIST';*/
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PROJECTEDPROVISIONADJ';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PROJECTEDPROVISIONADJ_TR';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PROJECTEDREVENUE';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PROJECTED_EXPENDITURE';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_SECONDARYHEAD_MASTER';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_TDS_TAXHEADS';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_TDS_TAXHEADS_HIST';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_TENDER_DET';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_TENDER_MASTER';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHER';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHERTEMPLATE_DET';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHERTEMPLATE_MAS';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHER_DET';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHER_POST_DETAIL';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHER_POST_MASTER';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_BANK_ACCOUNT';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_TAX_BUDGET_CODE';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_ULB_BANK';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_tax_ac_mapping';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_tax_ac_mapping_hist';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_bank_tds_details';
commit;

delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_ADVANCE');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BANK_DEPOSITSLIP_DENOM');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BANK_DEPOSITSLIP_MASTER');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BILL_DEDUCTION_DETAIL');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BILL_EXP_DETAIL');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BILL_MAS');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUDGETALLOCATION');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUDGETALLOCATION_HIST');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUDGETCODE_MAS');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUDGETORY_ESTIMATE');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUDGETORY_REVISED');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUGOPEN_BALANCE');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CHEQUEBOOKLEAF_DET');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CHEQUEBOOKLEAF_DET_HIST');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CHEQUEBOOKLEAF_MAS');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CODINGSTRUCTURE_DET');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CODINGSTRUCTURE_DET_HIST');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CODINGSTRUCTURE_MAS');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CODINGSTRUCTURE_MAS_HIST');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CODINGSTRUC_DET_12072017');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_DEPOADV_MAS');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_DEPOSITS');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_FIELD_MASTER');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_FIELD_MASTER_HIST');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_FUNCTION_MASTER');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_FUND_MASTER');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_FUND_MASTER_HIST');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_LIABILITY_BOOKING');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_LIABILITY_BOOKING_DET');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PAYMENT_DET');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PAYMENT_MAS');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PAY_TO_BANK');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PRIMARYHEAD_MASTER');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PRIMARYHEAD_MASTER_HIST');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PROJECTEDPROVISIONADJ');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PROJECTEDPROVISIONADJ_TR');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PROJECTEDREVENUE');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PROJECTED_EXPENDITURE');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_SECONDARYHEAD_MASTER');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_TDS_TAXHEADS');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_TDS_TAXHEADS_HIST');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_TENDER_DET');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_TENDER_MASTER');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHER');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHERTEMPLATE_DET');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHERTEMPLATE_MAS');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHER_DET');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHER_POST_DETAIL');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHER_POST_MASTER');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_BANK_ACCOUNT');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_TAX_BUDGET_CODE');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_ULB_BANK');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_tax_ac_mapping');
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_tax_ac_mapping_hist');;
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_bank_tds_details');;
commit;




delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_ADVANCE';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BANK_DEPOSITSLIP_DENOM';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BANK_DEPOSITSLIP_MASTER';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BILL_DEDUCTION_DETAIL';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BILL_EXP_DETAIL';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BILL_MAS';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUDGETALLOCATION';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUDGETALLOCATION_HIST';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUDGETCODE_MAS';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUDGETORY_ESTIMATE';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUDGETORY_REVISED';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_BUGOPEN_BALANCE';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CHEQUEBOOKLEAF_DET';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CHEQUEBOOKLEAF_DET_HIST';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CHEQUEBOOKLEAF_MAS';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CODINGSTRUCTURE_DET';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CODINGSTRUCTURE_DET_HIST';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CODINGSTRUCTURE_MAS';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CODINGSTRUCTURE_MAS_HIST';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_CODINGSTRUC_DET_12072017';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_DEPOADV_MAS';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_DEPOSITS';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_FIELD_MASTER';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_FIELD_MASTER_HIST';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_FUNCTION_MASTER';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_FUND_MASTER';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_FUND_MASTER_HIST';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_LIABILITY_BOOKING';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_LIABILITY_BOOKING_DET';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PAYMENT_DET';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PAYMENT_MAS';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PAY_TO_BANK';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PRIMARYHEAD_MASTER';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PRIMARYHEAD_MASTER_HIST';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PROJECTEDPROVISIONADJ';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PROJECTEDPROVISIONADJ_TR';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PROJECTEDREVENUE';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_PROJECTED_EXPENDITURE';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_SECONDARYHEAD_MASTER';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_TDS_TAXHEADS';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_TDS_TAXHEADS_HIST';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_TENDER_DET';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_TENDER_MASTER';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHER';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHERTEMPLATE_DET';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHERTEMPLATE_MAS';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHER_DET';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHER_POST_DETAIL';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_AC_VOUCHER_POST_MASTER';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_BANK_ACCOUNT';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_TAX_BUDGET_CODE';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_ULB_BANK';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_tax_ac_mapping';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_tax_ac_mapping_hist';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_bank_tds_details';
commit;


delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_bill_deduction_det_hist');
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_bill_deduction_det_hist';
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_bill_deduction_det_hist');
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_bill_deduction_det_hist';


delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_bill_exp_detail_hist');
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_bill_exp_detail_hist';
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_bill_exp_detail_hist');
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_bill_exp_detail_hist';

delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_bill_mas_hist');
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_bill_mas_hist';
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_bill_mas_hist');
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_bill_mas_hist';

delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_bugopen_balance_hist');
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_bugopen_balance_hist';
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_bugopen_balance_hist');
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_bugopen_balance_hist';


delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_projected_expendi_hist');
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_projected_expendi_hist';
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_projected_expendi_hist');
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_projected_expendi_hist';


delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_projectedrevenue_hist');
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_projectedrevenue_hist';
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_projectedrevenue_hist');
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_projectedrevenue_hist';


delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_secondaryhead_mas_hist');
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_secondaryhead_mas_hist';
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_secondaryhead_mas_hist');
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_secondaryhead_mas_hist';

delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_secondaryhead_master');
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_secondaryhead_master';
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_secondaryhead_master');
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_secondaryhead_master';

delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_voucher_det_hist');
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_voucher_det_hist';
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_voucher_det_hist');
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_voucher_det_hist';

delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_voucher_hist');
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_voucher_hist';
delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_voucher_hist');
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='tb_ac_voucher_hist';
commit;



