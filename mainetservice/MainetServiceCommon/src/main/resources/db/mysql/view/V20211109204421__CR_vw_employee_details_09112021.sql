--liquibase formatted sql
--changeset Kanchan:V20211109204421__CR_vw_employee_details_09112021.sql
CREATE VIEW vw_employee_details AS
    SELECT
        a.EMPID AS empid,
        d.cpd_desc AS cpd_desc,
        a.CPD_TTL_ID AS cpd_title,
        a.EMPNAME AS empname,
        a.EMPMNAME AS empmname,
        a.EMPLNAME AS emplname,
        a.EMP_GENDER AS emp_gender,
        e.cpd_desc AS emp_genderName,
        a.EMPEMAIL AS empemail,
        a.EMPMOBNO AS empmobno,
        a.EMP_ADDRESS AS emp_address,
        a.EMP_ADDRESS1 AS emp_address1,
        a.EMPPINCODE AS emppincode,
        b.dp_deptdesc AS dp_deptdesc,
        a.DP_DEPTID AS dp_deptid,
        c.dsgid AS dsgid,
        c.dsgname AS dsgname
    FROM
        ((((employee a
        LEFT JOIN (SELECT
            tb_department.DP_DEPTID AS dp_deptid,
                tb_department.DP_DEPTDESC AS dp_deptdesc
        FROM
            tb_department) b ON ((a.DP_DEPTID = b.dp_deptid)))
        LEFT JOIN (SELECT
            designation.DSGID AS dsgid,
                designation.DSGNAME AS dsgname
        FROM
            designation) c ON ((a.DSGID = c.dsgid)))
        LEFT JOIN (SELECT
            tb_comparam_det.CPD_ID AS cpd_id,
                tb_comparam_det.CPD_DESC AS cpd_desc,
                tb_comparam_det.CPM_ID AS cpm_id
        FROM
            tb_comparam_det
        WHERE
            (tb_comparam_det.CPM_ID = 9)) d ON ((a.CPD_TTL_ID = d.cpd_id)))
        LEFT JOIN (SELECT
            tb_comparam_det.CPD_ID AS cpd_id,
                tb_comparam_det.CPD_DESC AS cpd_desc,
                tb_comparam_det.CPM_ID AS cpm_id,
                tb_comparam_det.CPD_VALUE AS cpd_value
        FROM
            tb_comparam_det
        WHERE
            (tb_comparam_det.CPM_ID = 26)) e ON ((a.EMP_GENDER = e.cpd_value))) 
