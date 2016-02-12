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
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Rows;
import org.zkoss.zul.impl.InputElement;
import org.zkoss.zul.impl.XulElement;

import com.hrr3.entity.ssr.SSRInputData;

public class GridArrowKeyRender {


	List<XulElement> lstComponents = null;
	
	public void sortElements(Component mainComp) {
		lstComponents = new ArrayList<XulElement>();
		List<Component> rows = mainComp.getChildren();
		
		for (Component row : rows) {
			for (Component element : ((Row)row).getChildren()) {
				if (element instanceof InputElement)
					lstComponents.add((InputElement) element);			
			}	
		}
	}	
	
	public void nextElement(InputElement elem) {
		int index = 0;

		for (Object element : lstComponents) {
			if (elem == element)
				break;
			index++;
		}
		if (index + 1 < lstComponents.size()) {
			if (lstComponents.get(index + 1) instanceof InputElement){
				((InputElement) lstComponents.get(index + 1)).setFocus(true);	
				((InputElement) lstComponents.get(index + 1)).select();
			}
		}
	}
	
	
	
	public void nextElement(InputElement elem, boolean isDown) {
		int index = 0;
		int plusIndex = 1;
		Rows rowsObj = (Rows)(elem.getParent().getParent());
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
