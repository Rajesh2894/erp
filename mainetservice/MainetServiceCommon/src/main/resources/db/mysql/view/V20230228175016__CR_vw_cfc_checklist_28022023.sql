--liquibase formatted sql
--changeset Kanchan:V20230228175016__CR_vw_cfc_checklist_28022023.sql
CREATE OR REPLACE VIEW vw_cfc_checklist AS
SELECT
  a.APM_APPLICATION_ID AS apm_application_id,
  a.APM_APPLICATION_DATE AS apm_application_date,
  FN_GETCPDDESC(a.APM_TITLE, 'E', a.ORGID) AS englis_title,
  FN_GETCPDDESC(a.APM_TITLE, 'R', a.ORGID) AS Regional_title,
  CONCAT(
    IFNULL(a.APM_FNAME, ''),
    ' ',
    IFNULL(a.APM_MNAME, ''),
    ' ',
    IFNULL(a.APM_LNAME, '')
  ) AS applicants_name,
  a.SM_SERVICE_ID AS sm_service_id,
  c.SM_SERVICE_NAME AS service_name,
  c.SM_SERVICE_NAME_MAR AS service_name_mar,
  IFNULL(a.APM_CHKLST_VRFY_FLAG, 'P') AS apm_chklst_vrfy_flag,
  a.ORGID AS orgid,
  c.CDM_DEPT_ID AS cdm_dept_id,
  a.REF_NO AS REF_NO
FROM
  (
    tb_cfc_application_mst a
    JOIN tb_services_mst c
  )
WHERE
  (
    (a.SM_SERVICE_ID = c.SM_SERVICE_ID)
    AND (a.ORGID = c.ORGID)
    AND (IFNULL(a.APM_PAY_STAT_FLAG, 'x') IN ('F', 'Y'))
    AND EXISTS(
      SELECT
        1
      FROM
        tb_attach_cfc ac
      WHERE
        (
          (ac.ORGID = a.ORGID)
          AND (ac.APPLICATION_ID = a.APM_APPLICATION_ID)
          AND (ac.SERVICE_ID = a.SM_SERVICE_ID)
        )
    )
  );