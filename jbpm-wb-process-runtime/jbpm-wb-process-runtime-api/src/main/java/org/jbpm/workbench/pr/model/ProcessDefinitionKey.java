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

package org.jbpm.workbench.pr.model;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jbpm.workbench.common.service.ItemKey;

@Portable
public class ProcessDefinitionKey implements ItemKey {

    private String serverTemplateId;
    private String deploymentId;
    private String processId;
    private String processDefName;

    public ProcessDefinitionKey(String serverTemplateId,
                                String deploymentId,
                                String processId) {
        this.serverTemplateId = serverTemplateId;
        this.deploymentId = deploymentId;
        this.processId = processId;
    }

    public ProcessDefinitionKey(String serverTemplateId,
                                String deploymentId,
                                String processId,
                                String processDefName) {
        this.serverTemplateId = serverTemplateId;
        this.deploymentId = deploymentId;
        this.processId = processId;
        this.processDefName = processDefName;
    }

    public ProcessDefinitionKey() {
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public String getProcessId() {
        return processId;
    }

    public String getProcessDefName() {
        if (processDefName != null) {
            return processDefName;
        }
        return processId;
    }

    public void setProcessDefName(String processDefName) {
        this.processDefName = processDefName;
    }

    public String getServerTemplateId() {
        return serverTemplateId;
    }
    
    public Boolean isValid() {
        if (serverTemplateId == null || serverTemplateId.trim().isEmpty()) {
            return false;
        }

        if (deploymentId == null || deploymentId.trim().isEmpty()) {
            return false;
        }

        if (processId == null || processId.trim().isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "ProcessDefinitionKey{" +
                "serverTemplateId='" + serverTemplateId + '\'' +
                ", deploymentId='" + deploymentId + '\'' +
                ", processId='" + processId + '\'' +
                ", processDefName='" + processDefName + '\'' +
                '}';
    }

    @Override
    @SuppressWarnings("PMD.AvoidMultipleUnaryOperators")
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.deploymentId != null ? this.deploymentId.hashCode() : 0);
        hash = ~~hash;
        hash = 97 * hash + (this.processId != null ? this.processId.hashCode() : 0);
        hash = ~~hash;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProcessDefinitionKey other = (ProcessDefinitionKey) obj;
        if (this.deploymentId == null ? other.deploymentId != null : !this.deploymentId.equals(other.deploymentId)) {
            return false;
        }
        if (this.processId == null ? other.processId != null : !this.processId.equals(other.processId)) {
            return false;
        }
        return true;
    }
}
