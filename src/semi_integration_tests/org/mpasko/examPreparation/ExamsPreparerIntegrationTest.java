package org.mpasko.examPreparation;

import org.mpasko.web.legacyApi.generateExamData.ExamsPreparer;
import org.junit.Test;
import org.mpasko.japanese.runners.workflow.IDataSource;

public class ExamsPreparerIntegrationTest {

    @Test
    public void it_should_not_use_all_chached_whitelists() {
        System.out.println("it_should_not_use_all_chached_whitelists");
        IDataSource dataSourceMock = new DataSourceMock();
        ExamsPreparer testable = new ExamsPreparer(dataSourceMock);
    }
}