package com.bigtail.platform.framework.orm.dao.persistence.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dual")
public class DualEntity  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="DUMMY")
	private String testName;

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}
	
	
	
}
