package com.hrr3.util.reports;

import java.util.Iterator;
import java.util.Set;

import com.hrr3.entity.Hotel;
import com.hrr3.entity.SSRSnapshot;

public class JasperServerReportCustomFunctions {
	
	public static String splitHotelsWithPipes (Set<Hotel> hotels) {
		
		String pipeLine = "|";
		StringBuilder params = new StringBuilder();
		Iterator<Hotel> itr = hotels.iterator();
		
		while(itr.hasNext()) {
			
			Hotel hotel = itr.next();
			
			if(itr.hasNext())
				params.append(hotel.getHotelId() + pipeLine);
			else
				params.append(hotel.getHotelId());
		}
		
		return params.toString();
		
	}
	
	public static String splitSSRSnapshotsWithPipes (Set<SSRSnapshot> snapshots) {
		
		String pipeLine = "|";
		StringBuilder params = new StringBuilder();
		Iterator<SSRSnapshot> itr = snapshots.iterator();
		
		while(itr.hasNext()) {
			
			SSRSnapshot snapshot = itr.next();
			
			if(itr.hasNext())
				params.append(snapshot.getSnapshotId() + pipeLine);
			else
				params.append(snapshot.getSnapshotId());
		}
		
		return params.toString();
		
	}

}
