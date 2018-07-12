/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.web.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import spark.ResponseTransformer;

/**
 *
 * @author marcin
 */
public class JsonTransformer implements ResponseTransformer {

    public JsonTransformer() {
    }

    @Override
    public String render(Object o) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper = objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        return objectMapper.writer().writeValueAsString(o);
    }

}
