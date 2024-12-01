package com.http.post.view.panel;

public class ProtocolTextPanel extends TextPanel implements HandleProtocolResponse {

    public ProtocolTextPanel(String name, Boolean editable, String layout) {
        super(name, editable, layout);
    }

    @Override
    public void setResponse(String protocolResponse) {
        getTextArea().setText(protocolResponse);
    }
}
