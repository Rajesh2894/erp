CREATE 
VIEW vw_eip_quick_link_menu AS
    SELECT 
        FN_GET_ROWNUM() AS ROW_NUM,
        s.ORGID AS ORGID,
        s.LINK_ID AS LINKID,
        CONCAT('M',
                CAST(s.LINK_ID AS CHAR CHARSET UTF8)) AS LINK_ID,
        s.LINK_TITLE_EN AS MENU_NM_EN,
        s.LINK_TITLE_REG AS MENU_NM_REG,
        'M' AS PARENTID,
        'M' AS LINK_TYPE,
        NULL AS HAS_SUB_LINK,
        s.LINK_PATH AS PAGE_URL,
        s.LINK_ID AS SORT_ORDER,
        s.LINK_ORDER AS LINK_ORDER,
        NULL AS SECTION_TYPE,
        NULL AS IMG_LINK_TYPE,
        s.IS_LINK_MODIFY AS IS_LINK_MODIFY,
        s.CHEKER_FLAG AS Cheker
    FROM
        (tb_eip_links_master_hist s
        JOIN tb_eip_links_master d ON (((s.LINK_ID = d.LINK_ID)
            AND (d.ISDELETED = 'N'))))
    WHERE
        ((s.ISDELETED = 'N')
            AND s.LINK_ID_H IN (SELECT 
                MAX(eh.LINK_ID_H)
            FROM
                tb_eip_links_master_hist eh
            WHERE
                ((eh.LINK_ID = s.LINK_ID)
                    AND (eh.ORGID = s.ORGID)
                    AND (COALESCE(eh.CHEKER_FLAG,'Y') = 'Y')))) 
    UNION SELECT 
        FN_GET_ROWNUM() AS ROW_NUM,
        s.ORGID AS ORGID,
        d.SUB_LINK_MAS_ID AS LINKID,
        CONCAT('F',
                CAST(d.SUB_LINK_MAS_ID AS CHAR CHARSET UTF8)) AS LINK_ID,
        d.SUB_LINK_NAME_EN AS MENU_NM_EN,
        d.SUB_LINK_NAME_RG AS MENU_NM_REG,
        (CASE
            WHEN
                ISNULL(d.SUB_LINK_PAR_ID)
            THEN
                CONCAT('M',
                        CAST(d.LINK_ID AS CHAR CHARSET UTF8))
            ELSE CONCAT('F',
                    CAST(d.SUB_LINK_PAR_ID AS CHAR CHARSET UTF8))
        END) AS PARENTID,
        'F' AS LINK_TYPE,
        d.HAS_SUB_LINK AS HAS_SUB_LINK,
        d.PAGE_URL AS PAGE_URL,
        (s.LINK_ID OR d.SUB_LINK_ORDER) AS SORT_ORDER,
        s.LINK_ORDER AS LINK_ORDER,
        FN_GETCPDDESC(d.CPD_SECION_TYPE, 'V', d.ORGID) AS SECTION_TYPE,
        FN_GETCPDDESC(d.CPD_IMG_LINK_TYPE,
                'V',
                d.ORGID) AS IMG_LINK_TYPE,
        d.IS_LINK_MODIFY AS IS_LINK_MODIFY,
        s.CHEKER_FLAG AS Cheker
    FROM
        (tb_eip_links_master s
        JOIN tb_eip_sub_links_master_hist d)
    WHERE
        ((s.LINK_ID = d.LINK_ID)
            AND (s.ISDELETED = 'N')
            AND (d.ISDELETED = 'N')
            AND (s.ORGID = d.ORGID)
            AND (s.CHEKER_FLAG = 'Y')
            AND (d.CHEKER_FLAG = 'Y')
            AND d.SUB_LINK_MAS_ID_H IN (SELECT 
                MAX(sh.SUB_LINK_MAS_ID_H)
            FROM
                tb_eip_sub_links_master_hist sh
            WHERE
                ((sh.ORGID = d.ORGID)
                    AND (sh.SUB_LINK_MAS_ID = d.SUB_LINK_MAS_ID)
                    AND (COALESCE(sh.CHEKER_FLAG,'Y') = 'Y'))))
    ORDER BY ROW_NUM , LINK_ORDER , SORT_ORDER