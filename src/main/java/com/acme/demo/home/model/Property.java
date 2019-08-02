package com.acme.demo.home.model;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTypeAdapter;

/**
 *
 * @author Ana Maria
 */
public class Property {

	private String _rev;
	private String _id;
    private String address;
    private String zip;
    private String tags;
    private String brokerId;
    private String brokerName;
    private Money price;
    private Integer baths;
    private Integer bedrooms;
    private TransactionType transactionType;
    private String type;
    
    public Property() {}
    
	public Property(String address, String zip, String tags, String brokerId, Money price, Integer baths, Integer bedrooms, TransactionType type) {
		super();
		this.address = address;
		this.zip = zip;
		this.tags = tags;
		this.brokerId = brokerId;
		this.price = price;
		this.baths = baths;
		this.bedrooms = bedrooms;
		this.type = "property";
	}

	public String get_rev() {
		return _rev;
	}

	public void set_rev(String _rev) {
		this._rev = _rev;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getBrokerId() {
		return brokerId;
	}
	public void setBrokerId(String brokerId) {
		this.brokerId = brokerId;
	}
	public String getBrokerName() {
		return brokerName;
	}
	
	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	public Money getPrice() {
		return price;
	}
	public void setPrice(Money price) {
		this.price = price;
	}
	public Integer getBaths() {
		return baths;
	}
	public void setBaths(Integer baths) {
		this.baths = baths;
	}
	public Integer getBedrooms() {
		return bedrooms;
	}
	public void setBedrooms(Integer bedrooms) {
		this.bedrooms = bedrooms;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType type) {
		this.transactionType = type;
	}

	public String getType() {
		return type;
	}

	@java.lang.Override
	public java.lang.String toString() {
		final java.lang.StringBuffer sb = new java.lang.StringBuffer("Property{");
		sb.append("_rev='").append(_rev).append('\'');
		sb.append(", _id='").append(_id).append('\'');
		sb.append(", address='").append(address).append('\'');
		sb.append(", zip='").append(zip).append('\'');
		sb.append(", tags='").append(tags).append('\'');
		sb.append(", brokerId='").append(brokerId).append('\'');
		sb.append(", brokerName='").append(brokerName).append('\'');
		sb.append(", price=").append(price);
		sb.append(", baths=").append(baths);
		sb.append(", bedrooms=").append(bedrooms);
		sb.append(", transactionType=").append(transactionType);
		sb.append(", type='").append(type).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
