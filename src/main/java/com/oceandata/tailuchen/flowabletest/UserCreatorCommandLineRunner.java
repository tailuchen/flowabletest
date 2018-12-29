package com.oceandata.tailuchen.flowabletest;


import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.idm.api.IdmIdentityService;
import org.flowable.idm.api.Privilege;
import org.flowable.idm.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCreatorCommandLineRunner implements CommandLineRunner {

    protected final  RepositoryService repositoryService;

    protected final IdmIdentityService idmIdentityService;
    protected final   TaskService taskService;


    protected final  RuntimeService runtimeService;

    public UserCreatorCommandLineRunner(IdmIdentityService idmIdentityService, RepositoryService repositoryService, TaskService taskService, RuntimeService runtimeService) {
        this.repositoryService = repositoryService;
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.idmIdentityService = idmIdentityService;
    }




    @Override
    public void run(String... args) {
        createUserIfNotExists("flowfest");
        createUserIfNotExists("flowfest-actuator");
        createUserIfNotExists("flowfest-rest");

        if (idmIdentityService.createPrivilegeQuery().privilegeName("ROLE_REST").count() == 0) {
            Privilege restPrivilege = idmIdentityService.createPrivilege("ROLE_REST");
            idmIdentityService.addUserPrivilegeMapping(restPrivilege.getId(), "flowfest-rest");
        }

        if (idmIdentityService.createPrivilegeQuery().privilegeName("ROLE_ACTUATOR").count() == 0) {
            Privilege restPrivilege = idmIdentityService.createPrivilege("ROLE_ACTUATOR");
            idmIdentityService.addUserPrivilegeMapping(restPrivilege.getId(), "flowfest-actuator");
        }
    }

    protected void createUserIfNotExists(String username) {
        if (idmIdentityService.createUserQuery().userId(username).count() == 0) {
            User user = idmIdentityService.newUser(username);
            user.setPassword("test");
            idmIdentityService.saveUser(user);
        }
    }
}

