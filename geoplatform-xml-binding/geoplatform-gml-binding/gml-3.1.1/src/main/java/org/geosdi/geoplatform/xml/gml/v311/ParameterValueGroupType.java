//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-b10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.04.17 at 10:27:36 PM CEST 
//


package org.geosdi.geoplatform.xml.gml.v311;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;


/**
 * A group of related parameter values. The same group can be repeated more than once in a Conversion, Transformation, or higher level parameterValueGroup, if those instances contain different values of one or more parameterValues which suitably distinquish among those groups. This concrete complexType can be used for operation methods without using an Application Schema that defines operation-method-specialized element names and contents, especially for methods with only one instance. This complexType can be used, extended, or restricted for well-known operation methods, especially for methods with many instances. 
 * 
 * <p>Java class for ParameterValueGroupType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ParameterValueGroupType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml}AbstractGeneralParameterValueType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/gml}includesValue" maxOccurs="unbounded" minOccurs="2"/>
 *         &lt;element ref="{http://www.opengis.net/gml}valuesOfGroup"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParameterValueGroupType", propOrder = {
    "includesValue",
    "valuesOfGroup"
})
public class ParameterValueGroupType
    extends AbstractGeneralParameterValueType
    implements ToString
{

    @XmlElement(required = true)
    protected List<AbstractGeneralParameterValueType> includesValue;
    @XmlElement(required = true)
    protected OperationParameterGroupRefType valuesOfGroup;

    /**
     * Unordered set of composition associations to the parameter values and groups of values included in this group. Gets the value of the includesValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the includesValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIncludesValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AbstractGeneralParameterValueType }
     * 
     * 
     */
    public List<AbstractGeneralParameterValueType> getIncludesValue() {
        if (includesValue == null) {
            includesValue = new ArrayList<AbstractGeneralParameterValueType>();
        }
        return this.includesValue;
    }

    public boolean isSetIncludesValue() {
        return ((this.includesValue!= null)&&(!this.includesValue.isEmpty()));
    }

    public void unsetIncludesValue() {
        this.includesValue = null;
    }

    /**
     * Gets the value of the valuesOfGroup property.
     * 
     * @return
     *     possible object is
     *     {@link OperationParameterGroupRefType }
     *     
     */
    public OperationParameterGroupRefType getValuesOfGroup() {
        return valuesOfGroup;
    }

    /**
     * Sets the value of the valuesOfGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationParameterGroupRefType }
     *     
     */
    public void setValuesOfGroup(OperationParameterGroupRefType value) {
        this.valuesOfGroup = value;
    }

    public boolean isSetValuesOfGroup() {
        return (this.valuesOfGroup!= null);
    }

    public String toString() {
        final ToStringStrategy strategy = JAXBToStringStrategy.INSTANCE;
        final StringBuilder buffer = new StringBuilder();
        append(null, buffer, strategy);
        return buffer.toString();
    }

    public StringBuilder append(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        strategy.appendStart(locator, this, buffer);
        appendFields(locator, buffer, strategy);
        strategy.appendEnd(locator, this, buffer);
        return buffer;
    }

    public StringBuilder appendFields(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        super.appendFields(locator, buffer, strategy);
        {
            List<AbstractGeneralParameterValueType> theIncludesValue;
            theIncludesValue = (this.isSetIncludesValue()?this.getIncludesValue():null);
            strategy.appendField(locator, this, "includesValue", buffer, theIncludesValue);
        }
        {
            OperationParameterGroupRefType theValuesOfGroup;
            theValuesOfGroup = this.getValuesOfGroup();
            strategy.appendField(locator, this, "valuesOfGroup", buffer, theValuesOfGroup);
        }
        return buffer;
    }

    public void setIncludesValue(List<AbstractGeneralParameterValueType> value) {
        this.includesValue = null;
        List<AbstractGeneralParameterValueType> draftl = this.getIncludesValue();
        draftl.addAll(value);
    }

}
