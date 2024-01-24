package com.abm.mainet.common.ui.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractSearchFormModel<TEntity extends Serializable> extends AbstractFormModel {
    private static final long serialVersionUID = -7620050348809883460L;

    private List<TEntity> results = Collections.emptyList();

    public List<TEntity> getResults() {
        return results;
    }

    /**
     * @param auditPlans the auditPlans to set
     */
    public final void search(final HttpServletRequest httpServletRequest) {

        bind(httpServletRequest);

        validateModel();

        if (!hasValidationErrors()) {
            results = querySearchResults();
        } else {
            results.clear();
        }
    }

    protected void validateModel() {
    }

    protected abstract List<TEntity> querySearchResults();

    public void emptyGrid() {
        if (this.getResults() != null) {
            this.getResults().clear();
        }
    }

}
