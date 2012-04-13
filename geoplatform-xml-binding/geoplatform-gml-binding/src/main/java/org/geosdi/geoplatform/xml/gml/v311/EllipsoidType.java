/*
 *  geo-platform
 *  Rich webgis framework
 *  http://geo-platform.org
 * ====================================================================
 *
 * Copyright (C) 2008-2012 geoSDI Group (CNR IMAA - Potenza - ITALY).
 *
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. This program is distributed in the 
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details. You should have received a copy of the GNU General 
 * Public License along with this program. If not, see http://www.gnu.org/licenses/ 
 *
 * ====================================================================
 *
 * Linking this library statically or dynamically with other modules is 
 * making a combined work based on this library. Thus, the terms and 
 * conditions of the GNU General Public License cover the whole combination. 
 * 
 * As a special exception, the copyright holders of this library give you permission 
 * to link this library with independent modules to produce an executable, regardless 
 * of the license terms of these independent modules, and to copy and distribute 
 * the resulting executable under terms of your choice, provided that you also meet, 
 * for each linked independent module, the terms and conditions of the license of 
 * that module. An independent module is a module which is not derived from or 
 * based on this library. If you modify this library, you may extend this exception 
 * to your version of the library, but you are not obligated to do so. If you do not 
 * wish to do so, delete this exception statement from your version. 
 *
 */
package org.geosdi.geoplatform.xml.gml.v311;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * An ellipsoid is a geometric figure that can be used to describe the approximate shape of the earth. In mathematical terms, it is a surface formed by the rotation of an ellipse about its minor axis.
 * 
 * <p>Java class for EllipsoidType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EllipsoidType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml}EllipsoidBaseType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/gml}ellipsoidID" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.opengis.net/gml}remarks" minOccurs="0"/>
 *         &lt;element ref="{http://www.opengis.net/gml}semiMajorAxis"/>
 *         &lt;element ref="{http://www.opengis.net/gml}secondDefiningParameter"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EllipsoidType", propOrder = {
    "ellipsoidID",
    "remarks",
    "semiMajorAxis",
    "secondDefiningParameter"
})
public class EllipsoidType extends EllipsoidBaseType {

    private List<IdentifierType> ellipsoidID;
    protected StringOrRefType remarks;
    @XmlElement(required = true)
    protected MeasureType semiMajorAxis;
    @XmlElement(required = true)
    protected SecondDefiningParameterType secondDefiningParameter;

    public EllipsoidType() {
    }

    /**
     * @param ellipsoidID the ellipsoidID to set
     */
    public void setEllipsoidID(List<IdentifierType> ellipsoidID) {
        this.ellipsoidID = ellipsoidID;
    }

    /**
     * Set of alternative identifications of this ellipsoid. The first ellipsoidID, if any, is normally the primary identification code, and any others are aliases. Gets the value of the ellipsoidID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ellipsoidID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEllipsoidID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdentifierType }
     * 
     * 
     */
    public List<IdentifierType> getEllipsoidID() {
        if (ellipsoidID == null) {
            this.ellipsoidID = new ArrayList<IdentifierType>();
        }
        return this.ellipsoidID;
    }

    /**
     * Comments on or information about this ellipsoid, including source information. 
     * 
     * @return
     *     possible object is
     *     {@link StringOrRefType }
     *     
     */
    public StringOrRefType getRemarks() {
        return remarks;
    }

    /**
     * Sets the value of the remarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringOrRefType }
     *     
     */
    public void setRemarks(StringOrRefType value) {
        this.remarks = value;
    }

    /**
     * Gets the value of the semiMajorAxis property.
     * 
     * @return
     *     possible object is
     *     {@link MeasureType }
     *     
     */
    public MeasureType getSemiMajorAxis() {
        return semiMajorAxis;
    }

    /**
     * Sets the value of the semiMajorAxis property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeasureType }
     *     
     */
    public void setSemiMajorAxis(MeasureType value) {
        this.semiMajorAxis = value;
    }

    /**
     * Gets the value of the secondDefiningParameter property.
     * 
     * @return
     *     possible object is
     *     {@link SecondDefiningParameterType }
     *     
     */
    public SecondDefiningParameterType getSecondDefiningParameter() {
        return secondDefiningParameter;
    }

    /**
     * Sets the value of the secondDefiningParameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecondDefiningParameterType }
     *     
     */
    public void setSecondDefiningParameter(SecondDefiningParameterType value) {
        this.secondDefiningParameter = value;
    }

    @Override
    public String toString() {
        return "EllipsoidType{ " + "ellipsoidID = " + ellipsoidID
                + ", remarks = " + remarks + ", semiMajorAxis = "
                + semiMajorAxis + ", secondDefiningParameter = "
                + secondDefiningParameter + '}';
    }
}