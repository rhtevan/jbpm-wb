/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jbpm.console.ng.pr.client.editors.definition.list;


import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.gwtmockito.GwtMockitoTestRunner;

import org.jboss.errai.common.client.api.Caller;

import org.jbpm.console.ng.bd.model.ProcessSummary;
import org.jbpm.console.ng.gc.client.experimental.grid.base.ExtendedPagedTable;
import org.jbpm.console.ng.gc.client.list.base.events.SearchEvent;

import org.jbpm.console.ng.pr.model.events.ProcessDefSelectionEvent;
import org.jbpm.console.ng.pr.service.ProcessRuntimeDataService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.client.mvp.PlaceStatus;
import org.uberfire.mocks.CallerMock;
import org.uberfire.mocks.EventSourceMock;
import org.uberfire.mvp.PlaceRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class ProcessDefinitionListPresenterTest {

    @Mock
    ProcessDefinitionListPresenter.ProcessDefinitionListView view;

    @Mock
    ExtendedPagedTable extendedPagedTable;

    @Mock
    protected PlaceManager placeManager;

    @Mock
    protected EventSourceMock<ProcessDefSelectionEvent> processDefSelectionEvent = new EventSourceMock<ProcessDefSelectionEvent>();

    Caller<ProcessRuntimeDataService> processRuntimeDataServiceCaller;

    @Mock
    ProcessRuntimeDataService processRuntimeDataService;

    @Mock
    HasData next;

    @InjectMocks
    ProcessDefinitionListPresenter presenter;

    @Before
    public void setup() {
        processRuntimeDataServiceCaller = new CallerMock<ProcessRuntimeDataService>(processRuntimeDataService);
        presenter.setProcessRuntimeDataService(processRuntimeDataServiceCaller);
        when(view.getListGrid()).thenReturn(extendedPagedTable);
        when(extendedPagedTable.getColumnSortList()).thenReturn(new ColumnSortList());

        when(next.getVisibleRange()).thenReturn(new Range(1, 1));
        presenter.getDataProvider().addDataDisplay(next);
    }

    @Test
    public void testSearchString() {
        final String textSearchStr = "textSearch";
        final SearchEvent searchEvent = new SearchEvent(textSearchStr);

        presenter.onSearchEvent(searchEvent);

        assertEquals(searchEvent.getFilter(), presenter.getTextSearchStr());
        verify(processRuntimeDataService).getProcessesByFilter(anyString(), anyString(), anyInt(), anyInt(), anyString(), anyBoolean());
    }

    @Test
    public void testProcessDefNameDefinitionPropagation(){
        String processDefName = "testProcessDefName";

        ProcessSummary processSummary = new ProcessSummary();
        processSummary.setProcessDefId("testProcessDefId");
        processSummary.setDeploymentId("testDeploymentId");
        processSummary.setProcessDefName(processDefName);

        when(placeManager.getStatus(any(PlaceRequest.class))).thenReturn(PlaceStatus.CLOSE);
        presenter.selectProcessDefinition(processSummary, true);

        verify(processDefSelectionEvent).fire(any(ProcessDefSelectionEvent.class));
        ArgumentCaptor<ProcessDefSelectionEvent> argument = ArgumentCaptor.forClass( ProcessDefSelectionEvent.class );
        verify( processDefSelectionEvent).fire(argument.capture());
        assertEquals( processDefName, argument.getValue().getProcessDefName() );
    }

}