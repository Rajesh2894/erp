--liquibase formatted sql
--changeset nilima:V20181001150636__AL_vw_rej_checklist.sql
drop view vw_rej_checklist;

--liquibase formatted sql
--changeset nilima:V20181001150636__AL_vw_rej_checklist1.sql
CREATE VIEW vw_rej_checklist AS
    SELECT DISTINCT
    a.Att_Id as CLM_ID,
        a.CLM_DESC AS clm_desc,
        a.CLM_DESC_ENGL AS clm_desc_engl,
        a.APPLICATION_ID AS application_id,
        a.CLM_REMARK AS clm_remark
    FROM tb_attach_cfc a
    WHERE a.ATT_ID IN (SELECT 
                MAX(x.ATT_ID)
            FROM
                tb_attach_cfc x
            GROUP BY x.APPLICATION_ID , x.CLM_ID)
            AND a.CLM_APR_STATUS = 'N';