package org.mpasko.web.examResults;

public class ResultData {
    public String correct;
    public String incorrect;
    public String getTotal() {
        return correct + "\n" + incorrect;
    }
}
