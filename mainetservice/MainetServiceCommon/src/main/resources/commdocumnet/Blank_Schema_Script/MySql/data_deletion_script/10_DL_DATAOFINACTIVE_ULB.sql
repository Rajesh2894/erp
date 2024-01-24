delete from EMPLOYEE where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from EMPLOYEE_HIST where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from ROLE_ENTITLEMENT where org_id in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from ROLE_ENTITLEMENT_HIST where org_id in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_ATTACH_CFC where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_AUDIT_SERVICES_MST where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_AUDIT_SERVICES_MST where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_CHECKLIST_MST where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_COMPARAM_DET where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_COMPARAM_DET_HIST where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_COMPARENT_DET where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_COMPARENT_DET_HIST where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_COMPARENT_MAS where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_COMPARENT_MAS_HIST where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_DEPORG_MAP where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_DEPORG_MAP where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_DEPT_LOCATION where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_DEPT_LOCATION_HIST where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_DOCUMENT_GROUP where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_FINCIALYEARORG_MAP where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_FINCIALYEARORG_MAP_HIST where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_GROUP_MAST where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_GROUP_MAST_HIST where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_LOCATION_ELECT_WARDZONE where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_LOCATION_OPER_WARDZONE where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');


delete from TB_LOCATION_REVENUE_WARDZONE where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');


delete from TB_LOCATION_MAS where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_LOCATION_MAS_HIST where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_TAX_DET where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_TAX_DET_HIST where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from tb_tax_ac_mapping where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from tb_tax_ac_mapping_hist where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_TAX_MAS where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_TAX_MAS_HIST where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_VENDORMASTER where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_SCRUTINY_DET where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_SCRUTINY_DET_HIST where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_SCRUTINY_LABELS where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_SCRUTINY_LABELS_HIST where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_SCRUTINY_VALUES where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_SCRUTINY_VALUES_HIST where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_SERVICES_EVENT where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_SERVICES_EVENT_HIST where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_SERVICES_MST_HIST where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_SERVICES_MST where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from TB_ORG_DESIGNATION_HIST where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from tb_organisation where ORG_STATUS='I';
commit;




