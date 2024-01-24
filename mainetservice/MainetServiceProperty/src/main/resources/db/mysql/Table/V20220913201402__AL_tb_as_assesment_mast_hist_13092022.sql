--liquibase formatted sql
--changeset Kanchan:V20220913201402__AL_tb_as_assesment_mast_hist_13092022.sql
alter table tb_as_assesment_mast_hist
add column MN_ASS__PARSH_WARD1 bigint null default null,
add column MN_ASS__PARSH_WARD2 bigint null default null,
add column MN_ASS__PARSH_WARD3 bigint null default null,
add column MN_ASS__PARSH_WARD4 bigint null default null,
add column MN_ASS__PARSH_WARD5 bigint null default null;
--liquibase formatted sql
--changeset Kanchan:V20220913201402__AL_tb_as_assesment_mast_hist_130920221.sql
alter table tb_as_assesment_mast_hist add column Revised_bill_date datetime null default null,add column Revised_bill_type varchar(20)  null default null;