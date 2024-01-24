
package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author dharmendra.chouhan
 *
 */
public class AccountVoucherCommPostingMasterDto implements Serializable {

    private static final long serialVersionUID = -1976046266327293495L;

    private Long orgid;
    private Date vou_date;
    private Date vou_posting_date;
    private String vou_type_cpd_id_value;
    private String vou_subtype_cpd_id_value;
    private String dp_deptid_code;
    private String vou_reference_no;
    private Date vou_reference_no_date;
    private String narration;
    private String chequeno;
    private Date chequedate;;
    private String drawnon;
    private String payer_payee;
    private Long created_by;
    private Long lang_id;
    @JsonIgnore
    @Size(max = 100)
    private String lg_ip_mac;
    private String autho_flg;
    private int entry_type;
    private Long fieldId;
    private List<AccountVoucherCommPostingDetailDto> accountVoucherCommPostingDetailDto = new ArrayList<>();

    public AccountVoucherCommPostingMasterDto() {
        super();
    }

    /**
     * @param orgid
     * @param vou_date
     * @param vou_posting_date
     * @param vou_type_cpd_id_value
     * @param vou_subtype_cpd_id_value
     * @param dp_deptid_code
     * @param vou_reference_no
     * @param vou_reference_no_date
     * @param narration
     * @param chequeno
     * @param chequedate
     * @param drawnon
     * @param payer_payee
     * @param created_by
     * @param lang_id
     * @param lg_ip_mac
     * @param autho_flg
     * @param entry_type
     */
    public AccountVoucherCommPostingMasterDto(final Long orgid, final Date vou_date,
            final Date vou_posting_date, final String vou_type_cpd_id_value,
            final String vou_subtype_cpd_id_value, final String dp_deptid_code,
            final String vou_reference_no, final Date vou_reference_no_date,
            final String narration, final String chequeno, final Date chequedate, final String drawnon,
            final String payer_payee, final Long created_by, final Long lang_id,
            final String lg_ip_mac, final String autho_flg, final int entry_type, final Long fieldId,
            final List<AccountVoucherCommPostingDetailDto> accountVoucherCommPostingDetailDto) {
        super();
        this.orgid = orgid;
        this.vou_date = vou_date;
        this.vou_posting_date = vou_posting_date;
        this.vou_type_cpd_id_value = vou_type_cpd_id_value;
        this.vou_subtype_cpd_id_value = vou_subtype_cpd_id_value;
        this.dp_deptid_code = dp_deptid_code;
        this.vou_reference_no = vou_reference_no;
        this.vou_reference_no_date = vou_reference_no_date;
        this.narration = narration;
        this.chequeno = chequeno;
        this.chequedate = chequedate;
        this.drawnon = drawnon;
        this.payer_payee = payer_payee;
        this.created_by = created_by;
        this.lang_id = lang_id;
        this.lg_ip_mac = lg_ip_mac;
        this.autho_flg = autho_flg;
        this.entry_type = entry_type;
        this.fieldId = fieldId;
        this.accountVoucherCommPostingDetailDto = accountVoucherCommPostingDetailDto;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Date getVou_date() {
        return vou_date;
    }

    public void setVou_date(final Date vou_date) {
        this.vou_date = vou_date;
    }

    public Date getVou_posting_date() {
        return vou_posting_date;
    }

    public void setVou_posting_date(final Date vou_posting_date) {
        this.vou_posting_date = vou_posting_date;
    }

    public String getVou_type_cpd_id_value() {
        return vou_type_cpd_id_value;
    }

    public void setVou_type_cpd_id_value(final String vou_type_cpd_id_value) {
        this.vou_type_cpd_id_value = vou_type_cpd_id_value;
    }

    public String getVou_subtype_cpd_id_value() {
        return vou_subtype_cpd_id_value;
    }

    public void setVou_subtype_cpd_id_value(final String vou_subtype_cpd_id_value) {
        this.vou_subtype_cpd_id_value = vou_subtype_cpd_id_value;
    }

    public String getDp_deptid_code() {
        return dp_deptid_code;
    }

    public void setDp_deptid_code(final String dp_deptid_code) {
        this.dp_deptid_code = dp_deptid_code;
    }

    public String getVou_reference_no() {
        return vou_reference_no;
    }

    public void setVou_reference_no(final String vou_reference_no) {
        this.vou_reference_no = vou_reference_no;
    }

    /**
     * @return the vou_reference_no_date
     */
    public Date getVou_reference_no_date() {
        return vou_reference_no_date;
    }

    /**
     * @param vou_reference_no_date the vou_reference_no_date to set
     */
    public void setVou_reference_no_date(final Date vou_reference_no_date) {
        this.vou_reference_no_date = vou_reference_no_date;
    }

    /**
     * @return the narration
     */
    public String getNarration() {
        return narration;
    }

    /**
     * @param narration the narration to set
     */
    public void setNarration(final String narration) {
        this.narration = narration;
    }

    /**
     * @return the chequeno
     */
    public String getChequeno() {
        return chequeno;
    }

    /**
     * @param chequeno the chequeno to set
     */
    public void setChequeno(final String chequeno) {
        this.chequeno = chequeno;
    }

    /**
     * @return the chequedate
     */
    public Date getChequedate() {
        return chequedate;
    }

    /**
     * @param chequedate the chequedate to set
     */
    public void setChequedate(final Date chequedate) {
        this.chequedate = chequedate;
    }

    /**
     * @return the drawnon
     */
    public String getDrawnon() {
        return drawnon;
    }

    /**
     * @param drawnon the drawnon to set
     */
    public void setDrawnon(final String drawnon) {
        this.drawnon = drawnon;
    }

    /**
     * @return the payer_payee
     */
    public String getPayer_payee() {
        return payer_payee;
    }

    /**
     * @param payer_payee the payer_payee to set
     */
    public void setPayer_payee(final String payer_payee) {
        this.payer_payee = payer_payee;
    }

    /**
     * @return the created_by
     */
    public Long getCreated_by() {
        return created_by;
    }

    /**
     * @param created_by the created_by to set
     */
    public void setCreated_by(final Long created_by) {
        this.created_by = created_by;
    }

    /**
     * @return the lang_id
     */
    public Long getLang_id() {
        return lang_id;
    }

    /**
     * @param lang_id the lang_id to set
     */
    public void setLang_id(final Long lang_id) {
        this.lang_id = lang_id;
    }

    /**
     * @return the lg_ip_mac
     */
    public String getLg_ip_mac() {
        return lg_ip_mac;
    }

    /**
     * @param lg_ip_mac the lg_ip_mac to set
     */
    public void setLg_ip_mac(final String lg_ip_mac) {
        this.lg_ip_mac = lg_ip_mac;
    }

    /**
     * @return the autho_flg
     */
    public String getAutho_flg() {
        return autho_flg;
    }

    /**
     * @param autho_flg the autho_flg to set
     */
    public void setAutho_flg(final String autho_flg) {
        this.autho_flg = autho_flg;
    }

    /**
     * @return the entry_type
     */
    public int getEntry_type() {
        return entry_type;
    }

    /**
     * @param entry_type the entry_type to set
     */
    public void setEntry_type(final int entry_type) {
        this.entry_type = entry_type;
    }

    /**
     * @return the accountVoucherCommPostingDetailDto
     */
    public List<AccountVoucherCommPostingDetailDto> getAccountVoucherCommPostingDetailDto() {
        return accountVoucherCommPostingDetailDto;
    }

    /**
     * @param accountVoucherCommPostingDetailDto the accountVoucherCommPostingDetailDto to set
     */
    public void setAccountVoucherCommPostingDetailDto(
            final List<AccountVoucherCommPostingDetailDto> accountVoucherCommPostingDetailDto) {
        this.accountVoucherCommPostingDetailDto = accountVoucherCommPostingDetailDto;
    }

    /**
     * @return the fieldId
     */
    public Long getFieldId() {
        return fieldId;
    }

    /**
     * @param fieldId the fieldId to set
     */
    public void setFieldId(final Long fieldId) {
        this.fieldId = fieldId;
    }

}
