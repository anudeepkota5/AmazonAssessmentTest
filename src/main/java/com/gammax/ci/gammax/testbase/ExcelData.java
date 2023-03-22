package com.gammax.ci.gammax.testbase;

import com.creditdatamw.zerocell.annotation.Column;

public class ExcelData {
	
	@Column(name="TestName", index=0)
	private String testname;
	
	@Column(name="Crypto", index=1)
	private String crypto;

	@Column(name="Price", index=2)
	private String price;
	
	@Column(name="Quantity", index=3)
	private String quantity;
	
	@Column(name="TIF", index=4)
	private String tif;
	
	@Column(name="Amount", index=5)
	private String amount;
	
	@Override
	public String toString() {
		return "ExcelData{"+
				"TestName='"+testname+"'"+
				", price='"+price+"'"+
				", quantity='"+quantity+"'"+
				", tif='"+tif+"'"+
				"}";
	}
	
	public String getTestname() {
		return testname;
	}
	
	public String getCrypto() {
		return crypto;
	}

	public String getPrice() {
		return price;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getTif() {
		return tif;
	}
	
	public String getAmount() {
		return amount;
	}

}
