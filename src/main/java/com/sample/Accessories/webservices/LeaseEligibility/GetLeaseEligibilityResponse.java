//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.06.01 at 10:17:26 AM IST 
//


package com.sample.Accessories.webservices.LeaseEligibility;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="accessoryId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LeaseInfo" type="{http://www.example.org/LeaseEligbility}LeaseInfoType" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "accessoryId",
    "leaseInfo"
})
@XmlRootElement(name = "getLeaseEligibilityResponse")
public class GetLeaseEligibilityResponse {

    @XmlElement(required = true)
    protected String accessoryId;
    @XmlElement(name = "LeaseInfo", required = true)
    protected List<LeaseInfoType> leaseInfo;

    /**
     * Gets the value of the accessoryId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessoryId() {
        return accessoryId;
    }

    /**
     * Sets the value of the accessoryId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessoryId(String value) {
        this.accessoryId = value;
    }

    /**
     * Gets the value of the leaseInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the leaseInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLeaseInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LeaseInfoType }
     * 
     * 
     */
    public List<LeaseInfoType> getLeaseInfo() {
        if (leaseInfo == null) {
            leaseInfo = new ArrayList<LeaseInfoType>();
        }
        return this.leaseInfo;
    }

}
