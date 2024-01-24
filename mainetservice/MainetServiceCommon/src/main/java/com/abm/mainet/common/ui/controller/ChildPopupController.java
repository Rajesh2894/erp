package com.abm.mainet.common.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.ui.model.ChildPopupModel;

/**
 * @author Pranit.Mhatre
 * @since 02 December, 2013
 */
public abstract class ChildPopupController<TModel extends ChildPopupModel<? extends BaseEntity, ? extends AbstractEntryFormModel<? extends BaseEntity>>>
        extends AbstractEntryFormController<TModel> {
    @RequestMapping(params = "saveOrUpdate", method = RequestMethod.POST)
    public ModelAndView saveOrUpdateChildForm(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        final TModel model = getModel();

        try {
            final boolean result = model.saveOrUpdateForm();

            if (result) {
                return jsonResult(JsonViewObject.successResult());
            }
        } catch (final Throwable ex) {
            return jsonResult(JsonViewObject.failureResult(ex));
        }

        return defaultResult();
    }

}
