package org.mpasko.web.controlPanel.dao;

import org.mpasko.management.controlPanel.Controls;
import org.mpasko.management.parametercase.IParameterCase;
import org.mpasko.util.MapList;

import java.util.LinkedList;
import java.util.List;

public class PanelDao {
    public List<CategoryDao> categories;

    public static PanelDao getWebFormat() {
        MapList<String, IParameterCase> buckets = splitIntoBuckets(new Controls().getWebControls());
        PanelDao webFormat = new PanelDao();
        webFormat.categories = convertBucketsIntoCategories(buckets);
        return webFormat;
    }

    private static MapList<String, IParameterCase> splitIntoBuckets(List<IParameterCase> webControls) {
        MapList<String, IParameterCase> buckets = new MapList();
        webControls.stream().forEach(
                control -> buckets.add(control.category(), control)
        );
        return buckets;
    }

    private static List<CategoryDao> convertBucketsIntoCategories(MapList<String, IParameterCase> buckets) {
        List<CategoryDao> categoriesDao = new LinkedList<>();
        for(String category : buckets.keySet()) {
            LinkedList<IParameterCase> bucketContent = buckets.get(category);
            CategoryDao categoryDao = CategoryDao.convertCategory(category, bucketContent);
            categoriesDao.add(categoryDao);
        }
        return categoriesDao;
    }
}
