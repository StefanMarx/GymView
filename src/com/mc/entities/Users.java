package com.mc.entities;

import java.util.Date;

public class Users {
	private int user_id;
	private String mail;
	private String firstname;
	private String lastname;
	private String street;
	private String streetno;
	private String zip;
	private String city;
	private String cellphone;
	private Date birth_date;
	private Date enter_date;
	
	Users(){
		
	}
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreetno() {
		return streetno;
	}
	public void setStreetno(String streetno) {
		this.streetno = streetno;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public Date getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}
	public Date getEnter_date() {
		return enter_date;
	}
	public void setEnter_date(Date enter_date) {
		this.enter_date = enter_date;
	}

	
}
