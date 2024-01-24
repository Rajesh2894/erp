package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.Transient;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.FileStorageCache;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class BaseEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 8659093451302009149L;

    private static AtomicLong tempIdSequence = new AtomicLong();

    /**
     * This temporary variable is use for maintaining temporary value for the current record.
     * <p>
     * The value of the variable provided by {@link AtomicLong} which give synchronized next long value, so more concurrency while
     * working with multiple user.
     * </p>
     * <p>
     * So each use will get <strong>unique</strong> value for each record in the any list data.
     * </p>
     */
    @Transient
    private long tempId;

    /**
     * This temporary variable for deleting record from list.
     * <p>
     * If new record is added and very soon deleted the same record then the flag is set as <code>true</code>, so there is no need
     * to iterate the record in the list.
     * </p>
     * The benefit is to avoid any iteration and remove from the list.
     */
    @Transient
    private boolean deleted;

    @Transient
    private String[] pkValues;

    @JsonIgnore
    @Transient
    private FileStorageCache fileStorageCache;

    private UserSession getUserSession() {
        return UserSession.getCurrent();
    }

    public abstract long getId();

    public String[] getPkValues() {
        return new String[] {};
    }

    /**
     * @return the orgId
     */
    public abstract Organisation getOrgId();

    /**
     * @param orgId the orgId to set
     */
    public abstract void setOrgId(Organisation orgId);

    /**
     * @return the userId
     */
    public abstract Employee getUserId();

    /**
     * @param userId the userId to set
     */
    public abstract void setUserId(Employee userId);

    /**
     * @return
     */
    public abstract Employee getUpdatedBy();

    /**
     * @param updatedBy
     */
    public abstract void setUpdatedBy(Employee updatedBy);

    /**
     * @return the langId
     */
    public abstract int getLangId();

    /**
     * @param langId the langId to set
     */
    public abstract void setLangId(int langId);

    /**
     * @return the lmodDate
     */
    public abstract Date getLmodDate();

    /**
     * @param lmodDate the lmodDate to set
     */
    public abstract void setLmodDate(Date lmodDate);

    /**
     * @return lgIpMac
     */
    public abstract String getLgIpMac();

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public abstract void setLgIpMac(String lgIpMac);

    /**
     * @return the updatedDate
     */
    public abstract Date getUpdatedDate();

    /**
     * @param updatedDate the updatedDate to set
     */
    public abstract void setUpdatedDate(Date updatedDate);

    /**
     * @return the lgIpMacUpd
     */
    public abstract String getLgIpMacUpd();

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public abstract void setLgIpMacUpd(String lgIpMacUpd);

    /**
     * @return the rowId
     */
    public long getRowId() {
        final long id = getId();

        if (id != 0) {
            setTempId(id);

        }

        if (getTempId() == 0) {
            tempId = tempIdSequence.decrementAndGet();

            setTempId(tempId);
        }

        return getTempId();
    }

    private void setTempId(final long tempId) {
        this.tempId = tempId;
    }

    public long getTempId() {
        return tempId;
    }

    /**
     * @return the isDeleted
     */
    public abstract String getIsDeleted();

    /**
     * @param isDeleted the isDeleted to set
     */
    public abstract void setIsDeleted(String isDeleted);

    public void updateAuditFields() {
        if (getId() == 0) {
            setCreateAuditDetails();
        } else {
            setUpdateAuditDetails();
        }
    }

    private void setCreateAuditDetails() {
        setLmodDate(new Date());

        setLgIpMac(getUserSession().getEmployee().getEmppiservername());
        setOrgId(getUserSession().getOrganisation());
        setUserId(getUserSession().getEmployee());
        setLangId(getUserSession().getLanguageId());
    }

    private void setUpdateAuditDetails() {
        setUpdatedBy(getUserSession().getEmployee());
        setUpdatedDate(new Date());
        setLgIpMacUpd(getUserSession().getEmployee().getEmppiservername());
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone() {
        Object object;
        try {
            object = super.clone();

            if ((object != null) && (object instanceof BaseEntity)) {
                return object;
            } else {
                throw new FrameworkException(MainetConstants.OBJECT_NOTSUPPORT_CLONING);
            }
        } catch (final CloneNotSupportedException ex) {

            throw new FrameworkException(MainetConstants.OBJECT_NOTSUPPORT_CLONING, ex);
        }
    }

    /**
     * @return the activeStatus
     */
    public String getActiveStatus() {
        return getIsDeleted();
    }

    /**
     * @return the editFlag
     */
    public long getEditFlag() {
        return getRowId();
    }

    public long getViewMode() {
        return getRowId();
    }

    /**
     * @return the deleteFlag
     */
    public long getDeleteFlag() {
        return getRowId();
    }

    private ApplicationSession getAppSession() {
        return ApplicationContextProvider.getApplicationContext().getBean(ApplicationSession.class);
    }

    public List<LookUp> getLookUps(final String lookUpCode, final Organisation organisation) {
        return getAppSession().getHierarchicalLookUp(organisation, lookUpCode).get(lookUpCode);
    }

    /**
     * To get {@link FileStorageCache} object for storing uploaded file(s) of given particular record.
     * @return {@link FileStorageCache} object containing uploaded file information.
     */
    private FileStorageCache getFileCache() {
        if (fileStorageCache == null) {
            fileStorageCache = FileStorageCache.getInstance();
        }

        return fileStorageCache;
    }

    public FileStorageCache getFileStorageCache() {
        if (fileStorageCache == null) {
            fileStorageCache = getFileCache();
        }

        return fileStorageCache;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(final boolean deleted) {
        this.deleted = deleted;
    }

    public void setFileStorageCache(final FileStorageCache fileStorageCache) {
        this.fileStorageCache = fileStorageCache;
    }
}
