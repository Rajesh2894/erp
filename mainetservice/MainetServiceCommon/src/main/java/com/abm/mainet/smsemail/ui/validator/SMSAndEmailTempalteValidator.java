package com.abm.mainet.smsemail.ui.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.smsemail.domain.SMSAndEmailInterface;

@Component
public class SMSAndEmailTempalteValidator extends BaseEntityValidator<SMSAndEmailInterface> {

	@Override
	protected void performValidations(final SMSAndEmailInterface entity,
			final EntityValidationContext<SMSAndEmailInterface> validationContext) {

		if ((entity.getDpDeptid() == null) || (entity.getDpDeptid().getDpDeptid() == 0)) {
			validationContext.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.department"));
		}
		if ((entity.getServiceId() != null) && (entity.getServiceId().getSmServiceId() != null)
				&& (entity.getServiceId().getSmServiceId() == 0)) {
			validationContext.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.service"));
		}
		if ((entity.getSmfid() == null) || (entity.getSmfid().getSmfid() == 0)) {
			validationContext.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.event"));
		}

		if ((entity.getSmsAndmailTemplate().getMessageType() == null)
				|| entity.getSmsAndmailTemplate().getMessageType().equals(MainetConstants.BLANK)) {
			validationContext.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.template"));
		}
		if ((entity.getAlertType() == null) || entity.getAlertType().equalsIgnoreCase(MainetConstants.BLANK)) {
			validationContext.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.alert"));
		}
		if ((entity.getAlertType() != null) && !entity.getAlertType().equalsIgnoreCase(MainetConstants.BLANK)) {
			if (entity.getAlertType().equalsIgnoreCase(MainetConstants.MENU.S)) {

				final String check = ApplicationSession.getInstance().getMessage("unicode");
				if (MainetConstants.NOL.equals(check)) {

					if ((entity.getSmsAndmailTemplate().getSmsBody() == null)
							|| entity.getSmsAndmailTemplate().getSmsBody().trim().isEmpty()) {
						validationContext
								.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.smsbody1"));
					} else {
						if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX)
								.matcher(entity.getSmsAndmailTemplate().getSmsBody()).matches()) {
							validationContext
									.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.smsbody1en"));
						} else if ((entity.getSmsAndmailTemplate().getSmsBody()
								.replaceAll("\\W+", MainetConstants.BLANK).trim()).isEmpty()) {
							validationContext
									.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.smsbody1"));
						}
					}
					if ((entity.getSmsAndmailTemplate().getSmsBodyReg() == null)
							|| entity.getSmsAndmailTemplate().getSmsBodyReg().trim().isEmpty()) {
						validationContext
								.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.smsbody2"));
					} else {
						if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX)
								.matcher(entity.getSmsAndmailTemplate().getSmsBodyReg()).matches()) {
							validationContext
									.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.smsbody2mar"));
						} else if ((entity.getSmsAndmailTemplate().getSmsBodyReg()
								.replaceAll("\\W+", MainetConstants.BLANK).trim()).isEmpty()) {
							validationContext
									.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.smsbody2"));
						}
					}

				} else {
					if ((entity.getSmsAndmailTemplate().getSmsBody() == null)
							|| entity.getSmsAndmailTemplate().getSmsBody().trim().isEmpty()) {
						validationContext
								.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.smsbody1"));
					}
					if ((entity.getSmsAndmailTemplate().getSmsBodyReg() == null)
							|| entity.getSmsAndmailTemplate().getSmsBodyReg().trim().isEmpty()) {
						validationContext
								.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.smsbody2"));
					}
				}
			} else if (entity.getAlertType().equalsIgnoreCase(MainetConstants.MENU.E)) {
				final String check = ApplicationSession.getInstance().getMessage("unicode");
				if ("No".equals(check)) {
					if ((entity.getSmsAndmailTemplate().getMailBody() == null)
							|| entity.getSmsAndmailTemplate().getMailBody().trim().isEmpty()) {
						validationContext
								.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailbody1"));
					} else {
						if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX)
								.matcher(entity.getSmsAndmailTemplate().getMailBody()).matches()) {
							validationContext
									.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailbody1en"));
						} else if ((entity.getSmsAndmailTemplate().getMailBody()
								.replaceAll("\\W+", MainetConstants.BLANK).trim()).isEmpty()) {
							validationContext
									.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailbody1"));
						}
					}
					if ((entity.getSmsAndmailTemplate().getMailSub() == null)
							|| entity.getSmsAndmailTemplate().getMailSub().trim().isEmpty()) {
						validationContext
								.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailsub1"));
					} else {
						if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX)
								.matcher(entity.getSmsAndmailTemplate().getMailSub()).matches()) {
							validationContext
									.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailsub1en"));
						} else if ((entity.getSmsAndmailTemplate().getMailSub()
								.replaceAll("\\W+", MainetConstants.BLANK).trim()).isEmpty()) {
							validationContext
									.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailsub1"));
						}
					}

					if ((entity.getSmsAndmailTemplate().getMailBodyReg() == null)
							|| entity.getSmsAndmailTemplate().getMailBodyReg().trim().isEmpty()) {
						validationContext
								.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailbody2"));
					} else {
						if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX)
								.matcher(entity.getSmsAndmailTemplate().getMailBodyReg()).matches()) {
							validationContext.addOptionConstraint(
									getApplicationSession().getMessage("eipsmsemail.mailbody2mar"));
						} else if ((entity.getSmsAndmailTemplate().getMailBodyReg()
								.replaceAll("\\W+", MainetConstants.BLANK).trim()).isEmpty()) {
							validationContext
									.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailbody2"));
						}
					}
					if ((entity.getSmsAndmailTemplate().getMailSubReg() == null)
							|| entity.getSmsAndmailTemplate().getMailSubReg().trim().isEmpty()) {
						validationContext
								.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailsub2"));
					} else {
						if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX)
								.matcher(entity.getSmsAndmailTemplate().getMailSubReg()).matches()) {
							validationContext
									.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailsub2mar"));
						} else if ((entity.getSmsAndmailTemplate().getMailSubReg()
								.replaceAll("\\W+", MainetConstants.BLANK).trim()).isEmpty()) {
							validationContext
									.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailsub2"));
						}
					}
				} else {
					if ((entity.getSmsAndmailTemplate().getMailBody() == null)
							|| entity.getSmsAndmailTemplate().getMailBody().trim().equals(MainetConstants.BLANK)) {
						validationContext.addOptionConstraint("eipsmsemail.mailbody1");
					}
					if ((entity.getSmsAndmailTemplate().getMailSub() == null)
							|| entity.getSmsAndmailTemplate().getMailSub().trim().equals(MainetConstants.BLANK)) {
						validationContext.addOptionConstraint("eipsmsemail.mailsub1");
					}
					if ((entity.getSmsAndmailTemplate().getMailBodyReg() == null)
							|| entity.getSmsAndmailTemplate().getMailBodyReg().trim().equals(MainetConstants.BLANK)) {
						validationContext.addOptionConstraint("eipsmsemail.mailbody2");
					}
					if ((entity.getSmsAndmailTemplate().getMailSubReg() == null)
							|| entity.getSmsAndmailTemplate().getMailSubReg().trim().equals(MainetConstants.BLANK)) {
						validationContext.addOptionConstraint("eipsmsemail.mailsub2");
					}
				}
			}

			else if (entity.getAlertType().equalsIgnoreCase(MainetConstants.MENU.B)) {

				final String check = ApplicationSession.getInstance().getMessage("unicode");
				if ("No".equals(check)) {
					if ((entity.getSmsAndmailTemplate().getSmsBody() == null)
							|| entity.getSmsAndmailTemplate().getSmsBody().trim().isEmpty()) {
						validationContext
								.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.smsbody1"));
					} else {
						if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX)
								.matcher(entity.getSmsAndmailTemplate().getSmsBody()).matches()) {
							validationContext
									.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.smsbody1en"));
						} else if ((entity.getSmsAndmailTemplate().getSmsBody()
								.replaceAll("\\W+", MainetConstants.BLANK).trim()).isEmpty()) {
							validationContext
									.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.smsbody1"));
						}
					}
					if ((entity.getSmsAndmailTemplate().getMailBody() == null)
							|| entity.getSmsAndmailTemplate().getMailBody().trim().isEmpty()) {
						validationContext
								.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailbody1"));
					} else {
						if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX)
								.matcher(entity.getSmsAndmailTemplate().getMailBody()).matches()) {
							validationContext
									.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailbody1en"));
						} else if ((entity.getSmsAndmailTemplate().getMailBody()
								.replaceAll("\\W+", MainetConstants.BLANK).trim()).isEmpty()) {
							validationContext
									.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailbody1"));
						}
					}
					if ((entity.getSmsAndmailTemplate().getMailSub() == null)
							|| entity.getSmsAndmailTemplate().getMailSub().trim().isEmpty()) {
						validationContext
								.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailsub1"));
					} else {
						if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX)
								.matcher(entity.getSmsAndmailTemplate().getMailSub()).matches()) {
							validationContext
									.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailsub1en"));
						} else if ((entity.getSmsAndmailTemplate().getMailSub()
								.replaceAll("\\W+", MainetConstants.BLANK).trim()).isEmpty()) {
							validationContext
									.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailsub1"));
						}
					}
					if ((entity.getSmsAndmailTemplate().getSmsBodyReg() == null)
							|| entity.getSmsAndmailTemplate().getSmsBodyReg().trim().isEmpty()) {
						validationContext
								.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.smsbody2"));
					} else {
						if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX)
								.matcher(entity.getSmsAndmailTemplate().getSmsBodyReg()).matches()) {
							validationContext
									.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.smsbody2mar"));
						} else if ((entity.getSmsAndmailTemplate().getSmsBodyReg()
								.replaceAll("\\W+", MainetConstants.BLANK).trim()).isEmpty()) {
							validationContext
									.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.smsbody2"));
						}
					}
					if ((entity.getSmsAndmailTemplate().getMailBodyReg() == null)
							|| entity.getSmsAndmailTemplate().getMailBodyReg().trim().isEmpty()) {
						validationContext
								.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailbody2"));
					} else {
						if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX)
								.matcher(entity.getSmsAndmailTemplate().getMailBodyReg()).matches()) {
							validationContext.addOptionConstraint(
									getApplicationSession().getMessage("eipsmsemail.mailbody2mar"));
						} else if ((entity.getSmsAndmailTemplate().getMailBodyReg()
								.replaceAll("\\W+", MainetConstants.BLANK).trim()).isEmpty()) {
							validationContext
									.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailbody2"));
						}
					}
					if ((entity.getSmsAndmailTemplate().getMailSubReg() == null)
							|| entity.getSmsAndmailTemplate().getMailSubReg().trim().isEmpty()) {
						validationContext
								.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailsub2"));
					} else {
						if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX)
								.matcher(entity.getSmsAndmailTemplate().getMailSubReg()).matches()) {
							validationContext
									.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailsub2mar"));
						} else if ((entity.getSmsAndmailTemplate().getMailSubReg()
								.replaceAll("\\W+", MainetConstants.BLANK).trim()).isEmpty()) {
							validationContext
									.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailsub2"));
						}
					}
				} else {
					if ((entity.getSmsAndmailTemplate().getSmsBody() == null)
							|| entity.getSmsAndmailTemplate().getSmsBody().trim().isEmpty()) {
						validationContext
								.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.smsbody1"));
					}
					if ((entity.getSmsAndmailTemplate().getSmsBodyReg() == null)
							|| entity.getSmsAndmailTemplate().getSmsBodyReg().trim().isEmpty()) {
						validationContext
								.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.smsbody2"));
					}

					if ((entity.getSmsAndmailTemplate().getMailBody() == null)
							|| entity.getSmsAndmailTemplate().getMailBody().trim().equals(MainetConstants.BLANK)) {
						validationContext
								.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailbody1"));
					}
					if ((entity.getSmsAndmailTemplate().getMailSub() == null)
							|| entity.getSmsAndmailTemplate().getMailSub().trim().equals(MainetConstants.BLANK)) {
						validationContext
								.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailsub1"));
					}
					if ((entity.getSmsAndmailTemplate().getMailBodyReg() == null)
							|| entity.getSmsAndmailTemplate().getMailBodyReg().trim().equals(MainetConstants.BLANK)) {
						validationContext
								.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailbody2"));
					}
					if ((entity.getSmsAndmailTemplate().getMailSubReg() == null)
							|| entity.getSmsAndmailTemplate().getMailSubReg().trim().equals(MainetConstants.BLANK)) {
						validationContext
								.addOptionConstraint(getApplicationSession().getMessage("eipsmsemail.mailsub2"));
					}

				}
			}

		}

	}
}