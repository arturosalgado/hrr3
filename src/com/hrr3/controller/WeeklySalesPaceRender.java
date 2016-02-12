package com.hrr3.controller;

import java.util.Calendar;

import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Textbox;

import com.hrr3.entity.WeeklyMtgMinSalesPaces;

public class WeeklySalesPaceRender implements RowRenderer<WeeklyMtgMinSalesPaces>{	
	
/*	<label value="${each.rowTypeTranslated}"/>		
	<textbox readonly="true" value="${each.janOcc}"/>
	<textbox readonly="true" value="${each.febOcc}"/>
	<textbox readonly="true" value="${each.marOcc}"/>
	<textbox readonly="true" value="${each.aprOcc}"/>
	<textbox readonly="true" value="${each.mayOcc}"/>
	<textbox readonly="true" value="${each.junOcc}"/>
	<textbox readonly="true" value="${each.julOcc}"/>
	<textbox readonly="true" value="${each.augOcc}"/>
	<textbox readonly="true" value="${each.sepOcc}"/>
	<textbox readonly="true" value="${each.octOcc}"/>
	<textbox readonly="true" value="${each.novOcc}"/>
	<textbox readonly="true" value="${each.decOcc}"/>*/
	
	
	@Override
	public void render(Row row, WeeklyMtgMinSalesPaces data, int index)
			throws Exception {
		
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;   
		
		Label typeTranslated = new Label();
		typeTranslated.setValue(data.getRowTypeTranslated());
		
		row.appendChild(typeTranslated);
		
		for(int i=1; i<=12;i++){
			
			Textbox occCell = new Textbox();
			occCell.setReadonly(true);
			occCell.setValue(this.getOccByMonth(data, currentMonth > 12 ? currentMonth%12 : currentMonth));
			
			row.appendChild(occCell);	
			
			currentMonth++;
		}
	}
	
	private String getOccByMonth(WeeklyMtgMinSalesPaces data, int month) {
		
		if(month == 1) return data.getJanOcc().toPlainString();
		else if(month == 2) return data.getFebOcc().toPlainString();
		else if(month == 3) return data.getMarOcc().toPlainString();
		else if(month == 4) return data.getAprOcc().toPlainString();
		else if(month == 5) return data.getMayOcc().toPlainString();
		else if(month == 6) return data.getJunOcc().toPlainString();
		else if(month == 7) return data.getJulOcc().toPlainString();
		else if(month == 8) return data.getAugOcc().toPlainString();
		else if(month == 9) return data.getSepOcc().toPlainString();
		else if(month == 10) return data.getOctOcc().toPlainString();
		else if(month == 11) return data.getNovOcc().toPlainString();
		else if(month == 12) return data.getDecOcc().toPlainString();
		else return	"Unknown";
	}

}
