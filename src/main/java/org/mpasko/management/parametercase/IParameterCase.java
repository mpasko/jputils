/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.management.parametercase;

import java.util.List;

/**
 *
 * @author marcin
 */
public interface IParameterCase {

    String name();

    String description();

    String category();

    int parametersCount();

    void doTheJob(List<String> paramValue);
}
