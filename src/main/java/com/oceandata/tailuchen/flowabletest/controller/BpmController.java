package com.oceandata.tailuchen.flowabletest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oceandata.tailuchen.flowabletest.bpmn.MyBProcessManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/bpm")
@Controller
public class BpmController {
    protected final Log logger = LogFactory.getLog(getClass());
    private String message = "Hello World";
    @Autowired
    MyBProcessManager myBProcessManager;


    @ResponseBody
    @RequestMapping(value = "/add",produces ="application/json")
    public String publishPr() throws JsonProcessingException {

        String  kk = myBProcessManager.publishProcessDefintion("task2.bpmn20.xml");
        ObjectMapper mapper = new ObjectMapper();


        String s = mapper.writeValueAsString(kk);
        return s;

    }

    @ResponseBody
    @RequestMapping(value = "/del")
    public String delPr()  throws JsonProcessingException  {
        String kk = myBProcessManager.removeAllProcessDefintion();
        logger.info(kk);
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(kk);
        return s;
    }
    @ResponseBody
    @RequestMapping(value = "/list" )
    public List<String> listPr()  throws JsonProcessingException  {

        return myBProcessManager.getAllpublishProcessDefintion();

    }
    @RequestMapping({"/","/index",""})
    public String index(Map<String, Object> model) {
        model.put("message", this.message);
        return "/bpm/index";

    }
}
