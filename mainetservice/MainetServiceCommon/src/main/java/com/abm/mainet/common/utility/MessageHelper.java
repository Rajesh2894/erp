package com.abm.mainet.common.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.common.constant.MainetConstants;

@Component
public class MessageHelper {

    public void addException(final Model uiModel, final String messageKey, final Exception e) {
        final List<Message> messages = getMessages(uiModel);
        addException(messages, messageKey, e);
    }

    public void addException(final RedirectAttributes redirectAttributes, final String messageKey, final Exception e) {
        final List<Message> messages = getMessages(redirectAttributes);
        addException(messages, messageKey, e);
    }

    public void addException(final List<Message> messages, final Exception e) {
        if (e.getCause() == null) {
            messages.add(new Message(MessageType.DANGER, MainetConstants.ERROR, e.getMessage()));
        } else {
            messages.add(new Message(MessageType.DANGER, MainetConstants.ERROR_WITH_CAUSE, e.getMessage(), e.getCause().getMessage()));
        }
    }

    public void addException(final List<Message> messages, final String messageKey, final Exception e) {
        if (e.getCause() == null) {
            messages.add(new Message(MessageType.DANGER, messageKey, e.getMessage()));
        } else {
            messages.add(new Message(MessageType.DANGER, messageKey, e.getMessage(), e.getCause().getMessage()));
        }
    }

    public void addMessage(final RedirectAttributes redirectAttributes, final Message message) {
        final List<Message> messages = getMessages(redirectAttributes);
        messages.add(message);
    }

    public void addMessage(final Model uiModel, final Message message) {
        final List<Message> messages = getMessages(uiModel);
        messages.add(message);
    }

    @SuppressWarnings("unchecked")
    private List<Message> getMessages(final RedirectAttributes redirectAttributes) {
        final Map<String, Object> map = (Map<String, Object>) redirectAttributes.getFlashAttributes();
        List<Message> messages = (List<Message>) map.get(MainetConstants.MESSAGES);
        if (messages == null) {
            messages = new ArrayList<>();
            map.put(MainetConstants.MESSAGES, messages);
        }
        return (List<Message>) map.get(MainetConstants.MESSAGES);
    }

    @SuppressWarnings("unchecked")
    private List<Message> getMessages(final Model uiModel) {
        final Map<String, Object> map = uiModel.asMap();
        List<Message> messages = (List<Message>) map.get(MainetConstants.MESSAGES);
        if (messages == null) {
            messages = new ArrayList<>();
            map.put(MainetConstants.MESSAGES, messages);
        }
        return (List<Message>) map.get(MainetConstants.MESSAGES);
    }
}
