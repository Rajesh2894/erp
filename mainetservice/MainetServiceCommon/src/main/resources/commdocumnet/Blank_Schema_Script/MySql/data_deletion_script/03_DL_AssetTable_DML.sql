----------------------------------------------------------------Master table 
DELETE FROM TB_AST_CHAOFDEP_MAS;                    
DELETE FROM TB_AST_FUNC_LOCATION_MAS;               
----------------------------------------------------------------Transaction Table


delete from tb_ast_realstd_rev;
DELETE FROM TB_AST_LINEAR_REV;
DELETE FROM TB_AST_CHART_OF_DEPRETN_REV;
DELETE FROM TB_AST_LEASING_REV;
DELETE FROM TB_AST_INSURANCE_REV;
DELETE FROM TB_AST_SERVICE_REALESTD_REV;
DELETE FROM TB_AST_PURCHASER_REV;
DELETE FROM TB_AST_INFO_MST_REV;
DELETE FROM TB_AST_CLASSFCTN_REV;



delete from tb_ast_error;
delete from tb_ast_realstd where asset_id in (
select asset_id from TB_AST_INFO_MST);
DELETE FROM TB_AST_CHART_OF_DEPRETN  where asset_id in (
select asset_id from TB_AST_INFO_MST);
DELETE FROM TB_AST_LEASING  where asset_id in (
select asset_id from TB_AST_INFO_MST);
DELETE FROM TB_AST_INSURANCE where asset_id in (
select asset_id from TB_AST_INFO_MST);
DELETE FROM TB_AST_CLASSFCTN where asset_id in (
select asset_id from TB_AST_INFO_MST);
DELETE FROM TB_AST_PURCHASER where asset_id in (
select asset_id from TB_AST_INFO_MST);
DELETE FROM TB_AST_SERVICE_REALESTD where asset_id in (
select asset_id from TB_AST_INFO_MST);
DELETE FROM TB_AST_LINEAR where asset_id in (
select asset_id from TB_AST_INFO_MST);

DELETE FROM TB_AST_TRANSFER;
DELETE FROM TB_AST_RETIRE;
DELETE FROM TB_AST_VALUATION_DET;
DELETE FROM TB_AST_REVAL;
DELETE FROM TB_AST_INFO_MST;



delete from tb_ast_error_hist;
DELETE FROM tb_ast_realstd_hist where asset_id in (
select asset_id from TB_AST_INFO_MST_HIST);
DELETE FROM TB_AST_CHART_OF_DEPRETN_HIST  where asset_id in (
select asset_id from TB_AST_INFO_MST_HIST);
DELETE FROM TB_AST_LEASING_HIST where asset_id in (
select asset_id from TB_AST_INFO_MST_HIST);
DELETE FROM TB_AST_INSURANCE_HIST where asset_id in (
select asset_id from TB_AST_INFO_MST_HIST);
DELETE FROM TB_AST_CLASSFCTN_HIST where asset_id in (
select asset_id from TB_AST_INFO_MST_HIST);
DELETE FROM TB_AST_PURCHASER_HIST where asset_id in (
select asset_id from TB_AST_INFO_MST_HIST);
DELETE FROM TB_AST_SERVICE_REALESTD_HIST where asset_id in (
select asset_id from TB_AST_INFO_MST_HIST);
DELETE FROM TB_AST_LINEAR_HIST where asset_id in (
select asset_id from TB_AST_INFO_MST_HIST);
DELETE FROM TB_AST_CHAOFDEP_MAS_HIST;
DELETE FROM TB_AST_FUNC_LOCATION_MAS_HIST;
DELETE FROM TB_AST_TRANSFER_HIST;
DELETE FROM TB_AST_RETIRE_HIST;
DELETE FROM TB_AST_VALUATION_DET_HIST;
DELETE FROM TB_AST_INFO_MST_HIST;



delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where UPPER(sq_mdl_name)='AST');
delete FROM tb_seq_generation WHERE UPPER(sq_mdl_name)='AST';

delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where UPPER(sq_mdl_name)='AST');
delete FROM tb_java_seq_generation WHERE UPPER(sq_mdl_name)='AST';
commit;

