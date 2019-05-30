package org.mpasko.web.quizDownload;

import org.mpasko.commons.DictEntry;
import org.mpasko.exams.ExamBuilder;
import org.mpasko.exams.ExamItem;
import org.mpasko.quizgeneration.Quiz;
import org.mpasko.quizgeneration.QuizFormatter;
import org.mpasko.quizgeneration.builder.QuizBuilderFactory;
import org.mpasko.util.Randomation;
import org.mpasko.web.DataSourceCache;
import org.mpasko.web.legacyApi.generateExamData.ExamsPreparer;
import org.mpasko.web.server.StringDTO;
import org.mpasko.web.textpreview.FileIdMap;

import java.util.LinkedList;
import java.util.List;

public class QuizPreparer {

    private final FileIdMap idCache;
    private final DataSourceCache data;

    public QuizPreparer(FileIdMap idCache, DataSourceCache data) {
        this.idCache = idCache;
        this.data = data;
    }

    public StringDTO prepareQuiz(String id, String activity, String phase) {
        String path = idCache.search(id);
        List<ExamItem> quizData = new ExamsPreparer(data).generateExamByPath(path, activity, phase);
        List<DictEntry> shrinkedDict = shrinkedSubset(data.dataSources.getGlobalDict().items(), 1000);
        List<ExamItem> answerPool = new ExamBuilder().dictionaryIntoExam(shrinkedDict, activity);
        Quiz quiz = new QuizBuilderFactory()
                .getBuilder(activity, phase)
                .buildQuiz(quizData, answerPool);
        return new StringDTO(new QuizFormatter().format(quiz));
    }

    private List<DictEntry> shrinkedSubset(List<DictEntry> source, int number) {
        LinkedList<DictEntry> result = new LinkedList<>();
        for (int index = 0; index < number; ++index) {
            result.add(Randomation.choose(source));
        }
        return result;
    }
}
