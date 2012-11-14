/*

   Derby - Class org.apache.derbyTesting.functionTests.tests.engine.ReadMeFilesTest


   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */

package org.apache.derbyTesting.functionTests.tests.engine;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import junit.framework.Test;

import org.apache.derbyTesting.functionTests.util.PrivilegedFileOpsForTests;
import org.apache.derbyTesting.junit.BaseJDBCTestCase;
import org.apache.derbyTesting.junit.TestConfiguration;


/**
 * Tests related to the 3 Derby readme files. These readmes warn users against 
 *   editing/deleting any of the files in the database directories. The 
 *   location of the readme files are  
 *   1)at the db level directory, 
 *   2)in seg0 directory and 
 *   3)in the log directocy.
 * All the three readme files are named README_DONT_TOUCH_FILES.txt
 */
public class ReadMeFilesTest extends BaseJDBCTestCase {
    /**
    The readme file cautioning users against touching the files in
    the database directory 
    */
    private static final String DB_README_FILE_NAME = "README_DONT_TOUCH_FILES.txt";

    public ReadMeFilesTest(String name) {
        super(name);
    }

    public static Test suite() {
        Test suite = TestConfiguration.embeddedSuite(ReadMeFilesTest.class);
        return TestConfiguration.singleUseDatabaseDecorator(suite);
    }

    public void testReadMeFilesExist() throws IOException, SQLException {
        getConnection();
        TestConfiguration currentConfig = TestConfiguration.getCurrent();
        String dbPath = currentConfig.getDatabasePath(currentConfig.getDefaultDatabaseName());
        lookForReadmeFile(dbPath);
        lookForReadmeFile(dbPath+File.separator+"seg0");
        lookForReadmeFile(dbPath+File.separator+"log");
    }

    private void lookForReadmeFile(String path) throws IOException {
        File readmeFile = new File(path,
            DB_README_FILE_NAME);
        assertTrue(readmeFile + "doesn't exist", PrivilegedFileOpsForTests.exists(readmeFile));
    }
}
 