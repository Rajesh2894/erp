/**
 * 
 */
package com.abm.mainet.common.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author anwarul.hassan
 * @since 22-Dec-2020
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class CommonRejectionLetterModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;

}
