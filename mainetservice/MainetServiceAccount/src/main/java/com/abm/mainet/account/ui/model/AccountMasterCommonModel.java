package com.abm.mainet.account.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.integration.acccount.dto.AccountFieldMasterBean;
import com.abm.mainet.common.integration.acccount.dto.AccountFunctionMasterBean;
import com.abm.mainet.common.integration.acccount.dto.AccountFundMasterBean;
import com.abm.mainet.common.integration.acccount.dto.AccountHeadPrimaryAccountCodeMasterBean;

@Component
@Scope("session")
public class AccountMasterCommonModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private HashMap<String, HashSet<String>> map = new HashMap<>();

    private HashMap<String, HashSet<String>> mapWithDesc = new HashMap<>();

    // for Accounnt Head Primary Master
    private AccountHeadPrimaryAccountCodeMasterBean primaryCodeMasterBean = new AccountHeadPrimaryAccountCodeMasterBean();

    private AccountHeadPrimaryAccountCodeMasterBean primaryCodeMasterBeanForRemoveRecords = new AccountHeadPrimaryAccountCodeMasterBean();

    // for Function Master
    private AccountFunctionMasterBean functionMasterBean = new AccountFunctionMasterBean();

    private AccountFunctionMasterBean functionMasterBeanForRemoveRecords = new AccountFunctionMasterBean();

    // for Field Master
    private AccountFieldMasterBean fieldMasterBean = new AccountFieldMasterBean();

    private AccountFieldMasterBean fieldMasterBeanForRemoveRecords = new AccountFieldMasterBean();

    // for Fund Master
    private AccountFundMasterBean fundMasterBean = new AccountFundMasterBean();

    private AccountFundMasterBean fundMasterBeanForRemoveRecords = new AccountFundMasterBean();

    private List<String> storeCompositeCodeList = new ArrayList<>();

    private String mode;

    /**
     * @return the primaryCodeMasterBean
     */
    public AccountHeadPrimaryAccountCodeMasterBean getPrimaryCodeMasterBean() {
        return primaryCodeMasterBean;
    }

    /**
     * @param primaryCodeMasterBean the primaryCodeMasterBean to set
     */
    public void setPrimaryCodeMasterBean(
            final AccountHeadPrimaryAccountCodeMasterBean primaryCodeMasterBean) {
        this.primaryCodeMasterBean = primaryCodeMasterBean;
    }

    /**
     * @return the map
     */
    public HashMap<String, HashSet<String>> getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap(final HashMap<String, HashSet<String>> map) {
        this.map = map;
    }

    /**
     * @return the primaryCodeMasterBeanForRemoveRecords
     */
    public AccountHeadPrimaryAccountCodeMasterBean getPrimaryCodeMasterBeanForRemoveRecords() {
        return primaryCodeMasterBeanForRemoveRecords;
    }

    /**
     * @param primaryCodeMasterBeanForRemoveRecords the primaryCodeMasterBeanForRemoveRecords to set
     */
    public void setPrimaryCodeMasterBeanForRemoveRecords(
            final AccountHeadPrimaryAccountCodeMasterBean primaryCodeMasterBeanForRemoveRecords) {
        this.primaryCodeMasterBeanForRemoveRecords = primaryCodeMasterBeanForRemoveRecords;
    }

    /**
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(final String mode) {
        this.mode = mode;
    }

    /**
     * @return the storeCompositeCodeList
     */
    public List<String> getStoreCompositeCodeList() {
        return storeCompositeCodeList;
    }

    /**
     * @return the functionMasterBean
     */
    public AccountFunctionMasterBean getFunctionMasterBean() {
        return functionMasterBean;
    }

    /**
     * @param functionMasterBean the functionMasterBean to set
     */
    public void setFunctionMasterBean(final AccountFunctionMasterBean functionMasterBean) {
        this.functionMasterBean = functionMasterBean;
    }

    /**
     * @return the functionMasterBeanForRemoveRecords
     */
    public AccountFunctionMasterBean getFunctionMasterBeanForRemoveRecords() {
        return functionMasterBeanForRemoveRecords;
    }

    /**
     * @param functionMasterBeanForRemoveRecords the functionMasterBeanForRemoveRecords to set
     */
    public void setFunctionMasterBeanForRemoveRecords(
            final AccountFunctionMasterBean functionMasterBeanForRemoveRecords) {
        this.functionMasterBeanForRemoveRecords = functionMasterBeanForRemoveRecords;
    }

    /**
     * @return the fieldMasterBean
     */
    public AccountFieldMasterBean getFieldMasterBean() {
        return fieldMasterBean;
    }

    /**
     * @param fieldMasterBean the fieldMasterBean to set
     */
    public void setFieldMasterBean(final AccountFieldMasterBean fieldMasterBean) {
        this.fieldMasterBean = fieldMasterBean;
    }

    /**
     * @return the fieldMasterBeanForRemoveRecords
     */
    public AccountFieldMasterBean getFieldMasterBeanForRemoveRecords() {
        return fieldMasterBeanForRemoveRecords;
    }

    /**
     * @param fieldMasterBeanForRemoveRecords the fieldMasterBeanForRemoveRecords to set
     */
    public void setFieldMasterBeanForRemoveRecords(
            final AccountFieldMasterBean fieldMasterBeanForRemoveRecords) {
        this.fieldMasterBeanForRemoveRecords = fieldMasterBeanForRemoveRecords;
    }

    /**
     * @param storeCompositeCodeList the storeCompositeCodeList to set
     */
    public void setStoreCompositeCodeList(final List<String> storeCompositeCodeList) {
        this.storeCompositeCodeList = storeCompositeCodeList;
    }

    /**
     * @return the fundMasterBean
     */
    public AccountFundMasterBean getFundMasterBean() {
        return fundMasterBean;
    }

    /**
     * @param fundMasterBean the fundMasterBean to set
     */
    public void setFundMasterBean(final AccountFundMasterBean fundMasterBean) {
        this.fundMasterBean = fundMasterBean;
    }

    /**
     * @return the fundMasterBeanForRemoveRecords
     */
    public AccountFundMasterBean getFundMasterBeanForRemoveRecords() {
        return fundMasterBeanForRemoveRecords;
    }

    /**
     * @param fundMasterBeanForRemoveRecords the fundMasterBeanForRemoveRecords to set
     */
    public void setFundMasterBeanForRemoveRecords(
            final AccountFundMasterBean fundMasterBeanForRemoveRecords) {
        this.fundMasterBeanForRemoveRecords = fundMasterBeanForRemoveRecords;
    }

    /**
     * @return the mapWithDesc
     */
    public HashMap<String, HashSet<String>> getMapWithDesc() {
        return mapWithDesc;
    }

    /**
     * @param mapWithDesc the mapWithDesc to set
     */
    public void setMapWithDesc(final HashMap<String, HashSet<String>> mapWithDesc) {
        this.mapWithDesc = mapWithDesc;
    }

}
