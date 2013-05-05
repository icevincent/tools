//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.05.03 at 12:51:47 PM CEST 
//


package org.universAAL.ucc.model.usrv;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * describes multiple requirements with a given logical relation
 * 
 * <p>Java class for reqGroupType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="reqGroupType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="logicalRelation" type="{http://www.venstar.de/uapp}logicalRelationType"/>
 *         &lt;element name="requirement" type="{http://www.venstar.de/uapp}reqType" maxOccurs="2" minOccurs="2"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reqGroupType", propOrder = {
    "logicalRelation",
    "requirement"
})
public class ReqGroupType
    implements Serializable
{

    private final static long serialVersionUID = 12343L;
    @XmlElement(required = true)
    protected LogicalRelationType logicalRelation;
    @XmlElement(required = true)
    protected List<ReqType> requirement;

    /**
     * Gets the value of the logicalRelation property.
     * 
     * @return
     *     possible object is
     *     {@link LogicalRelationType }
     *     
     */
    public LogicalRelationType getLogicalRelation() {
        return logicalRelation;
    }

    /**
     * Sets the value of the logicalRelation property.
     * 
     * @param value
     *     allowed object is
     *     {@link LogicalRelationType }
     *     
     */
    public void setLogicalRelation(LogicalRelationType value) {
        this.logicalRelation = value;
    }

    public boolean isSetLogicalRelation() {
        return (this.logicalRelation!= null);
    }

    /**
     * Gets the value of the requirement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requirement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequirement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReqType }
     * 
     * 
     */
    public List<ReqType> getRequirement() {
        if (requirement == null) {
            requirement = new ArrayList<ReqType>();
        }
        return this.requirement;
    }

    public boolean isSetRequirement() {
        return ((this.requirement!= null)&&(!this.requirement.isEmpty()));
    }

    public void unsetRequirement() {
        this.requirement = null;
    }

}
