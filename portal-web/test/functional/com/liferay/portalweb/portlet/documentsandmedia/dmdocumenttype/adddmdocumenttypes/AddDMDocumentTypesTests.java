/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portalweb.portlet.documentsandmedia.dmdocumenttype.adddmdocumenttypes;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocumenttype.adddmdocumenttype.AddDMDocumentType1Test;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocumenttype.adddmdocumenttype.AddDMDocumentType2Test;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocumenttype.adddmdocumenttype.AddDMDocumentType3Test;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocumenttype.adddmdocumenttype.TearDownDMDocumentTypeTest;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.addportletdm.AddPageDMTest;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.addportletdm.AddPortletDMTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDMDocumentTypesTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageDMTest.class);
		testSuite.addTestSuite(AddPortletDMTest.class);
		testSuite.addTestSuite(AddDMDocumentType1Test.class);
		testSuite.addTestSuite(AddDMDocumentType2Test.class);
		testSuite.addTestSuite(AddDMDocumentType3Test.class);
		testSuite.addTestSuite(ViewDMDocumentTypesTest.class);
		testSuite.addTestSuite(TearDownDMDocumentTypeTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}