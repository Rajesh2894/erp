package com.abm.mainet.cfc.checklist.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nirmal.mahanta
 *
 */
public class TbCfcChecklistMstDto implements Serializable {

    private static final long serialVersionUID = 734710802262624330L;

    /*
     * private TbCfcChecklistMst cfcChecklistMst; private List<TbCfcChecklistMst> cfcChkList = new ArrayList<>();
     */

    private TbDocumentGroup docGroupMst;
    private List<TbDocumentGroup> docGroupList = new ArrayList<>();

    /*
     * private ChecklistMst checklistMst; private List<ChecklistMst> checklistList = new ArrayList<>();
     */

    /*  *//**
           * @return the cfcChecklistMst
           */
    /*
     * public TbCfcChecklistMst getCfcChecklistMst() { return cfcChecklistMst; }
     *//**
        * @param cfcChecklistMst the cfcChecklistMst to set
        */
    /*
     * public void setCfcChecklistMst(final TbCfcChecklistMst cfcChecklistMst) { this.cfcChecklistMst = cfcChecklistMst; }
     *//**
        * @return the cfcChkList
        *//*
           * public List<TbCfcChecklistMst> getCfcChkList() { return cfcChkList; }
           */

    /**
     * @param cfcChkList the cfcChkList to set
     */
    /*
     * public void setCfcChkList(final List<TbCfcChecklistMst> cfcChkList) { this.cfcChkList = cfcChkList; }
     */

    public TbDocumentGroup getDocGroupMst() {
        return docGroupMst;
    }

    public void setDocGroupMst(final TbDocumentGroup docGroupMst) {
        this.docGroupMst = docGroupMst;
    }

    public List<TbDocumentGroup> getDocGroupList() {
        return docGroupList;
    }

    public void setDocGroupList(final List<TbDocumentGroup> docGroupList) {
        this.docGroupList = docGroupList;
    }
    /*
     * public ChecklistMst getChecklistMst() { return checklistMst; } public List<ChecklistMst> getChecklistList() { return
     * checklistList; } public void setChecklistMst(final ChecklistMst checklistMst) { this.checklistMst = checklistMst; } public
     * void setChecklistList(final List<ChecklistMst> checklistList) { this.checklistList = checklistList; }
     */

}
