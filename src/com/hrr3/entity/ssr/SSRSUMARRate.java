package com.hrr3.entity.ssr;

import java.io.Serializable;

import com.hrr3.entity.ssrMigration.SSRSnapshotSUMARrate;

//SSRSnapshotSUMARrate was created  for the migration tool 
//it not necessary repeat code, so this class extends from it
public class SSRSUMARRate extends SSRSnapshotSUMARrate implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;

	public SSRSUMARRate() {
		// TODO Auto-generated constructor stub
	}

}
