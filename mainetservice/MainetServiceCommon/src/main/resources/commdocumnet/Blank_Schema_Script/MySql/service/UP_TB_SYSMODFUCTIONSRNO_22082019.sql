update tb_sysmodfunction set SMFSRNO=1 where sm_shortdesc
in 
(select PSM_SHORT_NAME from tb_portal_service_master where orgid=1 and PSM_DP_DEPTCODE='AS');
COMMIT;

update tb_sysmodfunction set SMFSRNO=2 where sm_shortdesc
in 
(select PSM_SHORT_NAME from tb_portal_service_master where orgid=1 and PSM_DP_DEPTCODE='WT');
COMMIT;

update tb_sysmodfunction set SMFSRNO=3 where sm_shortdesc
in 
(select PSM_SHORT_NAME from tb_portal_service_master where orgid=1 and PSM_DP_DEPTCODE='RL');
COMMIT;

update tb_sysmodfunction set SMFSRNO=4 where sm_shortdesc
in 
(select PSM_SHORT_NAME from tb_portal_service_master where orgid=1 and PSM_DP_DEPTCODE='RTI');
COMMIT;


update tb_sysmodfunction set SMFSRNO=5 where sm_shortdesc
in 
(select PSM_SHORT_NAME from tb_portal_service_master where orgid=1 and PSM_DP_DEPTCODE='SWD');
COMMIT;

update tb_sysmodfunction set SMFSRNO=6 where sm_shortdesc
in 
(select PSM_SHORT_NAME from tb_portal_service_master where orgid=1 and PSM_DP_DEPTCODE='ML');
COMMIT;

