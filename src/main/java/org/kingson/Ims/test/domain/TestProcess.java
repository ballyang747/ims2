package org.kingson.Ims.test.domain;

import javax.persistence.Entity;

import org.kingson.Ims.workflow.domain.BusinessData;

@Entity
public class TestProcess extends BusinessData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
