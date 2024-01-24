--liquibase formatted sql
--changeset Kanchan:V20220728133848__AL_INDEX_tb_as_bill_det_hist_28072022.sql
create index idx_tb_as_bill_det_hist_Revised_bill_date on tb_as_bill_det_hist (Revised_bill_date);
--liquibase formatted sql
--changeset Kanchan:V20220728133848__AL_INDEX_tb_as_bill_det_hist_280720221.sql
create index idx_tb_as_bill_det_hist_Revised_bill_type on tb_as_bill_det_hist (Revised_bill_type);
--liquibase formatted sql
--changeset Kanchan:V20220728133848__AL_INDEX_tb_as_bill_det_hist_280720222.sql 
create index idx_tb_as_bill_det_hist_H_STATUS on tb_as_bill_det_hist (H_STATUS);
--liquibase formatted sql
--changeset Kanchan:V20220728133848__AL_INDEX_tb_as_bill_det_hist_280720223.sql
create index idx_Tb_as_bill_mas_hist_Revised_bill_date on Tb_as_bill_mas_hist(Revised_bill_date);
--liquibase formatted sql
--changeset Kanchan:V20220728133848__AL_INDEX_tb_as_bill_det_hist_280720224.sql
create index idx_Tb_as_bill_mas_hist_Revised_bill_type on Tb_as_bill_mas_hist(Revised_bill_type);
--liquibase formatted sql
--changeset Kanchan:V20220728133848__AL_INDEX_tb_as_bill_det_hist_280720225.sql
create index idx_Tb_as_bill_mas_hist_H_STATUS on Tb_as_bill_mas_hist(H_STATUS);
--liquibase formatted sql
--changeset Kanchan:V20220728133848__AL_INDEX_tb_as_bill_det_hist_280720226.sql
create index idx_tb_as_bill_det_Revised_bill_date on tb_as_bill_det (Revised_bill_date);
--liquibase formatted sql
--changeset Kanchan:V20220728133848__AL_INDEX_tb_as_bill_det_hist_280720227.sql
create index idx_tb_as_bill_det_Revised_bill_type on tb_as_bill_det (Revised_bill_type);
--liquibase formatted sql
--changeset Kanchan:V20220728133848__AL_INDEX_tb_as_bill_det_hist_280720228.sql
create index idx_Tb_as_bill_mas_Revised_bill_date on Tb_as_bill_mas(Revised_bill_date);
--liquibase formatted sql
--changeset Kanchan:V20220728133848__AL_INDEX_tb_as_bill_det_hist_280720229.sql 
create index idx_Tb_as_bill_mas_Revised_bill_type on Tb_as_bill_mas(Revised_bill_type);
--liquibase formatted sql
--changeset Kanchan:V20220728133848__AL_INDEX_tb_as_bill_det_hist_2807202210.sql
create index idx_tb_as_bill_det_BD_CUR_TAXAMT on tb_as_bill_det (BD_CUR_TAXAMT);
--liquibase formatted sql
--changeset Kanchan:V20220728133848__AL_INDEX_tb_as_bill_det_hist_2807202211.sql
create index idx_tb_as_bill_det_BD_CUR_BAL_TAXAMT on tb_as_bill_det (BD_CUR_BAL_TAXAMT);
--liquibase formatted sql
--changeset Kanchan:V20220728133848__AL_INDEX_tb_as_bill_det_hist_2807202212.sql
create index idx_tb_as_bill_det_BD_PRV_BAL_ARRAMT on tb_as_bill_det (BD_PRV_BAL_ARRAMT);
--liquibase formatted sql
--changeset Kanchan:V20220728133848__AL_INDEX_tb_as_bill_det_hist_2807202213.sql
create index idx_tb_as_bill_det_BD_PRV_ARRAMT on tb_as_bill_det (BD_PRV_ARRAMT);
--liquibase formatted sql
--changeset Kanchan:V20220728133848__AL_INDEX_tb_as_bill_det_hist_2807202214.sql
create index idx_tb_as_bill_det_hist_BD_CUR_TAXAMT on tb_as_bill_det_hist (BD_CUR_TAXAMT);
--liquibase formatted sql
--changeset Kanchan:V20220728133848__AL_INDEX_tb_as_bill_det_hist_2807202215.sql
create index idx_tb_as_bill_det_hist_BD_CUR_BAL_TAXAMT on tb_as_bill_det_hist (BD_CUR_BAL_TAXAMT);
--liquibase formatted sql
--changeset Kanchan:V20220728133848__AL_INDEX_tb_as_bill_det_hist_2807202216.sql
create index idx_tb_as_bill_det_hist_BD_PRV_BAL_ARRAMT on tb_as_bill_det_hist (BD_PRV_BAL_ARRAMT);
--liquibase formatted sql
--changeset Kanchan:V20220728133848__AL_INDEX_tb_as_bill_det_hist_2807202217.sql
create index idx_tb_as_bill_det_hist_BD_PRV_ARRAMT on tb_as_bill_det_hist (BD_PRV_ARRAMT);