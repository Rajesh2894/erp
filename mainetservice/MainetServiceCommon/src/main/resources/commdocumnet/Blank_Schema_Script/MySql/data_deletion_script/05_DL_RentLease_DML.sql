delete from TB_RL_BILL_MAST;
delete from TB_RL_ESTATE_BOOKING;
delete from TB_RL_ESTATE_MAS;
delete from TB_RL_ESTATE_MAS_HIST;
delete from TB_RL_EST_CONTRACT_MAPPING;
delete from TB_RL_PROPERTY_DTL;
delete from TB_RL_PROPERTY_MAS;
delete from TB_RL_TENANT_ADD_OWNER;
delete from TB_RL_TENANT_MAS;
COMMIT;

delete from _sequences where  name in (select sq_seq_name  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_BILL_MAST');
delete from _sequences where  name in (select sq_seq_name  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_ESTATE_BOOKING');
delete from _sequences where  name in (select sq_seq_name  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_ESTATE_MAS');
delete from _sequences where  name in (select sq_seq_name  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_ESTATE_MAS_HIST');
delete from _sequences where  name in (select sq_seq_name  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_EST_CONTRACT_MAPPING');
delete from _sequences where  name in (select sq_seq_name  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_PROPERTY_DTL');
delete from _sequences where  name in (select sq_seq_name  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_PROPERTY_MAS');
delete from _sequences where  name in (select sq_seq_name  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_TENANT_ADD_OWNER');
delete from _sequences where  name in (select sq_seq_name  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_TENANT_MAS');

commit;

delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_BILL_MAST';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_ESTATE_BOOKING';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_ESTATE_MAS';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_ESTATE_MAS_HIST';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_EST_CONTRACT_MAPPING';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_PROPERTY_DTL';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_PROPERTY_MAS';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_TENANT_ADD_OWNER';
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_TENANT_MAS';

commit;

delete from _sequences where  name in (select sq_seq_name  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_BILL_MAST');
delete from _sequences where  name in (select sq_seq_name  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_ESTATE_BOOKING');
delete from _sequences where  name in (select sq_seq_name  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_ESTATE_MAS');
delete from _sequences where  name in (select sq_seq_name  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_ESTATE_MAS_HIST');
delete from _sequences where  name in (select sq_seq_name  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_EST_CONTRACT_MAPPING');
delete from _sequences where  name in (select sq_seq_name  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_PROPERTY_DTL');
delete from _sequences where  name in (select sq_seq_name  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_PROPERTY_MAS');
delete from _sequences where  name in (select sq_seq_name  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_TENANT_ADD_OWNER');
delete from _sequences where  name in (select sq_seq_name  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_TENANT_MAS');

commit;

delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_BILL_MAST';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_ESTATE_BOOKING';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_ESTATE_MAS';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_ESTATE_MAS_HIST';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_EST_CONTRACT_MAPPING';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_PROPERTY_DTL';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_PROPERTY_MAS';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_TENANT_ADD_OWNER';
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME='TB_RL_TENANT_MAS';

commit;
