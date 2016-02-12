package com.hrr3.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Auxhead;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.InputElement;
import org.zkoss.zul.impl.XulElement;

import com.hrr3.authentication.AuthenticationServiceHRR3Impl;
import com.hrr3.entity.RateCategory;
import com.hrr3.entity.ssr.SSRInputData;
import com.hrr3.model.RateCategoryDAO;
import com.hrr3.model.SSRInputDAO;
import com.hrr3.services.AuthenticationService;

public class SSRInputGridController extends GridArrowKeyController {
	private static final long serialVersionUID = 1L;

	@Wire
	Button ssrSubmit;
	
	@Wire
	Window parentSSRInput;

	@Wire
	Datebox ssrDateFrom;

	@Wire
	Datebox ssrDateTo;
	
	@Wire
	Auxhead segmentHeaders;
	AuthenticationService authService;

	Auxheader generalInformationHeader;
	Auxheader totalInformationHeader;
	Auxheader rateCategoriesHeader;
	Auxheader segmentInformationHeader;
	Auxheader MARInformationHeader;

	private ListModel<SSRInputData> ssrDataRows;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);		
		authService = new AuthenticationServiceHRR3Impl();
	}

	
	
	/**
	 * @return the ssrDataRows
	 */
	public ListModel<SSRInputData> getSsrDataRows() {
		return ssrDataRows;
	}

	/**
	 * @param ssrDataRows
	 *            the ssrDataRows to set
	 */
	public void setSsrDataRows(ListModel<SSRInputData> ssrDataRows) {
		this.ssrDataRows = ssrDataRows;
	}

	@Listen("onClick=#ssrSubmit")
	public void ssrSubmit() {

		AuthenticationService authService = new AuthenticationServiceHRR3Impl();

		Date dateFrom = ssrDateFrom.getValue();
		Date dateTo = ssrDateTo.getValue();

		if (authService.getUserData().getCurrentHotel() == null
				|| authService.getUserData().getCurrentHotel().getHotelId() == null
				|| authService.getUserData().getCurrentHotel().getHotelId() < 1) {
			Messagebox
					.show("Please select a valid Hotel and return to this section.");
			return;
		}

		int customerId = authService.getUserData().getCurrentCustomer()
				.getCustomerId();
		int userId = authService.getUserData().getUserId();
		// Clear out columns
		this.genericGrid.getColumns().getChildren().clear();
		this.segmentHeaders.getChildren().clear();
		Columns currentGridColumns = this.genericGrid.getColumns();
		// Load all SubHeaders
		this.loadGeneralInformationHeaders();
		this.loadTotalInformationHeaders();
		this.loadRateCategoriesHeaders();
		this.loadSegmentInformationHeaders();
		this.loadMARInformationHeaders();

		this.reloadStaticColumns(currentGridColumns);

		// Create TIDAO and set currentHotelId
		SSRInputDAO ssrDAO = new SSRInputDAO(authService.getUserData()
				.getCurrentHotel());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<SSRInputData> tdrList = ssrDAO.getSSRInputData(
				dateFormat.format(dateFrom), dateFormat.format(dateTo), userId,
				customerId);

		this.ssrDataRows = new ListModelList<SSRInputData>(tdrList);
		
		
		
		this.genericGrid.setModel(this.ssrDataRows);
		this.genericGrid.setRowRenderer(new SSRInputRender());
		// Redraw grid to allow scrollbar appears
		this.genericGrid.setSizedByContent(true);
		// this.transientGrid.invalidate();		
	}
	
	
	private void loadGeneralInformationHeaders() {

		this.generalInformationHeader = new Auxheader("General Information");
		this.generalInformationHeader.setColspan(5);
		this.generalInformationHeader.setRowspan(1);
		this.generalInformationHeader.setStyle("text-align:center");

		this.segmentHeaders.appendChild(this.generalInformationHeader);

	}

	private void loadTotalInformationHeaders() {

		this.totalInformationHeader = new Auxheader("Totals");
		this.totalInformationHeader.setColspan(3);
		this.totalInformationHeader.setRowspan(1);
		this.totalInformationHeader.setStyle("text-align:center");

		this.segmentHeaders.appendChild(this.totalInformationHeader);

	}

	private void loadRateCategoriesHeaders() {

		RateCategoryDAO rateCategoryDAO = new RateCategoryDAO(this.authService.getUserData().getCurrentHotel());
		List<String> rateCols = rateCategoryDAO.populateColumnData().getVisibleColsWithAlias();	
		
		if(rateCols.size() > 0) {
			
			this.rateCategoriesHeader = new Auxheader("Rate Categories");
			this.rateCategoriesHeader.setColspan(rateCols.size());
			this.rateCategoriesHeader.setRowspan(1);
			this.rateCategoriesHeader.setStyle("text-align:center");

			this.segmentHeaders.appendChild(this.rateCategoriesHeader);
			
		}
		

	}

	private void loadSegmentInformationHeaders() {

		this.segmentInformationHeader = new Auxheader("Segment Information");
		this.segmentInformationHeader.setColspan(7);
		this.segmentInformationHeader.setRowspan(1);
		this.segmentInformationHeader.setStyle("text-align:center");

		this.segmentHeaders.appendChild(this.segmentInformationHeader);

	}

	private void loadMARInformationHeaders() {

		this.MARInformationHeader = new Auxheader("MAR Information");
		this.MARInformationHeader.setColspan(2);
		this.MARInformationHeader.setRowspan(1);
		this.MARInformationHeader.setStyle("text-align:center");

		this.segmentHeaders.appendChild(this.MARInformationHeader);

	}

	private void reloadStaticColumns(Columns currentGridColumns) {

		// General Information

		Column c1 = new Column("Comments");
		c1.setStyle("text-align:center;");

		Column c2 = new Column("Exception");
		c2.setStyle("text-align:center;");

		Column c3 = new Column("DOW");
		c3.setStyle("text-align:center;");

		Column c4 = new Column("Date");
		c4.setStyle("text-align:center;");

		Column c5 = new Column("A/F");
		c5.setStyle("text-align:center;");

		// Totals

		Column c6 = new Column(); 
		Label lbl6 = new Label("Tot \n Occ%");
		lbl6.setMultiline(true);
		c6.appendChild(lbl6);
		c6.setStyle("text-align:center;");

		Column c7 = new Column();
		Label lbl7 = new Label("Occ \n Rms");
		lbl7.setMultiline(true);
		c7.appendChild(lbl7);
		c7.setStyle("text-align:center;");

		Column c8 = new Column("LRR");
		c8.setStyle("text-align:center;");

		// Rate Categories
		
		RateCategoryDAO rateCategoryDAO = new RateCategoryDAO(this.authService.getUserData().getCurrentHotel());
		RateCategory rateCols = rateCategoryDAO.populateColumnData();

		Column c9 = new Column(rateCols.getColumnNameA());
		c9.setStyle("text-align:center;");
		c9.setVisible(!Boolean.valueOf(rateCols.getColumnAHiddenString()));
		
		Column c10 = new Column(rateCols.getColumnNameB());
		c10.setStyle("text-align:center;");
		c10.setVisible(!Boolean.valueOf(rateCols.getColumnBHiddenString()));

		Column c11 = new Column(rateCols.getColumnNameC());
		c11.setStyle("text-align:center;");
		c11.setVisible(!Boolean.valueOf(rateCols.getColumnCHiddenString()));

		Column c12 = new Column(rateCols.getColumnNameD());
		c12.setStyle("text-align:center;");
		c12.setVisible(!Boolean.valueOf(rateCols.getColumnDHiddenString()));

		Column c13 = new Column(rateCols.getColumnNameE());
		c13.setStyle("text-align:center;");
		c13.setVisible(!Boolean.valueOf(rateCols.getColumnEHiddenString()));

		Column c14 = new Column(rateCols.getColumnNameF());
		c14.setStyle("text-align:center;");
		c14.setVisible(!Boolean.valueOf(rateCols.getColumnFHiddenString()));

		Column c15 = new Column(rateCols.getColumnNameG());
		c15.setStyle("text-align:center;");
		c15.setVisible(!Boolean.valueOf(rateCols.getColumnGHiddenString()));

		Column c16 = new Column(rateCols.getColumnNameH());
		c16.setStyle("text-align:center;");
		c16.setVisible(!Boolean.valueOf(rateCols.getColumnHHiddenString()));

		Column c17 = new Column(rateCols.getColumnNameI());
		c17.setStyle("text-align:center;");
		c17.setVisible(!Boolean.valueOf(rateCols.getColumnIHiddenString()));

		Column c18 = new Column(rateCols.getColumnNameHP1());
		c18.setStyle("text-align:center;");
		c18.setVisible(!Boolean.valueOf(rateCols.getColumnHP1HiddenString()));

		Column c19 = new Column(rateCols.getColumnNameHP2());
		c19.setStyle("text-align:center;");
		c19.setVisible(!Boolean.valueOf(rateCols.getColumnHP2HiddenString()));

		Column c20 = new Column("Oversell Factor");
		c20.setVisible(Boolean.valueOf(rateCols.getOversellString()));

		// Segment Information

		Column c21 = new Column("Trans");
		c21.setStyle("text-align:center;");

		Column c22 = new Column();
		Label lbl22 = new Label("Group \n Block");
		lbl22.setMultiline(true);
		c22.appendChild(lbl22);
		c22.setStyle("text-align:center;");

		Column c23 = new Column();
		Label lbl23 = new Label("Group \n P/U");
		lbl23.setMultiline(true);
		c23.appendChild(lbl23);
		c23.setStyle("text-align:center;");

		Column c24 = new Column();
		Label lbl24 = new Label("Group \n Remain");
		lbl24.setMultiline(true);
		c24.appendChild(lbl24);
		c24.setStyle("text-align:center;");

		Column c25 = new Column("Contract");
		c25.setStyle("text-align:center;");

		Column c26 = new Column();
		Label lbl26 = new Label("Demand \n TD");
		lbl26.setMultiline(true);
		c26.appendChild(lbl26);
		c26.setStyle("text-align:center;");
		
		Column c27 = new Column();
		Label lbl27 = new Label("Price \n TD");
		lbl27.setMultiline(true);
		c27.appendChild(lbl27);
		c27.setStyle("text-align:center;");


		// MAR Summary

		// Column c26 = new Column("Seasonal Rate");
		// c26.setStyle("text-align:center;");

		Column c28 = new Column();
		Label lbl28 = new Label("SSR \n MAR");
		lbl28.setMultiline(true);
		c28.appendChild(lbl28);
		

		currentGridColumns.appendChild(c1);
		currentGridColumns.appendChild(c2);
		currentGridColumns.appendChild(c3);
		currentGridColumns.appendChild(c4);
		currentGridColumns.appendChild(c5);
		currentGridColumns.appendChild(c6);
		currentGridColumns.appendChild(c7);
		currentGridColumns.appendChild(c8);
		currentGridColumns.appendChild(c9);
		currentGridColumns.appendChild(c10);
		currentGridColumns.appendChild(c11);
		currentGridColumns.appendChild(c12);
		currentGridColumns.appendChild(c13);
		currentGridColumns.appendChild(c14);
		currentGridColumns.appendChild(c15);
		currentGridColumns.appendChild(c16);
		currentGridColumns.appendChild(c17);
		currentGridColumns.appendChild(c18);
		currentGridColumns.appendChild(c19);
		currentGridColumns.appendChild(c20);
		currentGridColumns.appendChild(c21);
		currentGridColumns.appendChild(c22);
		currentGridColumns.appendChild(c23);
		currentGridColumns.appendChild(c24);
		currentGridColumns.appendChild(c25);
		currentGridColumns.appendChild(c26);
		currentGridColumns.appendChild(c27);
		currentGridColumns.appendChild(c28);
		// currentGridColumns.appendChild(c27);

	}

}
