package com.ulascan.serverservice.service.event;

import com.ulascan.serverservice.dto.scene.*;
import com.ulascan.serverservice.dto.scene.event.*;
import com.ulascan.serverservice.dto.scene.SceneResponseDTO;
import com.ulascan.serverservice.entity.*;
import com.ulascan.serverservice.repository.IEventRepository;
import com.ulascan.serverservice.repository.ISceneRepository;
import com.ulascan.serverservice.repository.IServerRepository;
import com.ulascan.serverservice.service.AbstractSceneService;
import com.ulascan.serverservice.util.exception.BadRequestException;
import com.ulascan.serverservice.util.exception.Error;
import com.ulascan.serverservice.util.mapper.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EventSceneService extends AbstractSceneService {

    private final IEventRepository repository;

    private final ModelConverter modelConverter;

    @Autowired
    public EventSceneService(IEventRepository repository,
                             ModelConverter modelConverter,
                             IServerRepository serverRepository,
                             ISceneRepository sceneRepository){
        super(serverRepository,sceneRepository);
        this.repository = repository;
        this.modelConverter = modelConverter;
    }

    @Override
    public List<SceneResponseDTO> getAll() {
        List<EventEntity> eventEntities = repository.findAll();
        List<EventResponseDTO> eventResponseDTOs = modelConverter.mapList(eventEntities, EventResponseDTO.class);
        return new ArrayList<>(eventResponseDTOs);
    }

    @Override
    public SceneResponseDTO getByName(NameDTO nameDTO) {
        EventEntity event = getSceneByName(nameDTO.getName());
        return modelConverter.entityToDTO(event);
    }

    @Override
    public CreateResponseDTO create(SceneRequestDTO sceneRequestDTO) {
        EventRequestDTO eventRequestDTO = (EventRequestDTO) sceneRequestDTO;

        validateScene(eventRequestDTO);

        return CreateEventResponseDTO
                .builder()
                .password(repository
                        .save(modelConverter
                                .dtoToEntity(eventRequestDTO, EventEntity.builder().build()))
                        .getScenePassword())
                .build();
    }

    @Override
    public EventEntity getSceneByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public SceneResponseDTO joinWithPassword(PasswordDTO passwordDTO) {
        EventEntity event = getScenePassword(passwordDTO.getPassword());

        //TODO: hataları özelleştir live değilse, active değilse vs
        if(event != null && event.isLive() && event.isActive()){
            return modelConverter.entityToDTO(event);
        }
        else {
            throw new BadRequestException(Error.EVENT_NOT_FOUND.getErrorCode(),
                    Error.EVENT_NOT_FOUND.getErrorMessage());
        }
    }

    @Override
    public EventEntity getScenePassword(String password) {
        return repository.findByScenePassword(password);
    }

    @Override
    public EventEntity findFirstByActiveFalse() {
        return repository.findFirstByActiveFalseAndLiveTrue();
    }

    @Override
    public void join(JoinDTO joinDTO) {
        EventEntity event = getSceneByName(joinDTO.getName());

        if(event == null){
            throw new BadRequestException(Error.EVENT_NOT_FOUND.getErrorCode(),
                    Error.EVENT_NOT_FOUND.getErrorMessage());
        }
        else if(!Objects.equals(event.getScenePassword(), joinDTO.getPassword()))
        {
            throw new BadRequestException(Error.SCENE_PASSWORD_DOESNT_MATCH.getErrorCode(),
                    Error.SCENE_PASSWORD_DOESNT_MATCH.getErrorMessage());
        }
        else if(!event.isLive() && !event.isActive())
        {
            throw new BadRequestException(Error.EVENT_IS_NOT_LIVE.getErrorCode(),
                    Error.EVENT_IS_NOT_LIVE.getErrorMessage());
        }
    }

    public void startEvent(EventNameDTO eventName) {
        setEventLive(eventName.getName(), true);
    }


    public void stopEvent(EventNameDTO eventName) {
        setEventLive(eventName.getName(), false);
    }

    @Override
    public void validateScene(SceneRequestDTO sceneRequestDTO) {

        if(findFreeServer())
            throw new BadRequestException(Error.NO_FREE_SERVER_FOUND.getErrorCode(),
                    Error.NO_FREE_SERVER_FOUND.getErrorMessage());

        EventRequestDTO requestDTO = (EventRequestDTO) sceneRequestDTO;

        if(getSceneByName(requestDTO.getName()) != null){
            throw new BadRequestException(Error.DUPLICATE_SCENE_NAME.getErrorCode(),
                    Error.DUPLICATE_SCENE_NAME.getErrorMessage());
        }

    }

    @Override
    public void delete(Scene scene) {
        EventEntity eventEntity = (EventEntity) scene;

        Server server = eventEntity.getServer();

        if (server != null) {
            server.setScene(null);
            serverRepository.save(server);
        }

        repository.delete(eventEntity);
    }

    @Override
    public void setSceneFree(Scene scene) {
        EventEntity eventEntity = (EventEntity) scene;
        eventEntity.setActive(false);
        eventEntity.setServer(null);
        repository.save(eventEntity);
    }

    private void setEventLive(String eventName ,boolean isLive){
        EventEntity event = getSceneByName(eventName);

        if(event != null){
            event.setLive(isLive);
            repository.save(event);
        }
        else {
            throw new BadRequestException(Error.EVENT_NOT_FOUND.getErrorCode(),
                    Error.EVENT_NOT_FOUND.getErrorMessage());
        }
    }
















}
