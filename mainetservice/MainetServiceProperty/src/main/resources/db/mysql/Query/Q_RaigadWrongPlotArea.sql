**********List of Properties having plot area < Buildup area************
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
and b.MN_ASS_no in
('CGRGRR0000068000','CGRGRR0000095000','CGRGRR0000127000','CGRGRR0000393000','CGRGRR0000727000',
'CGRGRR0000809000','CGRGRR0001457000','CGRGRR0001472000','CGRGRR0001493000','CGRGRR0001560000',
'CGRGRR0001790000','CGRGRR0001917000','CGRGRR0002003000','CGRGRR0002122000','CGRGRR0002194000',
'CGRGRR0002599000','CGRGRR0002737000','CGRGRR0003000000','CGRGRR0003076000','CGRGRR0003278000',
'CGRGRR0003422000','CGRGRR0003543000','CGRGRR0003575000','CGRGRR0003821000','CGRGRR0003894000',
'CGRGRR0003997000','CGRGRR0004225000','CGRGRR0004369000','CGRGRR0004513000','CGRGRR0004532000',
'CGRGRR0004691000','CGRGRR0004746000','CGRGRR0004809000','CGRGRR0004851000','CGRGRR0004888000',
'CGRGRR0005155000','CGRGRR0005158000','CGRGRR0005360000','CGRGRR0005485000','CGRGRR0005745000',
'CGRGRR0005807000','CGRGRR0005879000','CGRGRR0005897000','CGRGRR0005916000','CGRGRR0005928000',
'CGRGRR0006250000','CGRGRR0006390000','CGRGRR0006492000','CGRGRR0006568000','CGRGRR0006644000',
'CGRGRR0006686000','CGRGRR0006954000','CGRGRR0007205000','CGRGRR0007218000','CGRGRR0007236000',
'CGRGRR0007444000','CGRGRR0007550000','CGRGRR0007558000','CGRGRR0007571000','CGRGRR0007604000',
'CGRGRR0007700000','CGRGRR0007700000','CGRGRR0007740000','CGRGRR0007772000','CGRGRR0007852000',
'CGRGRR0007852000','CGRGRR0007852000','CGRGRR0007883000','CGRGRR0007883000','CGRGRR0007935000',
'CGRGRR0007955000','CGRGRR0008081000','CGRGRR0008112000','CGRGRR0008112000','CGRGRR0008115000',
'CGRGRR0008364000','CGRGRR0008462000','CGRGRR0008478000','CGRGRR0008500000','CGRGRR0008735000',
'CGRGRR0008870000','CGRGRR0008945000','CGRGRR0009003000','CGRGRR0009005000','CGRGRR0009057000',
'CGRGRR0009146000','CGRGRR0009422000','CGRGRR0009429000','CGRGRR0009443000','CGRGRR0009470000',
'CGRGRR0009502000','CGRGRR0009503000','CGRGRR0009556000','CGRGRR0009638000','CGRGRR0009642000',
'CGRGRR0009645000','CGRGRR0009646000','CGRGRR0009910000','CGRGRR0009939000','CGRGRR0009947000',
'CGRGRR0010042000','CGRGRR0010074000','CGRGRR0010081000','CGRGRR0010464000','CGRGRR0010845000',
'CGRGRR0010924000','CGRGRR0011079000','CGRGRR0011117000','CGRGRR0011171000','CGRGRR0011201000',
'CGRGRR0011597000','CGRGRR0011749000','CGRGRR0011994000','CGRGRR0012217000','CGRGRR0012476000',
'CGRGRR0012508000','CGRGRR0012597000','CGRGRR0012612000','CGRGRR0012647000','CGRGRR0012650000',
'CGRGRR0012895000','CGRGRR0013080000','CGRGRR0013633000','CGRGRR0013635000','CGRGRR0013663000',
'CGRGRR0013856000','CGRGRR0014284000','CGRGRR0014419000','CGRGRR0014936000','CGRGRR0014943000',
'CGRGRR0014966000','CGRGRR0015411000','CGRGRR0015526000','CGRGRR0015608000','CGRGRR0016171000',
'CGRGRR0016217000','CGRGRR0016223000','CGRGRR0016229000','CGRGRR0016310000','CGRGRR0016348000',
'CGRGRR0016348000','CGRGRR0016451000','CGRGRR0016465000','CGRGRR0016517000','CGRGRR0016629000',
'CGRGRR0016719000','CGRGRR0016725000','CGRGRR0016739000','CGRGRR0016757000','CGRGRR0016902000',
'CGRGRR0016907000','CGRGRR0017004000','CGRGRR0017121000','CGRGRR0017517000','CGRGRR0017735000',
'CGRGRR0017975000','CGRGRR0018031000','CGRGRR0018143000','CGRGRR0018154000','CGRGRR0018161000',
'CGRGRR0018338000','CGRGRR0018389000','CGRGRR0018593000','CGRGRR0018593000','CGRGRR0018830000',
'CGRGRR0018833000','CGRGRR0018847000','CGRGRR0018880000','CGRGRR0018947000');

**************data entry problem*******
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
and b.MN_ASS_no in
('CGRGRR0000150000','CGRGRR0000634000','CGRGRR0000776000','CGRGRR0001034000','CGRGRR0001285000',
'CGRGRR0001319000','CGRGRR0001373000','CGRGRR0002370000','CGRGRR0002454000','CGRGRR0002455000',
'CGRGRR0002632000','CGRGRR0003157000','CGRGRR0003375000','CGRGRR0003594000','CGRGRR0003609000',
'CGRGRR0003751000','CGRGRR0003753000','CGRGRR0003946000','CGRGRR0003965000','CGRGRR0003971000',
'CGRGRR0004428000','CGRGRR0004903000','CGRGRR0004956000','CGRGRR0005007000','CGRGRR0005111000',
'CGRGRR0005886000','CGRGRR0006215000','CGRGRR0006248000','CGRGRR0006305000','CGRGRR0007120000',
'CGRGRR0007157000','CGRGRR0007158000','CGRGRR0007220000','CGRGRR0007270000','CGRGRR0007629000',
'CGRGRR0007808000','CGRGRR0007906000','CGRGRR0007990000','CGRGRR0007993000','CGRGRR0008042000',
'CGRGRR0008088000','CGRGRR0008549000','CGRGRR0008817000','CGRGRR0008886000','CGRGRR0008966000',
'CGRGRR0009604000','CGRGRR0009711000','CGRGRR0009718000','CGRGRR0009719000','CGRGRR0009863000',
'CGRGRR0010122000','CGRGRR0010405000','CGRGRR0010532000','CGRGRR0010628000','CGRGRR0011136000',
'CGRGRR0011469000','CGRGRR0011505000','CGRGRR0011687000','CGRGRR0011847000','CGRGRR0011873000',
'CGRGRR0012032000','CGRGRR0012164000','CGRGRR0012280000','CGRGRR0012435000','CGRGRR0012689000',
'CGRGRR0012992000','CGRGRR0013007000','CGRGRR0013067000','CGRGRR0013106000','CGRGRR0013107000',
'CGRGRR0013108000','CGRGRR0013315000','CGRGRR0013606000','CGRGRR0013609000','CGRGRR0013653000',
'CGRGRR0013765000','CGRGRR0013798000','CGRGRR0013971000','CGRGRR0014289000','CGRGRR0014321000',
'CGRGRR0014596000','CGRGRR0014602000','CGRGRR0014747000','CGRGRR0014751000','CGRGRR0014815000',
'CGRGRR0014822000','CGRGRR0015167000','CGRGRR0015183000','CGRGRR0015187000','CGRGRR0015539000',
'CGRGRR0015555000','CGRGRR0015707000','CGRGRR0015892000','CGRGRR0016059000','CGRGRR0016162000',
'CGRGRR0016478000','CGRGRR0016872000','CGRGRR0016910000','CGRGRR0017372000','CGRGRR0017437000',
'CGRGRR0017802000','CGRGRR0017969000','CGRGRR0018162000','CGRGRR0018366000','CGRGRR0018595000',
'CGRGRR0018604000','CGRGRR0018810000','CGRGRR0018850000','CGRGRR0018864000','CGRGRR0018876000',
'CGRGRR0018939000','CGRGRR0018973000');
****************
select * from 
(select a.MN_ASS_no,Lbulidup,sum(Gbulidup) Gbulidup,a.MN_ASS_plot_area plotarea
	  from (select b.MN_ASS_no,a.MN_assd_buildup_area as Lbulidup,b.MN_ASS_plot_area
	        from tb_as_assesment_detail a,tb_as_assesment_mast b 
			where a.mn_ass_id=b.mn_ass_id and a.mn_ass_id in
            (select distinct mn_ass_id from tb_as_assesment_detail where orgid=17
             group by mn_ass_id having count(1) >1) and mn_assd_floor_no in 
	        (select cpd_id from tb_comparam_det where cpm_id=76 and orgid=1 and
             cpd_value='L') ) a,
            (select b.MN_ASS_no,a.MN_assd_buildup_area as Gbulidup
			from tb_as_assesment_detail a,tb_as_assesment_mast b 
			where a.mn_ass_id=b.mn_ass_id and a.mn_ass_id in 
           (select distinct mn_ass_id from tb_as_assesment_detail where orgid=17
            group by mn_ass_id
            having count(1) >1) and mn_assd_floor_no in 
	       (select cpd_id from tb_comparam_det where cpm_id=76 and orgid=1 and
            cpd_value='G')) b
           where a.MN_ASS_no=b.MN_ASS_no group by a.MN_ASS_no,a.MN_ASS_plot_area,Lbulidup) a
  where (Lbulidup+Gbulidup)<>plotarea;
 