--liquibase formatted sql
--changeset Kanchan:V20210702192755__AL_tb_mrm_husband_02072021.sql
alter table tb_mrm_husband modify column UID_NO varchar(15);
--liquibase formatted sql
--changeset Kanchan:V20210702192755__AL_tb_mrm_husband_020720211.sql
alter table tb_mrm_husband_hist modify column UID_NO varchar(15);
--liquibase formatted sql
--changeset Kanchan:V20210702192755__AL_tb_mrm_husband_020720212.sql
alter table tb_mrm_wife modify column UID_NO varchar(15);
--liquibase formatted sql
--changeset Kanchan:V20210702192755__AL_tb_mrm_husband_020720213.sql
alter table tb_mrm_wife_hist modify column UID_NO varchar(15);
--liquibase formatted sql
--changeset Kanchan:V20210702192755__AL_tb_mrm_husband_020720214.sql
alter table tb_mrm_witness_det modify column UID_NO varchar(15);
--liquibase formatted sql
--changeset Kanchan:V20210702192755__AL_tb_mrm_husband_020720215.sql
alter table tb_mrm_witness_det_hist modify column UID_NO varchar(15);

