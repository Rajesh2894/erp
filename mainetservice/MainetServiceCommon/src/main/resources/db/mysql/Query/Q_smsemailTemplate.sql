select
(select DP_DEPTDESC from tb_department where DP_DEPTID=tb1.DP_DEPTID) Module_Name,
(select DP_DEPTDESC from tb_department where DP_DEPTID=tb1.DP_DEPTID) Department_Name,
(select SM_SERVICE_NAME from tb_services_mst where SM_SERVICE_ID=tb1.SM_SERVICEID) Servcie_Name,
(select SMFNAME from tb_sysmodfunction where SMFID=tb1.SMFID) Event_Name,
tb2.MESSAGE_TYPE TemplateType,
(Select CPD_DESC from TB_COMPARAM_det x,
tb_comparam_mas y
where CPD_VALUE=tb2.MESSAGE_TYPE and x.cpm_id=y.cpm_id and cpm_prefix='SMT') TemplateType_descrption,
tb2.SMS_BODY SMS_Body_in_English,
tb2.SMS_BODY_REG SMS_Body_in_hindi,
tb2.MAIL_BODY Email_Body_in_English,
tb2.MAIL_BODY_REG Email_Body_in_hindi
from
tb_portal_sms_integration tb1,
tb_portal_sms_mail_template tb2
where tb1.SE_ID=tb2.SE_ID