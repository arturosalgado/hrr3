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
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Rows;
import org.zkoss.zul.impl.InputElement;
import org.zkoss.zul.impl.XulElement;

import com.hrr3.entity.ssr.SSRInputData;

public class ListboxArrowKeyRender {


	public List<XulElement> lstComponents = null;
	private int headerCountElements = 0;
	
	public ListboxArrowKeyRender(int headerCount) {
		
		headerCountElements = headerCount;
	}
	
	public void sortElements(Component mainComp) {
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
	
	
	public int getCurrentIndex(InputElement elem) {
		int index = 0;

		for (Object element : lstComponents) {
			if (elem == element)
				return index;
			else
				index++;
		}
		return 0;
	}
	
}
