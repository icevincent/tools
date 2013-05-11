//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.05.11 at 11:42:03 AM CEST 
//


package org.universAAL.ucc.model.usrv;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * describes single offering, mostly used for devices and platforms
 * 
 * <p>Java class for reqType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="reqType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="reqAtom" type="{http://www.universaal.org/aal-uapp/v1.0.0}reqAtomType"/>
 *           &lt;element name="reqGroup" type="{http://www.universaal.org/aal-uapp/v1.0.0}reqGroupType"/>
 *         &lt;/choice>
 *         &lt;element name="optional" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reqType", propOrder = {
    "reqAtom",
    "reqGroup",
    "optional"
})
public class ReqType
    implements Serializable
{

    private final static long serialVersionUID = 12343L;
    protected ReqAtomType reqAtom;
    protected ReqGroupType reqGroup;
    protected Boolean optional;

    /**
     * Gets the value of the reqAtom property.
     * 
     * @return
     *     possible object is
     *     {@link ReqAtomType }
     *     
     */
    public ReqAtomType getReqAtom() {
        return reqAtom;
    }

    /**
     * Sets the value of the reqAtom property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReqAtomType }
     *     
     */
    public void setReqAtom(ReqAtomType value) {
        this.reqAtom = value;
    }

    public boolean isSetReqAtom() {
        return (this.reqAtom!= null);
    }

    /**
     * Gets the value of the reqGroup property.
     * 
     * @return
     *     possible object is
     *     {@link ReqGroupType }
     *     
     */
    public ReqGroupType getReqGroup() {
        return reqGroup;
    }

    /**
     * Sets the value of the reqGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReqGroupType }
     *     
     */
    public void setReqGroup(ReqGroupType value) {
        this.reqGroup = value;
    }

    public boolean isSetReqGroup() {
        return (this.reqGroup!= null);
    }

    /**
     * Gets the value of the optional property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOptional() {
        return optional;
    }

    /**
     * Sets the value of the optional property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOptional(Boolean value) {
        this.optional = value;
    }

    public boolean isSetOptional() {
        return (this.optional!= null);
    }

}
