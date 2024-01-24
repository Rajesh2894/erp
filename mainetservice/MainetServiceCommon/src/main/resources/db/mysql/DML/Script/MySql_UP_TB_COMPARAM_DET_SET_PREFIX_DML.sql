update TB_COMPARAM_DET j set j.cpd_value='Y' where j.cpd_value='ST1' and cpm_id in
(select cpm_id from TB_COMPARAM_MAS where cpm_prefix='SET');

update TB_COMPARAM_DET j set j.cpd_value='N' where j.cpd_value='ST2' and cpm_id in
(select cpm_id from TB_COMPARAM_MAS where cpm_prefix='SET');

update TB_COMPARAM_DET j set j.cpd_value='NA' where j.cpd_value='ST3' and cpm_id in
(select cpm_id from TB_COMPARAM_MAS where cpm_prefix='SET');

commit;