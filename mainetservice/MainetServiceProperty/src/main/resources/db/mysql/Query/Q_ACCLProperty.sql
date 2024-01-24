select 
(select O_NLS_ORGNAME from tb_organisation where orgid=a.orgid) OrganisationName,
a.mn_ass_no AS PropertyNo,
a.mn_ass_oldpropno AS oldPropertyNo, 
(select cpd_desc from tb_comparam_det where cpd_id=a.mn_ass_owner_type) AS OwnershipType,
(select cod_desc from tb_comparent_det where cod_id=c.natureofproperty1) As PropertyType,
(select cod_desc from tb_comparent_det where cod_id=a.mn_ass_ward1) As PropertyZone,
(select cod_desc from tb_comparent_det where cod_id=a.mn_ass_ward2) As PropertyWard,
(select cpd_desc from tb_comparam_det where cpd_id=a.mn_prop_lvl_road_type) RoadType,
concat ('\r\n',  b.MN_asso_owner_name, 
COALESCE(concat("(", (select cpd_desc from tb_comparam_det where cpd_id = b.MN_gender_id),") ",
(select cpd_desc from tb_comparam_det where cpd_id = b.MN_relation_id)), " Contact Person Name:")," ",
 b.MN_asso_guardian_name, " , Mobile No:", b.MN_asso_mobileno) As Owner_Details,
concat (" Floor No :",'\r\n',
(select cpd_desc from tb_comparam_det where cpd_id=c.mn_assd_floor_no),'\r\n'," ConstructionType :",
(select cpd_desc from tb_comparam_det where cpd_id=c.mn_assd_constru_type),'\r\n',
" UsageType :",(select cod_desc from tb_comparent_det where cod_id=c.mn_assd_usagetype1),'\r\n',
" NatureOfProperty :",(select cod_desc from tb_comparent_det where cod_id=c.natureofproperty1),'\r\n', "Year Of Construction :",c.mn_assd_year_construction,'\r\n',
"Year Of Construction :",c.mn_assd_buildup_area) As FloorDetail,
concat (" Occupancy Type :",'\r\n',COALESCE((select cpd_desc from tb_comparam_det where cpd_id=c.mn_assd_occupancy_type),''),'\r\n'," Occupier Name :",'\r\n',COALESCE(c.MN_assd_occupier_name,''),
'\r\n'," Property Type :",'\r\n',(select cod_desc from tb_comparent_det where cod_id=c.natureofproperty1)) As AdditionalUnitDetails
from tb_as_assesment_mast a,
tb_as_assesment_owner_dtl b ,
tb_as_assesment_detail c
where a.mn_ass_id=b.mn_ass_id and
c.mn_ass_id=b.mn_ass_id and
(c.natureofproperty1=(select cod_id from tb_comparent_det where cod_desc='Apartment' and cod_value='AP') or
a.mn_ass_owner_type=(select cpd_id from tb_comparam_det where cpd_desc='Colony / Society' )) and
a.orgid in
(select orgid from tb_organisation a where 
a.O_NLS_ORGNAME like '%Jhagarakhand Nagar Panchayat%' or 
a.O_NLS_ORGNAME like '%NaiLedri Nagar Panchayat%' or
a.O_NLS_ORGNAME like '%Chirimiri Municipal Corporation%' or 
a.O_NLS_ORGNAME like '%Bishrampur Nagar Panchayat%' or
a.O_NLS_ORGNAME like '%Jarhi Nagar Panchayat%' or
a.O_NLS_ORGNAME like '%Khongapani Nagar Panchayat%' or
a.O_NLS_ORGNAME like '%Bhatgaon Nagar Panchayat (Surajpur)%' or
a.O_NLS_ORGNAME like '%Dipka Municipal Council%' or
a.O_NLS_ORGNAME like '%Korba Municipal Corporation%' or
a.O_NLS_ORGNAME like '%Shivpurcharcha Municipal Council%')
order by OrganisationName,PropertyNo