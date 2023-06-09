package com.ulascan.serverservice.util;

import com.ulascan.serverservice.enums.SceneType;
import com.ulascan.serverservice.service.AbstractSceneService;
import com.ulascan.serverservice.service.environment.EnvironmentSceneService;
import com.ulascan.serverservice.service.event.EventSceneService;
import com.ulascan.serverservice.service.session.SessionSceneService;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class SceneServiceProvider {

    private ApplicationContext applicationContext;

    @Autowired
    SceneServiceProvider(ApplicationContext context){
        applicationContext = context;
    }

    public AbstractSceneService getSceneService(SceneType type)
    {
        AbstractSceneService service;

        switch (type)
        {
            case ENVIRONMENT:
                service = applicationContext.getBean(EnvironmentSceneService.class);
                break;
            case EVENT:
                service = applicationContext.getBean(EventSceneService.class);
                break;
            case SESSION:
                service = applicationContext.getBean(SessionSceneService.class);
                break;
            default:
                throw new NotFoundException("No such a scene service");
        }

        return service;
    }
}
