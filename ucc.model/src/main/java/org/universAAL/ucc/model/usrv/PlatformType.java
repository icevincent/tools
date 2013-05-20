//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.05.17 at 11:54:47 PM CEST 
//


package org.universAAL.ucc.model.usrv;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for platformType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="platformType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Java"/>
 *     &lt;enumeration value=".NET"/>
 *     &lt;enumeration value="Android"/>
 *     &lt;enumeration value="OSGi"/>
 *     &lt;enumeration value="OSGi-Android"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "platformType")
@XmlEnum
public enum PlatformType {

    @XmlEnumValue("Java")
    JAVA("Java"),
    @XmlEnumValue(".NET")
    NET(".NET"),
    @XmlEnumValue("Android")
    ANDROID("Android"),
    @XmlEnumValue("OSGi")
    OS_GI("OSGi"),
    @XmlEnumValue("OSGi-Android")
    OS_GI_ANDROID("OSGi-Android");
    private final String value;

    PlatformType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PlatformType fromValue(String v) {
        for (PlatformType c: PlatformType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
