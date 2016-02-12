package com.hrr3.controller;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.impl.InputElement;
import org.zkoss.zul.impl.XulElement;

public class ListboxArrowKeyController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Wire
	protected Listbox genericListBox;
	
	private int headerCountElements = 0;
	private List<XulElement> lstComponents;
	
	public ListboxArrowKeyController(int headerCount) {
		
		headerCountElements = headerCount;
	}
	
	private void sortElements(Component mainComp) {
		lstComponents = new ArrayList<XulElement>();
		List<Listitem> rows = ((Listbox)mainComp).getItems();
		
		for (Component row : rows) {
			for (Component element : row.getChildren()) {
				Component cp = ((Listcell)element).getFirstChild();
				if (cp instanceof InputElement)
					lstComponents.add((InputElement)cp);			
			}	
		}	
	}

	public void nextElement(InputElement elem, boolean isDown) {
		int index = 0;
		int plusIndex = 1;
		int rows= ((Listcell)elem.getParent()).getListbox().getItemCount() - headerCountElements;

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
		int rows= ((Listcell)elem.getParent()).getListbox().getItemCount() - headerCountElements;

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
	
	@Listen("onCtrlKey=#genericListBox; onOK=#genericListBox")
	public void doArrowManagement(KeyEvent event) {
		
		// detect current selected element	
		if(event.getKeyCode() == 39 && event.getReference() !=null) //RIGHT
			this.nextElement((InputElement)event.getReference(), false);
		else if(event.getKeyCode() == 37 && event.getReference() !=null) //EFT
			this.previousElement((InputElement)event.getReference(), false);
		else if(event.getKeyCode() == 13 && event.getReference() !=null) //ENTER
			this.nextElement((InputElement)event.getReference(), false);
		else if(event.getKeyCode() == 38 && event.getReference() !=null) //UP
			this.previousElement((InputElement)event.getReference(), true);
		else if(event.getKeyCode() == 40 && event.getReference() !=null) //DOWN
			this.nextElement((InputElement)event.getReference(), true);
		//Always assign the lstComponents after any onChange event caused in the SSRInputRender
		lstComponents = (List<XulElement>)this.genericListBox.getAttribute("lstComponents");
	}
	
	@Listen("onAfterRender=#genericListBox")
	public void retrieveFellows() {
		this.sortElements(this.genericListBox);
		this.genericListBox.setAttribute("lstComponents", this.lstComponents);
	}

}
