/*
 *  Copyright 2005 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mybatis.generator.api;

import org.mybatis.generator.api.dom.xml.Document;

/**
 * @author Jeff Butler
 */
public class GeneratedXmlFile extends GeneratedFile {
    private Document document;

    private String fileName;

    private String targetPackage;

    private boolean isMergeable;
    
    private XmlFormatter xmlFormatter;

    /**
     * 
     * @param document
     * @param fileName
     * @param targetPackage
     * @param targetProject
     * @param isMergeable
     *            true if the file can be merged by the built in XML file
     *            merger.
     */
    public GeneratedXmlFile(Document document, String fileName,
            String targetPackage, String targetProject, boolean isMergeable,
            XmlFormatter xmlFormatter) {
        super(targetProject);
        this.document = document;
        this.fileName = fileName;
        this.targetPackage = targetPackage;
        this.isMergeable = isMergeable;
        this.xmlFormatter = xmlFormatter;
    }

    @Override
    public String getFormattedContent() {
        return xmlFormatter.getFormattedContent(document);
    }

    /**
     * @return Returns the fileName.
     */
    @Override
    public String getFileName() {
        return fileName;
    }

    /**
     * @return Returns the targetPackage.
     */
    @Override
    public String getTargetPackage() {
        return targetPackage;
    }

    @Override
    public boolean isMergeable() {
        return isMergeable;
    }
}
