
package com.abm.mainet.socialsecurityl.ui.validator;
import org.springframework.stereotype.Component;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.socialsecurity.ui.model.RenewalFormModel;

/**
 * @author priti.singh
 *
 */
@Component
public class RenewalFormValidator extends BaseEntityValidator<RenewalFormModel> {
    
    final ApplicationSession session = ApplicationSession.getInstance();

    @Override
    protected void performValidations(RenewalFormModel model,
            EntityValidationContext<RenewalFormModel> entityValidationContext) {

        if ((model.getRenewalFormDto().getUploaddoc() != null) && !model.getRenewalFormDto().getUploaddoc().isEmpty()) {
            for (final DocumentDetailsVO doc : model.getRenewalFormDto().getUploaddoc()) {
                if (doc.getDocumentByteCode() == null) {
                    if (doc.getDocumentByteCode() == null) {
                        entityValidationContext.addOptionConstraint(session.getMessage("social.sec.mandtory.docs"));
                        break;
                    }
                }
            }
        }
    }

   


}
