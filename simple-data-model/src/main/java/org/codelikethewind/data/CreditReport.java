package org.codelikethewind.data;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "org.codelikethewind.data.CreditReport")
public class CreditReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fullname;
	private String ssn;
	private Integer creditScore;
	
	public CreditReport(){
		
	}
	
	/**
	 * @return the fullname
	 */
	@XmlElement
	public String getFullname() {
		return fullname;
	}
	/**
	 * @param fullname the fullname to set
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	/**
	 * @return the ssn
	 */
	@XmlElement
	public String getSsn() {
		return ssn;
	}
	/**
	 * @param ssn the ssn to set
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	/**
	 * @return the creditScore
	 */
	@XmlElement
	public Integer getCreditScore() {
		return creditScore;
	}
	/**
	 * @param creditScore the creditScore to set
	 */
	public void setCreditScore(Integer creditScore) {
		this.creditScore = creditScore;
	}
	

}
