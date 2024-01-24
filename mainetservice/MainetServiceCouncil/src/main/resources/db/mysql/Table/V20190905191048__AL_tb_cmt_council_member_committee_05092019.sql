--liquibase formatted sql
--changeset Anil:V20190905191048__AL_tb_cmt_council_member_committee_05092019.sql
ALTER TABLE tb_cmt_council_member_committee
ADD COLUMN Expiry_Date DATETIME NULL AFTER COMMITTEE_DSG_ID,
ADD COLUMN Expiry_Reason VARCHAR(500) NULL AFTER Expiry_Date,
ADD COLUMN Member_Status CHAR(1) NULL AFTER Expiry_Reason;
--liquibase formatted sql
--changeset Anil:V20190905191048__AL_tb_cmt_council_member_committee_050920191.sql
ALTER TABLE tb_cmt_council_member_committee_hist
ADD COLUMN Expiry_Date DATETIME NULL AFTER COMMITTEE_DSG_ID,
ADD COLUMN Expiry_Reason VARCHAR(500) NULL AFTER Expiry_Date,
ADD COLUMN Member_Status CHAR(1) NULL AFTER Expiry_Reason;
