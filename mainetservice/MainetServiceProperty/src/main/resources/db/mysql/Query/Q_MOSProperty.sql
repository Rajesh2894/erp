**********onlt 1 Unit******************
select
(select cod_desc from tb_comparent_det where cod_id=b.MN_ASS_ward1 ) Ward1, 
(select cod_desc from tb_comparent_det where cod_id=b.MN_ASS_ward2 ) Ward2, 
b.MN_ASS_no, 
(select cpd_desc from tb_comparam_det where cpd_id=b.MN_ASS_owner_type) OwnerType,
(select cpd_desc from tb_comparam_det where cpd_id=b.MN_PROP_LVL_ROAD_TYPE) RoadType,
(select cpd_desc from tb_comparam_det where cpd_id=a.mn_assd_floor_no) FloorNo,
(select cod_desc from tb_comparent_det where cod_id=MN_assd_usagetype1) UsageType1, 
(select cod_desc from tb_comparent_det where cod_id=MN_assd_usagetype2) UsageType2,
(select cpd_desc from tb_comparam_det where cpd_id=MN_assd_constru_type) ConstructionType, 
(select cpd_desc from tb_comparam_det where cpd_id=MN_assd_occupancy_type) OccupancyType, 
(select cod_desc from tb_comparent_det where cod_id=a.natureOfProperty1) PropertyType ,
(select cod_desc from tb_comparent_det where cod_id=natureOfProperty2) PropertyType2,
b.MN_ASS_address Address, 
(select x.LOC_NAME_ENG from tb_location_mas x where loc_id=b.MN_loc_id) Location, 
b.MN_ASS_corr_address Address, 
b.MN_ASS_acq_date AcqusitionDate, 
b.MN_ASS_plot_area PlotArea, 
MN_assd_buildup_area BuilArea , 
MN_assd_year_construction ConstructionYear,
MN_assd_assesment_date AssesmentDate, 
MN_assd_unit_no UnitNo, 
MN_assd_occupier_name OccupierName
from tb_as_assesment_detail a,
tb_as_assesment_mast b
where a.mn_ass_id=b.mn_ass_id and
a.mn_ass_id in
(select mn_ass_id from tb_as_assesment_detail where orgid=17
group by mn_ass_id
having count(1) =1)  and mn_assd_floor_no in (select cpd_id from tb_comparam_det where cpm_id=76 and orgid=1 and
cpd_value in ('L','G')) and mn_assd_constru_type in
(select cpd_id from tb_comparam_det where cpm_id=73 and orgid=17 and cpd_desc='Land')


*******************morethan 1 unit****************
select
(select cod_desc from tb_comparent_det where cod_id=b.MN_ASS_ward1 ) Ward1, 
(select cod_desc from tb_comparent_det where cod_id=b.MN_ASS_ward2 ) Ward2, 
b.MN_ASS_no, 
(select cpd_desc from tb_comparam_det where cpd_id=b.MN_ASS_owner_type) OwnerType,
(select cpd_desc from tb_comparam_det where cpd_id=b.MN_PROP_LVL_ROAD_TYPE) RoadType,
(select cpd_desc from tb_comparam_det where cpd_id=a.mn_assd_floor_no) FloorNo,
(select cod_desc from tb_comparent_det where cod_id=MN_assd_usagetype1) UsageType1, 
(select cod_desc from tb_comparent_det where cod_id=MN_assd_usagetype2) UsageType2,
(select cpd_desc from tb_comparam_det where cpd_id=MN_assd_constru_type) ConstructionType, 
(select cpd_desc from tb_comparam_det where cpd_id=MN_assd_occupancy_type) OccupancyType, 
(select cod_desc from tb_comparent_det where cod_id=a.natureOfProperty1) PropertyType ,
(select cod_desc from tb_comparent_det where cod_id=natureOfProperty2) PropertyType2,
b.MN_ASS_address Address, 
(select x.LOC_NAME_ENG from tb_location_mas x where loc_id=b.MN_loc_id) Location, 
b.MN_ASS_corr_address Address, 
b.MN_ASS_acq_date AcqusitionDate, 
b.MN_ASS_plot_area PlotArea, 
MN_assd_buildup_area BuilArea , 
MN_assd_year_construction ConstructionYear,
MN_assd_assesment_date AssesmentDate, 
MN_assd_unit_no UnitNo, 
MN_assd_occupier_name OccupierName
from tb_as_assesment_detail a,
tb_as_assesment_mast b
where a.mn_ass_id=b.mn_ass_id and
a.mn_ass_id in
(select mn_ass_id from tb_as_assesment_detail where mn_ass_id in
(select mn_ass_id from tb_as_assesment_detail where orgid=17
group by mn_ass_id
having count(1) >1)  and mn_assd_floor_no in (select cpd_id from tb_comparam_det where cpm_id=76 and orgid=1 and
cpd_value in ('L','G')) and mn_assd_constru_type in
(select cpd_id from tb_comparam_det where cpm_id=73 and orgid=17 and cpd_desc='Land'))
