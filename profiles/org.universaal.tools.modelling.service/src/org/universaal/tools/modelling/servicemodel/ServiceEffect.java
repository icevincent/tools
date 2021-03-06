/**
 * 	Copyright 2013 SINTEF, http://www.sintef.no
 * 	
 * 	See the NOTICE file distributed with this work for additional 
 * 	information regarding copyright ownership
 * 	
 * 	Licensed under the Apache License, Version 2.0 (the "License");
 * 	you may not use this file except in compliance with the License.
 * 	You may obtain a copy of the License at
 * 	
 * 	  http://www.apache.org/licenses/LICENSE-2.0
 * 	
 * 	Unless required by applicable law or agreed to in writing, software
 * 	distributed under the License is distributed on an "AS IS" BASIS,
 * 	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 	See the License for the specific language governing permissions and
 * 	limitations under the License.
 */
package org.universaal.tools.modelling.servicemodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Service Effect</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.universaal.tools.modelling.servicemodel.ServiceEffect#getEffectType <em>Effect Type</em>}</li>
 *   <li>{@link org.universaal.tools.modelling.servicemodel.ServiceEffect#getEffectValue <em>Effect Value</em>}</li>
 *   <li>{@link org.universaal.tools.modelling.servicemodel.ServiceEffect#getPropertyPath <em>Property Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.universaal.tools.modelling.servicemodel.ServiceModelPackage#getServiceEffect()
 * @model
 * @generated
 */
public interface ServiceEffect extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Effect Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.universaal.tools.modelling.servicemodel.EffectType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Effect Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Effect Type</em>' attribute.
	 * @see org.universaal.tools.modelling.servicemodel.EffectType
	 * @see #setEffectType(EffectType)
	 * @see org.universaal.tools.modelling.servicemodel.ServiceModelPackage#getServiceEffect_EffectType()
	 * @model required="true"
	 * @generated
	 */
	EffectType getEffectType();

	/**
	 * Sets the value of the '{@link org.universaal.tools.modelling.servicemodel.ServiceEffect#getEffectType <em>Effect Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Effect Type</em>' attribute.
	 * @see org.universaal.tools.modelling.servicemodel.EffectType
	 * @see #getEffectType()
	 * @generated
	 */
	void setEffectType(EffectType value);

	/**
	 * Returns the value of the '<em><b>Effect Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Effect Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Effect Value</em>' attribute.
	 * @see #setEffectValue(String)
	 * @see org.universaal.tools.modelling.servicemodel.ServiceModelPackage#getServiceEffect_EffectValue()
	 * @model
	 * @generated
	 */
	String getEffectValue();

	/**
	 * Sets the value of the '{@link org.universaal.tools.modelling.servicemodel.ServiceEffect#getEffectValue <em>Effect Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Effect Value</em>' attribute.
	 * @see #getEffectValue()
	 * @generated
	 */
	void setEffectValue(String value);

	/**
	 * Returns the value of the '<em><b>Property Path</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Path</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Path</em>' containment reference.
	 * @see #setPropertyPath(PropertyPath)
	 * @see org.universaal.tools.modelling.servicemodel.ServiceModelPackage#getServiceEffect_PropertyPath()
	 * @model containment="true"
	 * @generated
	 */
	PropertyPath getPropertyPath();

	/**
	 * Sets the value of the '{@link org.universaal.tools.modelling.servicemodel.ServiceEffect#getPropertyPath <em>Property Path</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Path</em>' containment reference.
	 * @see #getPropertyPath()
	 * @generated
	 */
	void setPropertyPath(PropertyPath value);

} // ServiceEffect
