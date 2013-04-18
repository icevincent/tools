//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.17 at 09:18:23 PM CEST 
//


package org.universAAL.ucc.database.model.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for profiles complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="profiles">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ontologyInstances" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ontologyInstance" type="{}ontologyInstance" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name ="profiles")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "profiles", propOrder = {
    "ontologyInstances"
})
public class Profiles {

    protected Profiles.OntologyInstances ontologyInstances;

    /**
     * Gets the value of the ontologyInstances property.
     * 
     * @return
     *     possible object is
     *     {@link Profiles.OntologyInstances }
     *     
     */
    public Profiles.OntologyInstances getOntologyInstances() {
        return ontologyInstances;
    }

    /**
     * Sets the value of the ontologyInstances property.
     * 
     * @param value
     *     allowed object is
     *     {@link Profiles.OntologyInstances }
     *     
     */
    public void setOntologyInstances(Profiles.OntologyInstances value) {
        this.ontologyInstances = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ontologyInstance" type="{}ontologyInstance" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "ontologyInstance"
    })
    public static class OntologyInstances {

        protected List<OntologyInstance> ontologyInstance;

        /**
         * Gets the value of the ontologyInstance property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ontologyInstance property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOntologyInstance().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OntologyInstance }
         * 
         * 
         */
        public List<OntologyInstance> getOntologyInstance() {
            if (ontologyInstance == null) {
                ontologyInstance = new ArrayList<OntologyInstance>();
            }
            return this.ontologyInstance;
        }

    }

}
