delete from table TB_LOI_DET;
delete from table TB_LOI_DET_HIST;
delete from table TB_LOI_MAS;
delete from table TB_LOI_MAS_HIST;
delete from table TB_MRDATA;
delete from table TB_MRDATA_HIST;
delete from table TB_METER_MAS;
delete from table TB_METER_MAS_HIST;
delete from table TB_WORK_ORDER;
delete from table TB_WORK_ORDER_DETAIL;
delete from table TB_WORK_ORDER_DETAIL_HIST;
delete from table TB_WORK_ORDER_HIST;
delete from table TB_WT_BILL_DET;
delete from table TB_WT_BILL_DET_HIST;
delete from table TB_WT_BILL_GEN_ERROR;
delete from table TB_WT_BILL_MAS;
delete from table TB_WT_BILL_MAS_HIST;
delete from table TB_WT_BILL_SCHEDULE;
delete from table TB_WT_BILL_SCHEDULE_DETAIL;
delete from table TB_WT_BILL_SCHEDULE_DET_HIST;
delete from table TB_WT_BILL_SCHEDULE_HIST;
delete from table TB_WT_CHANGE_OF_OWNERSHIP;
delete from table TB_WT_CHANGE_OF_OWNERSHIP_HIST;
delete from table TB_WT_CHANGE_OF_USE;
delete from table TB_WT_CSMR_ADDITIONAL_OWNER;
delete from table TB_WT_CSMR_ADDIT_OWNER_HIST;
delete from table TB_WT_CSMR_ROAD_TYPES;
delete from table TB_WT_CSMR_ROAD_TYPES_HIST;
delete from table TB_WT_DEMAND_NOTICE;
delete from table TB_WT_DEMAND_NOTICE_HIST;
delete from table TB_WT_DISCONNECTIONS;
delete from table TB_WT_DISCONNECTIONS_HIST;
delete from table TB_WT_EXCEPTION_MGAP;
delete from table TB_WT_EXCESS_AMT;
delete from table TB_WT_EXCESS_AMT_HIST;
delete from table TB_WT_INST_CSMP;
delete from table TB_WT_INST_CSMP_HIST;
delete from table TB_WT_METER_CUT_RESTORATION;
delete from table TB_WT_METER_CUT_RESTOR_HIST;
delete from table TB_WT_NODUES;
delete from table TB_WT_NODUES_HIST;
delete from table TB_WT_OLD_NEW_CCN_LINK;
delete from table TB_WT_OLD_NEW_CCN_LINK_HIST;
delete from table TB_WT_PLUMRENEWAL_REG;
delete from table TB_WT_PLUMRENEWAL_REG_HIST;
delete from table TB_WT_PLUM_EXPERIENCE;
delete from table TB_WT_PLUM_EXPERIENCE_HIST;
delete from table TB_WT_PLUM_MAS;
delete from table TB_WT_PLUM_MAS_HIST;
delete from table TB_WT_PLUM_QUALIFICATION;
delete from table TB_WT_PLUM_QUALIFICATION_HIST;
delete from table TB_WT_PLUM_RENWANL_SCH;
delete from table TB_WT_PLUM_RENWANL_SCH_HIST;
delete from table TB_WT_RECONNECTION;
delete from table TB_WT_RECONNECTION_HIST;
delete from table TB_CSMRRCMD_MAS;
delete from table TB_CSMRRCMD_MAS_HIST;
delete from table TB_CSMR_INFO;
delete from table TB_CSMR_INFO_HIST;
commit;

delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME like 'TB_WT%');
delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME LIKE 'TB_WT%';
commit;

delete from _sequences where  name in (select sq_seq_name   from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME IN ('TB_CSMRRCMD_MAS',
'TB_CSMRRCMD_MAS_HIST','TB_CSMR_INFO','TB_CSMR_INFO_HIST','TB_LOI_DET','TB_LOI_DET_HIST','TB_LOI_MAS','TB_LOI_MAS_HIST',
'TB_METER_MAS','TB_METER_MAS_HIST','TB_MRDATA','TB_MRDATA_HIST','TB_SLOPE_PRM','TB_SLOPE_PRM_HIST','TB_WORK_ORDER','TB_WORK_ORDER_DETAIL',
'TB_WORK_ORDER_DETAIL','TB_WORK_ORDER_DETAIL_HIST','TB_WORK_ORDER_HIST'));

delete  from TB_JAVA_SEQ_GENERATION where SQ_TBL_NAME IN ('TB_CSMRRCMD_MAS',
'TB_CSMRRCMD_MAS_HIST','TB_CSMR_INFO','TB_CSMR_INFO_HIST','TB_LOI_DET','TB_LOI_DET_HIST','TB_LOI_MAS','TB_LOI_MAS_HIST',
'TB_METER_MAS','TB_METER_MAS_HIST','TB_MRDATA','TB_MRDATA_HIST','TB_SLOPE_PRM','TB_SLOPE_PRM_HIST','TB_WORK_ORDER','TB_WORK_ORDER_DETAIL',
'TB_WORK_ORDER_DETAIL','TB_WORK_ORDER_DETAIL_HIST','TB_WORK_ORDER_HIST');
commit;


delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME like 'TB_WT%');
delete  from TB_SEQ_GENERATION where SQ_TBL_NAME LIKE 'TB_WT%';
commit;


delete from _sequences where  name in (select sq_seq_name   from TB_SEQ_GENERATION where SQ_TBL_NAME IN ('TB_CSMRRCMD_MAS',
'TB_CSMRRCMD_MAS_HIST','TB_CSMR_INFO','TB_CSMR_INFO_HIST','TB_LOI_DET','TB_LOI_DET_HIST','TB_LOI_MAS','TB_LOI_MAS_HIST',
'TB_METER_MAS','TB_METER_MAS_HIST','TB_MRDATA','TB_MRDATA_HIST','TB_SLOPE_PRM','TB_SLOPE_PRM_HIST','TB_WORK_ORDER','TB_WORK_ORDER_DETAIL',
'TB_WORK_ORDER_DETAIL','TB_WORK_ORDER_DETAIL_HIST','TB_WORK_ORDER_HIST'));

delete  from TB_SEQ_GENERATION where SQ_TBL_NAME IN ('TB_CSMRRCMD_MAS',
'TB_CSMRRCMD_MAS_HIST','TB_CSMR_INFO','TB_CSMR_INFO_HIST','TB_LOI_DET','TB_LOI_DET_HIST','TB_LOI_MAS','TB_LOI_MAS_HIST',
'TB_METER_MAS','TB_METER_MAS_HIST','TB_MRDATA','TB_MRDATA_HIST','TB_SLOPE_PRM','TB_SLOPE_PRM_HIST','TB_WORK_ORDER','TB_WORK_ORDER_DETAIL',
'TB_WORK_ORDER_DETAIL','TB_WORK_ORDER_DETAIL_HIST','TB_WORK_ORDER_HIST');
commit;



