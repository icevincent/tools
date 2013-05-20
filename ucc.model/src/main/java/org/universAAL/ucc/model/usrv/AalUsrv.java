//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.05.17 at 11:54:47 PM CEST 
//


package org.universAAL.ucc.model.usrv;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="srv">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="version" type="{http://www.universaal.org/aal-uapp/v1.0.0}versionType"/>
 *                   &lt;element name="serviceId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="tags" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="serviceProvider" type="{http://www.universaal.org/aal-uapp/v1.0.0}contactType"/>
 *                   &lt;element name="licenses" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="license" type="{http://www.universaal.org/aal-uapp/v1.0.0}licenseType" maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;element name="sla" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="link" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="serviceProfile" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="serviceCapabilities" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="capability" type="{http://www.universaal.org/aal-uapp/v1.0.0}capabilityType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="serviceRequirements" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="requirement" type="{http://www.universaal.org/aal-uapp/v1.0.0}reqType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="components">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="application" type="{http://www.universaal.org/aal-usrv/v1.0.0}applicationType" maxOccurs="unbounded"/>
 *                   &lt;element name="hardware" type="{http://www.universaal.org/aal-usrv/v1.0.0}hardwareType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="human" type="{http://www.universaal.org/aal-usrv/v1.0.0}humanType" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "srv",
    "serviceCapabilities",
    "serviceRequirements",
    "components"
})
@XmlRootElement(name = "aal-usrv", namespace = "http://www.universaal.org/aal-usrv/v1.0.0")
public class AalUsrv
    implements Serializable
{

    private final static long serialVersionUID = 12343L;
    @XmlElement(namespace = "http://www.universaal.org/aal-usrv/v1.0.0", required = true)
    protected AalUsrv.Srv srv;
    @XmlElement(namespace = "http://www.universaal.org/aal-usrv/v1.0.0")
    protected AalUsrv.ServiceCapabilities serviceCapabilities;
    @XmlElement(namespace = "http://www.universaal.org/aal-usrv/v1.0.0")
    protected AalUsrv.ServiceRequirements serviceRequirements;
    @XmlElement(namespace = "http://www.universaal.org/aal-usrv/v1.0.0", required = true)
    protected AalUsrv.Components components;

    /**
     * Gets the value of the srv property.
     * 
     * @return
     *     possible object is
     *     {@link AalUsrv.Srv }
     *     
     */
    public AalUsrv.Srv getSrv() {
        return srv;
    }

    /**
     * Sets the value of the srv property.
     * 
     * @param value
     *     allowed object is
     *     {@link AalUsrv.Srv }
     *     
     */
    public void setSrv(AalUsrv.Srv value) {
        this.srv = value;
    }

    public boolean isSetSrv() {
        return (this.srv!= null);
    }

    /**
     * Gets the value of the serviceCapabilities property.
     * 
     * @return
     *     possible object is
     *     {@link AalUsrv.ServiceCapabilities }
     *     
     */
    public AalUsrv.ServiceCapabilities getServiceCapabilities() {
        return serviceCapabilities;
    }

    /**
     * Sets the value of the serviceCapabilities property.
     * 
     * @param value
     *     allowed object is
     *     {@link AalUsrv.ServiceCapabilities }
     *     
     */
    public void setServiceCapabilities(AalUsrv.ServiceCapabilities value) {
        this.serviceCapabilities = value;
    }

    public boolean isSetServiceCapabilities() {
        return (this.serviceCapabilities!= null);
    }

    /**
     * Gets the value of the serviceRequirements property.
     * 
     * @return
     *     possible object is
     *     {@link AalUsrv.ServiceRequirements }
     *     
     */
    public AalUsrv.ServiceRequirements getServiceRequirements() {
        return serviceRequirements;
    }

    /**
     * Sets the value of the serviceRequirements property.
     * 
     * @param value
     *     allowed object is
     *     {@link AalUsrv.ServiceRequirements }
     *     
     */
    public void setServiceRequirements(AalUsrv.ServiceRequirements value) {
        this.serviceRequirements = value;
    }

    public boolean isSetServiceRequirements() {
        return (this.serviceRequirements!= null);
    }

    /**
     * Gets the value of the components property.
     * 
     * @return
     *     possible object is
     *     {@link AalUsrv.Components }
     *     
     */
    public AalUsrv.Components getComponents() {
        return components;
    }

    /**
     * Sets the value of the components property.
     * 
     * @param value
     *     allowed object is
     *     {@link AalUsrv.Components }
     *     
     */
    public void setComponents(AalUsrv.Components value) {
        this.components = value;
    }

    public boolean isSetComponents() {
        return (this.components!= null);
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
     *         &lt;element name="application" type="{http://www.universaal.org/aal-usrv/v1.0.0}applicationType" maxOccurs="unbounded"/>
     *         &lt;element name="hardware" type="{http://www.universaal.org/aal-usrv/v1.0.0}hardwareType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="human" type="{http://www.universaal.org/aal-usrv/v1.0.0}humanType" maxOccurs="unbounded" minOccurs="0"/>
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
        "application",
        "hardware",
        "human"
    })
    public static class Components
        implements Serializable
    {

        private final static long serialVersionUID = 12343L;
        @XmlElement(namespace = "http://www.universaal.org/aal-usrv/v1.0.0", required = true)
        protected List<ApplicationType> application;
        @XmlElement(namespace = "http://www.universaal.org/aal-usrv/v1.0.0")
        protected List<HardwareType> hardware;
        @XmlElement(namespace = "http://www.universaal.org/aal-usrv/v1.0.0")
        protected List<HumanType> human;

        /**
         * Gets the value of the application property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the application property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getApplication().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ApplicationType }
         * 
         * 
         */
        public List<ApplicationType> getApplication() {
            if (application == null) {
                application = new ArrayList<ApplicationType>();
            }
            return this.application;
        }

        public boolean isSetApplication() {
            return ((this.application!= null)&&(!this.application.isEmpty()));
        }

        public void unsetApplication() {
            this.application = null;
        }

        /**
         * Gets the value of the hardware property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the hardware property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHardware().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HardwareType }
         * 
         * 
         */
        public List<HardwareType> getHardware() {
            if (hardware == null) {
                hardware = new ArrayList<HardwareType>();
            }
            return this.hardware;
        }

        public boolean isSetHardware() {
            return ((this.hardware!= null)&&(!this.hardware.isEmpty()));
        }

        public void unsetHardware() {
            this.hardware = null;
        }

        /**
         * Gets the value of the human property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the human property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHuman().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HumanType }
         * 
         * 
         */
        public List<HumanType> getHuman() {
            if (human == null) {
                human = new ArrayList<HumanType>();
            }
            return this.human;
        }

        public boolean isSetHuman() {
            return ((this.human!= null)&&(!this.human.isEmpty()));
        }

        public void unsetHuman() {
            this.human = null;
        }

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
     *         &lt;element name="capability" type="{http://www.universaal.org/aal-uapp/v1.0.0}capabilityType" maxOccurs="unbounded"/>
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
        "capability"
    })
    public static class ServiceCapabilities
        implements Serializable
    {

        private final static long serialVersionUID = 12343L;
        @XmlElement(namespace = "http://www.universaal.org/aal-usrv/v1.0.0", required = true)
        protected List<CapabilityType> capability;

        /**
         * Gets the value of the capability property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the capability property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCapability().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CapabilityType }
         * 
         * 
         */
        public List<CapabilityType> getCapability() {
            if (capability == null) {
                capability = new ArrayList<CapabilityType>();
            }
            return this.capability;
        }

        public boolean isSetCapability() {
            return ((this.capability!= null)&&(!this.capability.isEmpty()));
        }

        public void unsetCapability() {
            this.capability = null;
        }

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
     *         &lt;element name="requirement" type="{http://www.universaal.org/aal-uapp/v1.0.0}reqType" maxOccurs="unbounded"/>
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
        "requirement"
    })
    public static class ServiceRequirements
        implements Serializable
    {

        private final static long serialVersionUID = 12343L;
        @XmlElement(namespace = "http://www.universaal.org/aal-usrv/v1.0.0", required = true)
        protected List<ReqType> requirement;

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
     *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="version" type="{http://www.universaal.org/aal-uapp/v1.0.0}versionType"/>
     *         &lt;element name="serviceId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="tags" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="serviceProvider" type="{http://www.universaal.org/aal-uapp/v1.0.0}contactType"/>
     *         &lt;element name="licenses" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="license" type="{http://www.universaal.org/aal-uapp/v1.0.0}licenseType" maxOccurs="unbounded" minOccurs="0"/>
     *                   &lt;element name="sla" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="link" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="serviceProfile" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "name",
        "version",
        "serviceId",
        "description",
        "tags",
        "serviceProvider",
        "licenses",
        "serviceProfile"
    })
    public static class Srv
        implements Serializable
    {

        private final static long serialVersionUID = 12343L;
        @XmlElement(namespace = "http://www.universaal.org/aal-usrv/v1.0.0", required = true)
        protected String name;
        @XmlElement(namespace = "http://www.universaal.org/aal-usrv/v1.0.0", required = true)
        protected VersionType version;
        @XmlElement(namespace = "http://www.universaal.org/aal-usrv/v1.0.0", required = true)
        protected String serviceId;
        @XmlElement(namespace = "http://www.universaal.org/aal-usrv/v1.0.0", required = true)
        protected String description;
        @XmlElement(namespace = "http://www.universaal.org/aal-usrv/v1.0.0", required = true)
        protected String tags;
        @XmlElement(namespace = "http://www.universaal.org/aal-usrv/v1.0.0", required = true)
        protected ContactType serviceProvider;
        @XmlElement(namespace = "http://www.universaal.org/aal-usrv/v1.0.0")
        protected List<AalUsrv.Srv.Licenses> licenses;
        @XmlElement(namespace = "http://www.universaal.org/aal-usrv/v1.0.0", required = true)
        protected String serviceProfile;

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

        public boolean isSetName() {
            return (this.name!= null);
        }

        /**
         * Gets the value of the version property.
         * 
         * @return
         *     possible object is
         *     {@link VersionType }
         *     
         */
        public VersionType getVersion() {
            return version;
        }

        /**
         * Sets the value of the version property.
         * 
         * @param value
         *     allowed object is
         *     {@link VersionType }
         *     
         */
        public void setVersion(VersionType value) {
            this.version = value;
        }

        public boolean isSetVersion() {
            return (this.version!= null);
        }

        /**
         * Gets the value of the serviceId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getServiceId() {
            return serviceId;
        }

        /**
         * Sets the value of the serviceId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setServiceId(String value) {
            this.serviceId = value;
        }

        public boolean isSetServiceId() {
            return (this.serviceId!= null);
        }

        /**
         * Gets the value of the description property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescription() {
            return description;
        }

        /**
         * Sets the value of the description property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescription(String value) {
            this.description = value;
        }

        public boolean isSetDescription() {
            return (this.description!= null);
        }

        /**
         * Gets the value of the tags property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTags() {
            return tags;
        }

        /**
         * Sets the value of the tags property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTags(String value) {
            this.tags = value;
        }

        public boolean isSetTags() {
            return (this.tags!= null);
        }

        /**
         * Gets the value of the serviceProvider property.
         * 
         * @return
         *     possible object is
         *     {@link ContactType }
         *     
         */
        public ContactType getServiceProvider() {
            return serviceProvider;
        }

        /**
         * Sets the value of the serviceProvider property.
         * 
         * @param value
         *     allowed object is
         *     {@link ContactType }
         *     
         */
        public void setServiceProvider(ContactType value) {
            this.serviceProvider = value;
        }

        public boolean isSetServiceProvider() {
            return (this.serviceProvider!= null);
        }

        /**
         * Gets the value of the licenses property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the licenses property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getLicenses().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AalUsrv.Srv.Licenses }
         * 
         * 
         */
        public List<AalUsrv.Srv.Licenses> getLicenses() {
            if (licenses == null) {
                licenses = new ArrayList<AalUsrv.Srv.Licenses>();
            }
            return this.licenses;
        }

        public boolean isSetLicenses() {
            return ((this.licenses!= null)&&(!this.licenses.isEmpty()));
        }

        public void unsetLicenses() {
            this.licenses = null;
        }

        /**
         * Gets the value of the serviceProfile property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getServiceProfile() {
            return serviceProfile;
        }

        /**
         * Sets the value of the serviceProfile property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setServiceProfile(String value) {
            this.serviceProfile = value;
        }

        public boolean isSetServiceProfile() {
            return (this.serviceProfile!= null);
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
         *         &lt;element name="license" type="{http://www.universaal.org/aal-uapp/v1.0.0}licenseType" maxOccurs="unbounded" minOccurs="0"/>
         *         &lt;element name="sla" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="link" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
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
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "license",
            "sla"
        })
        public static class Licenses
            implements Serializable
        {

            private final static long serialVersionUID = 12343L;
            @XmlElement(namespace = "http://www.universaal.org/aal-usrv/v1.0.0")
            protected List<LicenseType> license;
            @XmlElement(namespace = "http://www.universaal.org/aal-usrv/v1.0.0")
            protected AalUsrv.Srv.Licenses.Sla sla;

            /**
             * Gets the value of the license property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the license property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getLicense().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link LicenseType }
             * 
             * 
             */
            public List<LicenseType> getLicense() {
                if (license == null) {
                    license = new ArrayList<LicenseType>();
                }
                return this.license;
            }

            public boolean isSetLicense() {
                return ((this.license!= null)&&(!this.license.isEmpty()));
            }

            public void unsetLicense() {
                this.license = null;
            }

            /**
             * Gets the value of the sla property.
             * 
             * @return
             *     possible object is
             *     {@link AalUsrv.Srv.Licenses.Sla }
             *     
             */
            public AalUsrv.Srv.Licenses.Sla getSla() {
                return sla;
            }

            /**
             * Sets the value of the sla property.
             * 
             * @param value
             *     allowed object is
             *     {@link AalUsrv.Srv.Licenses.Sla }
             *     
             */
            public void setSla(AalUsrv.Srv.Licenses.Sla value) {
                this.sla = value;
            }

            public boolean isSetSla() {
                return (this.sla!= null);
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
             *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="link" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
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
                "name",
                "link"
            })
            public static class Sla
                implements Serializable
            {

                private final static long serialVersionUID = 12343L;
                @XmlElement(namespace = "http://www.universaal.org/aal-usrv/v1.0.0", required = true)
                protected String name;
                @XmlElement(namespace = "http://www.universaal.org/aal-usrv/v1.0.0", required = true)
                @XmlSchemaType(name = "anyURI")
                protected String link;

                /**
                 * Gets the value of the name property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getName() {
                    return name;
                }

                /**
                 * Sets the value of the name property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setName(String value) {
                    this.name = value;
                }

                public boolean isSetName() {
                    return (this.name!= null);
                }

                /**
                 * Gets the value of the link property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLink() {
                    return link;
                }

                /**
                 * Sets the value of the link property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLink(String value) {
                    this.link = value;
                }

                public boolean isSetLink() {
                    return (this.link!= null);
                }

            }

        }

    }

}
