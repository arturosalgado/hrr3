package com.hrr3.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;

public class ClientInfoController extends GenericForwardComposer {
	private static final long serialVersionUID = 1L;
	
	public void onClientInfo$rootContainer(ClientInfoEvent event) {
        
		int windowWidth = event.getDesktopWidth();
		windowWidth = windowWidth - 100;
    }
	
	@Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
    }
	
}
