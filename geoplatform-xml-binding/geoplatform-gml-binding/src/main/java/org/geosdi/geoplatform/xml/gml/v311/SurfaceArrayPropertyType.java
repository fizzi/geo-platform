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
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * A container for an array of surfaces. The elements are always contained in the array property, referencing geometry elements or arrays of geometry elements is not supported.
 * 
 * <p>Java class for SurfaceArrayPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SurfaceArrayPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/gml}_Surface" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SurfaceArrayPropertyType", propOrder = {
    "surface"
})
public class SurfaceArrayPropertyType {

    @XmlElementRef(name = "_Surface", namespace = "http://www.opengis.net/gml",
                   type = JAXBElement.class)
    private List<JAXBElement<? extends AbstractSurfaceType>> surface;

    public SurfaceArrayPropertyType() {
    }

    /**
     * @param surface the surface to set
     */
    public void setSurface(List<JAXBElement<? extends AbstractSurfaceType>> surface) {
        this.surface = surface;
    }

    /**
     * Gets the value of the surface property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the surface property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    get_Surface().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link AbstractSurfaceType }{@code >}
     * {@link JAXBElement }{@code <}{@link CompositeSurfaceType }{@code >}
     * {@link JAXBElement }{@code <}{@link PolyhedralSurfaceType }{@code >}
     * {@link JAXBElement }{@code <}{@link PolygonType }{@code >}
     * {@link JAXBElement }{@code <}{@link OrientableSurfaceType }{@code >}
     * {@link JAXBElement }{@code <}{@link TriangulatedSurfaceType }{@code >}
     * {@link JAXBElement }{@code <}{@link SurfaceType }{@code >}
     * {@link JAXBElement }{@code <}{@link TinType }{@code >}
     * 
     * 
     */
    public List<JAXBElement<? extends AbstractSurfaceType>> getSurface() {
        if (surface == null) {
            this.surface = new ArrayList<JAXBElement<? extends AbstractSurfaceType>>();
        }
        return this.surface;
    }

    @Override
    public String toString() {
        return "SurfaceArrayPropertyType{ " + "surface = " + surface + '}';
    }
}