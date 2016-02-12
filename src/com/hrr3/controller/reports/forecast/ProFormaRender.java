package com.hrr3.controller.reports.forecast;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

import com.hrr3.entity.proforma.ReportData;

public class ProFormaRender implements RowRenderer<ReportData>{	
	

	
	@Override
	public void render(Row row, ReportData data, int index)
			throws Exception {
		
		DateFormat uiFormat = new SimpleDateFormat("MM/dd/yyyy"); 
		DateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		String commonWidth = "40px";
		boolean isSummaryRow = data.isIsSummaryRow();
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		String decimalFormat = "###,###,##0.00";		
		String integerFormat = "###,###,##0";
		symbols.setGroupingSeparator(',');
		
		//General information
		Label comments = new Label(data.getComments());
		Label dow = new Label(data.getDow());
		String dateStr = data.getStatDate() !=null && !data.getStatDate().isEmpty() ? uiFormat.format(dbFormat.parse(data.getStatDate())) : "";
		Label date = new Label(dateStr);		
		//Baseline forecast
		Label baseRms = new Label(formatter.format(data.getBaseRms()));
		Label baseRate = new Label(formatter.format(data.getBaseRate()));
		Label baseRev = new Label(formatter.format(data.getBaseRev()));		
		//Add to row
		row.appendChild(comments);
		row.appendChild(dow);
		row.appendChild(date);
		row.appendChild(baseRms);
		row.appendChild(baseRate);
		row.appendChild(baseRev);			
		//Change
		if(isSummaryRow){
			Label changeRms = new Label(formatter.format(data.getChangeRms()));
			Label changeRate = new Label(formatter.format(data.getChangeRate()));
			Label changeRev = new Label(formatter.format(data.getChangeRev()));
			row.appendChild(changeRms);
			row.appendChild(changeRate);
			row.appendChild(changeRev);				
		}
		
		else {
			
			Intbox changeRms = new Intbox(data.getChangeRms());
			Decimalbox changeRate = new Decimalbox(data.getChangeRate());
			Decimalbox changeRev = new Decimalbox(data.getChangeRev());
			
			changeRms.setFormat(integerFormat);
			changeRate.setFormat(decimalFormat);
			changeRev.setFormat(decimalFormat);
			
			changeRms.setLocale(Locale.US);			
			changeRate.setLocale(Locale.US);	
			changeRev.setLocale(Locale.US);	
			
			changeRms.setInplace(true);
			changeRate.setInplace(true);
			changeRev.setInplace(true);
			
			changeRms.setConstraint(" no empty");
			changeRate.setConstraint(" no empty");
			changeRev.setConstraint(" no empty");
			
			changeRms.setWidth(commonWidth);
			changeRate.setWidth(commonWidth);
			changeRev.setWidth(commonWidth);
						
			row.appendChild(changeRms);
			row.appendChild(changeRate);
			row.appendChild(changeRev);	
		}
		
		//Proforma Basis Forecast
		Label occRms = new Label(formatter.format(data.getOccRms()));
		Label totalAdr = new Label(formatter.format(data.getTotAdr()));
		Label roomRev = new Label(formatter.format(data.getTotRev()));
		
		row.appendChild(occRms);	
		row.appendChild(totalAdr);	
		row.appendChild(roomRev);
		
		//Proposed
		if(isSummaryRow){			
			
			Label proposedBase = new Label(formatter.format(data.getPropBaseRms()));
			Label proposedRate = new Label(formatter.format(data.getPropBaseRate()));
			
			row.appendChild(proposedBase);	
			row.appendChild(proposedRate);			
		}
		
		else {
			
			Intbox proposedBase = new Intbox(data.getPropBaseRms());
			Decimalbox proposedRate = new Decimalbox(data.getPropBaseRate());
			
			proposedBase.setFormat(integerFormat);
			proposedRate.setFormat(decimalFormat);
			
			proposedBase.setLocale(Locale.US);			
			proposedRate.setLocale(Locale.US);
			
			proposedBase.setInplace(true);
			proposedRate.setInplace(true);
			
			proposedBase.setConstraint(" no empty");
			proposedRate.setConstraint(" no empty");
			
			proposedBase.setWidth(commonWidth);
			proposedRate.setWidth(commonWidth);
						
			row.appendChild(proposedBase);
			row.appendChild(proposedRate);
			
		}
			
		//Displaced
		Label dispRms = new Label(formatter.format(data.getDispRms()));
		Label dispRev = new Label(formatter.format(data.getIncRev()));
		
		row.appendChild(dispRms);	
		row.appendChild(dispRev);
		
		//New Totals
		Label newTotRev = new Label(formatter.format(data.getNewTotRev()));
		Label incProfit = new Label(formatter.format(data.getIncProfit()));
		Label dispAdr = new Label(formatter.format(data.getDispAdr()));
		
		row.appendChild(newTotRev);	
		row.appendChild(incProfit);
		row.appendChild(dispAdr);
		
		if(isSummaryRow){	
			Label dispAdrOverride = new Label(formatter.format(data.getDispAdrOverride()));
			row.appendChild(dispAdrOverride);
		}
		else {
			Decimalbox dispAdrOverride = new Decimalbox(new BigDecimal(data.getDispAdrOverride()));
			
			dispAdrOverride.setFormat(integerFormat);	
			dispAdrOverride.setLocale(Locale.US);
			dispAdrOverride.setInplace(true);
			dispAdrOverride.setConstraint(" no empty");
			dispAdrOverride.setWidth(commonWidth);
			row.appendChild(dispAdrOverride);
		}
	
		
	}

}
