/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.console.parametercase;

/**
 *
 * @author marcin
 */
public interface IParameterCase {

    String name();

    String description();

    boolean hasParameter();

    void doTheJob(String paramValue);
}
