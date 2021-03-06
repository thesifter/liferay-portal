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

package com.liferay.portlet.polls.lar;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.model.PollsVote;
import com.liferay.portlet.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.portlet.polls.service.permission.PollsPermission;
import com.liferay.portlet.polls.service.persistence.PollsChoiceExportActionableDynamicQuery;
import com.liferay.portlet.polls.service.persistence.PollsQuestionExportActionableDynamicQuery;
import com.liferay.portlet.polls.service.persistence.PollsVoteExportActionableDynamicQuery;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Bruno Farache
 * @author Marcellus Tavares
 * @author Mate Thurzo
 */
public class PollsPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "polls";

	public PollsPortletDataHandler() {
		setAlwaysExportable(true);
		setDataLocalized(true);
		setExportControls(
			new PortletDataHandlerBoolean(NAMESPACE, "questions", true, true),
			new PortletDataHandlerBoolean(NAMESPACE, "votes"));
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				PollsPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		PollsQuestionLocalServiceUtil.deleteQuestions(
			portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			PollsPermission.RESOURCE_NAME,
			portletDataContext.getScopeGroupId());

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ActionableDynamicQuery questionActionableDynamicQuery =
			new PollsQuestionExportActionableDynamicQuery(portletDataContext);

		questionActionableDynamicQuery.performActions();

		ActionableDynamicQuery choiceActionableDynamicQuery =
			new PollsChoiceExportActionableDynamicQuery(portletDataContext);

		choiceActionableDynamicQuery.performActions();

		if (portletDataContext.getBooleanParameter(
				PollsPortletDataHandler.NAMESPACE, "votes")) {

			ActionableDynamicQuery voteActionableDynamicQuery =
				new PollsVoteExportActionableDynamicQuery(portletDataContext);

			voteActionableDynamicQuery.performActions();
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			PollsPermission.RESOURCE_NAME,
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Element questionsElement = portletDataContext.getImportDataGroupElement(
			PollsQuestion.class);

		List<Element> questionElements = questionsElement.elements();

		for (Element questionElement : questionElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, questionElement);
		}

		Element choicesElement = portletDataContext.getImportDataGroupElement(
			PollsChoice.class);

		List<Element> choiceElements = choicesElement.elements();

		for (Element choiceElement : choiceElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, choiceElement);
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "votes")) {
			Element votesElement = portletDataContext.getImportDataGroupElement(
				PollsVote.class);

			List<Element> voteElements = votesElement.elements();

			for (Element voteElement : voteElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, voteElement);
			}
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext)
		throws Exception {

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		ActionableDynamicQuery choiceActionableDynamicQuery =
			new PollsChoiceExportActionableDynamicQuery(portletDataContext);

		manifestSummary.addModelCount(
			PollsChoice.class, choiceActionableDynamicQuery.performCount());

		ActionableDynamicQuery questionActionableDynamicQuery =
			new PollsQuestionExportActionableDynamicQuery(portletDataContext);

		manifestSummary.addModelCount(
			PollsQuestion.class, questionActionableDynamicQuery.performCount());

		ActionableDynamicQuery voteActionableDynamicQuery =
			new PollsVoteExportActionableDynamicQuery(portletDataContext);

		manifestSummary.addModelCount(
			PollsVote.class, voteActionableDynamicQuery.performCount());
	}

}