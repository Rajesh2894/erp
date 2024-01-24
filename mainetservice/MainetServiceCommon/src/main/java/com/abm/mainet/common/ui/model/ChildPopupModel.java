package com.abm.mainet.common.ui.model;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.context.annotation.Scope;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.ApplicationContextProvider;

/**
 * @author Pranit.Mhatre
 * @since 02 December, 2013
 */
@Scope(value = "session")
public abstract class ChildPopupModel<TEntity extends BaseEntity, TParentModel extends AbstractEntryFormModel<? extends BaseEntity>>
        extends AbstractEntryFormModel<TEntity> {
    private static final long serialVersionUID = -2636173147482957153L;
    private TParentModel parentModel;

    public ChildPopupModel() {
        final Class<TParentModel> parentModelClass = (Class<TParentModel>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[1];
        parentModel = ApplicationContextProvider.getApplicationContext().getBean(parentModelClass);
    }

    private void loadParentClass() {
        final Class<TParentModel> parentModelClass = (Class<TParentModel>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[1];
        this.parentModel = ApplicationContextProvider.getApplicationContext().getBean(parentModelClass);
    }

    public TParentModel getParentModel() {
        return parentModel;
    }

    @Override
    public void addForm() {
        try {
            setEntity(getEntityClass().newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new FrameworkException("Unable to instantiate entity of type " + getEntityClass().getSimpleName());
        }
    }

    @Override
    public void editForm(final long rowId) {
        loadParentClass();

        @SuppressWarnings("unchecked")
        final TEntity entity = (TEntity) findEntity(getChildEntityCollection(), rowId).clone();

        setEntity(entity);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ui.model.AbstractEntryFormModel#delete(long)
     */
    @Override
    public void delete(final long rowId) {
        loadParentClass();

        final List<TEntity> childCollection = getChildEntityCollection();

        for (int i = 0; i < childCollection.size(); ++i) {
            final TEntity entity = childCollection.get(i);

            if (entity.getRowId() == rowId) {
                if (rowId < 0) {
                    childCollection.remove(i);
                } else {
                    entity.setIsDeleted(MainetConstants.IsDeleted.DELETE);
                }

                break;
            }
        }
    }

    /**
     * To add newly created {@link ChargeDetail} object into {@link ChargeMaster}
     * @return {@link Boolean} <code>true</code> if added successfully otherwise <code>false</code>
     * @throws FrameworkException
     */
    private void addChildEntity() throws FrameworkException {
        final TEntity child = getEntity();
        attachParentEntity(child);

        child.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
        getChildEntityCollection().add(child);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ui.model.AbstractFormModel#saveOrUpdateForm(org.springframework .validation.BindingResult)
     */
    @Override
    public final boolean saveOrUpdateForm() {
        final TEntity entity = getEntity();

        onValidate(entity);

        if (hasValidationErrors()) {
            return false;
        }

        if (entity.getTempId() == 0) {
            entity.getRowId();
            addChildEntity();

            entity.getFileStorageCache().setRecordId(entity.getRowId());
        } else {
            replaceEntity(getChildEntityCollection(), getEntity());
        }

        onSaveOrUpdate(getEntity());


        return true;
    }

    protected abstract List<TEntity> getChildEntityCollection();

    protected abstract void attachParentEntity(TEntity child);

    protected abstract void onValidate(TEntity child);

    protected void onSaveOrUpdate(final TEntity child) {
    }
}
