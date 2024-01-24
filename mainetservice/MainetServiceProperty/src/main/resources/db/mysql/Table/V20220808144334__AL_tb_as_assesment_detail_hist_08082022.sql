--liquibase formatted sql
--changeset Kanchan:V20220808144334__AL_tb_as_assesment_detail_hist_08082022.sql
alter table tb_as_assesment_detail_hist add column MN_ASS_HIS_id bigint(20) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220808144334__AL_tb_as_assesment_detail_hist_080820221.sql
alter table TB_AS_ASSESMENT_OWNER_DTL_HIST add column MN_ASS_HIS_id bigint(20) null default null;