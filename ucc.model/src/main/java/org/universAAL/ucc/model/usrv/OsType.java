//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.05.11 at 11:42:03 AM CEST 
//


package org.universAAL.ucc.model.usrv;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for osType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="osType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Linux"/>
 *     &lt;enumeration value="Windows"/>
 *     &lt;enumeration value="MacOS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "osType")
@XmlEnum
public enum OsType {

    @XmlEnumValue("Linux")
    LINUX("Linux"),
    @XmlEnumValue("Windows")
    WINDOWS("Windows"),
    @XmlEnumValue("MacOS")
    MAC_OS("MacOS");
    private final String value;

    OsType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OsType fromValue(String v) {
        for (OsType c: OsType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
