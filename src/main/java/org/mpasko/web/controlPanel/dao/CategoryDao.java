package org.mpasko.web.controlPanel.dao;

import org.mpasko.management.parametercase.IParameterCase;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryDao {
    public String name;
    public List<ControlDao> controls;

    static CategoryDao convertCategory(String category, LinkedList<IParameterCase> controls) {
        CategoryDao categoryDao = new CategoryDao();
        categoryDao.name = category;
        categoryDao.controls = controls
                .stream()
                .map(control -> ControlDao.convertIntoWebFormat(control))
                .collect(Collectors.toList());
        return categoryDao;
    }
}
