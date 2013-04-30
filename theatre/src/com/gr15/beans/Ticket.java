package com.gr15.beans;

import org.joda.time.DateTime;

public class Ticket {
    private int id;
    private DateTime date;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public DateTime getDate() {
	return date;
    }

    public void setDate(DateTime date) {
	this.date = date;
    }
}
