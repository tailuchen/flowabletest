package com.oceandata.tailuchen.flowabletest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oceandata.tailuchen.flowabletest.bpmn.MyBProcessManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
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


    @RequestMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file){
        if(!file.isEmpty()){
            try {
                String fileName = myBProcessManager.getResourcePath() + "/" + file.getOriginalFilename();

                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
                out.write(file.getBytes());
                out.flush();
                out.close();
            }catch(FileNotFoundException e) {
                e.printStackTrace();
                return "上传失败,"+e.getMessage();
            }catch (IOException e) {
                e.printStackTrace();
                return "上传失败,"+e.getMessage();
            }

            return "上传成功";

        }else{

            return "上传失败，因为文件是空的.";

        }
    }

    /**

     * 多文件具体上传时间，主要是使用了MultipartHttpServletRequest和MultipartFile

     * @param request

     * @return

     */

    @RequestMapping(value="/batch/upload", method= RequestMethod.POST)
    public @ResponseBody
    String handleFileUpload(HttpServletRequest request){
        List<MultipartFile> files =((MultipartHttpServletRequest)request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i =0; i< files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream =
                            new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    stream =  null;
                    return "You failed to upload " + i + " =>" + e.getMessage();
                }
            } else {
                return "You failed to upload " + i + " becausethe file was empty.";
            }
        }
        return "upload successful";

    }

}
