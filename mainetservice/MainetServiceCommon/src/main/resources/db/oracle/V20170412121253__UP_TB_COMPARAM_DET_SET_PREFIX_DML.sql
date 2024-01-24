--liquibase formatted sql
--changeset nilima:V20170412121253__UP_TB_COMPARAM_DET_SET_PREFIX_DML.sql
update tb_comparam_det j set j.cpd_value='Y' where j.cpd_value='ST1' and cpm_id in
(select cpm_id from tb_comparam_mas where cpm_prefix='SET');

update tb_comparam_det j set j.cpd_value='N' where j.cpd_value='ST2' and cpm_id in
(select cpm_id from tb_comparam_mas where cpm_prefix='SET');

update tb_comparam_det j set j.cpd_value='NA' where j.cpd_value='ST3' and cpm_id in
(select cpm_id from tb_comparam_mas where cpm_prefix='SET');

commit;
