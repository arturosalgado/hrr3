package com.hrr3.controller;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.impl.InputElement;
import org.zkoss.zul.impl.XulElement;

public class GridArrowKeyController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Wire
	Grid genericGrid;
	

	private List<XulElement> lstComponents;
	
	private void sortElements(Component mainComp) {
		lstComponents = new ArrayList<XulElement>();
		List<Component> rows = mainComp.getChildren();
		
		for (Component row : rows) {
			for (Component element : ((Row)row).getChildren()) {
				if (element instanceof InputElement && element.isVisible())
					lstComponents.add((InputElement) element);			
			}	
		}	
	}

	public void nextElement(InputElement elem, boolean isDown) {
		int index = 0;
		int plusIndex = 1;
		Rows rowsObj = genericGrid.getRows();
		int rows = rowsObj.getChildren().size();

		for (Object element : lstComponents) {
			if (elem == element)
				break;
			index++;
		}
		
		if(isDown)
			plusIndex = lstComponents.size()/rows;
		
		if (index + plusIndex < lstComponents.size()) {
			if (lstComponents.get(index + plusIndex) instanceof InputElement) {
				((InputElement) lstComponents.get(index + plusIndex)).setFocus(true);	
				((InputElement) lstComponents.get(index + plusIndex)).select();
			}
						
		}
	}
	
	public void previousElement(InputElement elem, boolean isUp) {
		int index = 0;
		int plusIndex = 1;
		Rows rowsObj = genericGrid.getRows();
		int rows = rowsObj.getChildren().size();

		for (Object element : lstComponents) {
			if (elem == element)
				break;
			index++;
		}
		
		if(isUp)
			plusIndex = lstComponents.size()/rows;
		
		if (index - plusIndex < lstComponents.size() && index -plusIndex >= 0) {
			if (lstComponents.get(index - plusIndex) instanceof InputElement) {
				((InputElement) lstComponents.get(index - plusIndex)).setFocus(true);	
				((InputElement) lstComponents.get(index - plusIndex)).select();
			}
						
		}
	}	
	
	@Listen("onCtrlKey=#genericGrid; onOK=#genericGrid")
	public void doArrowManagement(KeyEvent event) {
		
		int ci = 0;
		InputElement ie = null;
		
		if(event.getReference() == null) {
			ci = (int)this.genericGrid.getAttribute("currentIndex");
			ie = (InputElement)this.lstComponents.get(ci);
			
		}
			
		else
			ie = (InputElement)event.getReference();
		
		// detect current selected element	
		if(event.getKeyCode() == 39 && ie !=null) //RIGHT
			this.nextElement(ie, false);
		else if(event.getKeyCode() == 37 && ie !=null) //EFT
			this.previousElement(ie, false);
		else if(event.getKeyCode() == 13 && ie !=null) //ENTER
			this.nextElement(ie, false);
		else if(event.getKeyCode() == 38 && ie !=null) //UP
			this.previousElement(ie, true);
		else if(event.getKeyCode() == 40 && ie !=null) //DOWN
			this.nextElement(ie, true);
		//Always assign the lstComponents after any onChange event caused in the SSRInputRender
		lstComponents = (List<XulElement>)this.genericGrid.getAttribute("lstComponents");
	}
	
	@Listen("onAfterRender=#genericGrid")
	public void retrieveFellows() {
		this.sortElements(this.genericGrid.getRows());
		this.genericGrid.setAttribute("lstComponents", this.lstComponents);
	}

}
