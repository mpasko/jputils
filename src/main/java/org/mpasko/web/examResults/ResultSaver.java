package org.mpasko.web.examResults;

import org.mpasko.exams.Remover;
import org.mpasko.japanese.runners.workflow.IDataSource;
import org.mpasko.util.Filesystem;
import org.mpasko.web.DataSourceCache;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ResultSaver {

    private final IDataSource data;

    public ResultSaver(IDataSource data) {
        this.data = data;
    }

    public String saveResults(String type, String id, ResultData content) {
        final Remover remover = new Remover(data.getGlobalDictionary());
        try {
            remover.removeRedundancy("black", type, content.correct);
            remover.removeRedundancy("white", type, content.incorrect);
        }catch(RuntimeException ex){
            System.out.println(ex.getMessage());
        }
        saveGeneric(Remover.switchSourcePath("black", type), id, content.incorrect);
        saveGeneric(Remover.switchSourcePath("white", type), id, content.correct);
        data.reloadDataSources();
        return "";
    }

    private void saveGeneric(String directory, String id, String content) {
        if (content.length() > 3) {
            String filename = directory + id + formatTimestamp() + ".txt";
            new Filesystem().saveFile(filename, content);
        }
    }

    private String formatTimestamp() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HHmmss");
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        return reportDate;
    }
}
