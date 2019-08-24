package com.acme.demo.home.model;

/**
 *
 * @author Ana Maria
 */
public class Customer {

	private String _rev;

	private String _id;

	private String lastName;

	private String firstName;

	private String streetAddress;

	private String cityStateZip;
	
	private CustomerType customerType;
	
	private String propertyId;
	
	private final String type = "customer";

	public Customer() {
	}

	public Customer(String ownerId) {
		this._id = ownerId;
	}


	public Customer(String lastName, String firstName, String streetAddress, String cityStateZip, CustomerType type, String propertyId) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.streetAddress = streetAddress;
		this.cityStateZip = cityStateZip;
		this.customerType = type;
		this.propertyId = propertyId;
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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCityStateZip() {
		return cityStateZip;
	}

	public void setCityStateZip(String cityStateZip) {
		this.cityStateZip = cityStateZip;
	}


	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (_id != null ? _id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Customer)) {
			return false;
		}
		Customer other = (Customer) object;
		if ((this._id == null && other._id != null)
				|| (this._id != null && !this._id.equals(other._id))) {
			return false;
		}
		return true;
	}

	@java.lang.Override
	public java.lang.String toString() {
		final java.lang.StringBuffer sb = new java.lang.StringBuffer("Customer{");
		sb.append("_rev='").append(_rev).append('\'');
		sb.append(", _id='").append(_id).append('\'');
		sb.append(", lastName='").append(lastName).append('\'');
		sb.append(", firstName='").append(firstName).append('\'');
		sb.append(", streetAddress='").append(streetAddress).append('\'');
		sb.append(", cityStateZip='").append(cityStateZip).append('\'');
		sb.append(", customerType=").append(customerType);
		sb.append(", propertyId='").append(propertyId).append('\'');
		sb.append(", type='").append(type).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
