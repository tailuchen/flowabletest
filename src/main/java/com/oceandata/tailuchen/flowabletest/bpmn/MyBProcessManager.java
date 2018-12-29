package com.oceandata.tailuchen.flowabletest.bpmn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyBProcessManager {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
   private transient RepositoryService repositoryService;

    private String resourcePath = "myprocesses/";
    public String publishProcessDefintion(String xmlName) {
        String xmlFullPathName = resourcePath + xmlName;
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource(xmlFullPathName)
                .deploy();
        logger.info("add" + deployment.getId());
        return deployment.getId() ;

    }

    public List<String> getAllpublishProcessDefintion() {
        List<Deployment> deploymentList = repositoryService.createDeploymentQuery().orderByDeploymentId().desc().list();


        return deploymentList.parallelStream().map(x -> x.getId()).collect(Collectors.toList());

    }

    public String removeAllProcessDefintion() {
        List<Deployment> deploymentList = repositoryService.createDeploymentQuery().orderByDeploymentId().desc().list();

// repositoryService.deleteDeployment(did, true);
        int myCount = deploymentList.size();
        deploymentList.forEach(k -> {
            logger.info("tailuchen" + k.getId());
            repositoryService.deleteDeployment(k.getId(),true);
        });
        return "del" + myCount;
    }


}
