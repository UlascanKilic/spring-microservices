package com.ulascan.serverservice.service.session;

import com.ulascan.serverservice.dto.scene.*;
import com.ulascan.serverservice.dto.scene.SceneResponseDTO;
import com.ulascan.serverservice.dto.scene.session.*;
import com.ulascan.serverservice.entity.EventEntity;
import com.ulascan.serverservice.entity.SessionEntity;
import com.ulascan.serverservice.repository.ISessionRepository;
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
public class SessionSceneService extends AbstractSceneService {
    private final ISessionRepository repository;

    private final ModelConverter modelConverter;


    @Autowired
    public SessionSceneService(ISessionRepository repository,
                               ModelConverter modelConverter){
        this.repository = repository;
        this.modelConverter = modelConverter;
    }

    @Override
    public List<SceneResponseDTO> getAll() {
        List<SessionEntity> sessionEntities = repository.findByActiveTrue();
        List<SessionResponseDTO> sessionResponseDTOs = modelConverter.mapList(sessionEntities, SessionResponseDTO.class);
        return new ArrayList<>(sessionResponseDTOs);
    }

    @Override
    public CreateResponseDTO create(SceneRequestDTO sceneRequestDTO) {

        SessionRequestDTO requestDTO = (SessionRequestDTO) sceneRequestDTO;

        validateScene(requestDTO);

        return CreateSessionResponseDTO
                .builder()
                .password(repository
                        .save(modelConverter
                                .dtoToEntity(requestDTO, SessionEntity.builder().build()))
                        .getScenePassword())
                .build();
    }

    @Override
    public SceneResponseDTO getByName(NameDTO nameDTO) {
        SessionEntity session = getSceneByName(nameDTO.getName());
        return modelConverter.entityToDTO(session); //TODO null gelirse kendi hatalarından birini fırlat
    }

    @Override
    public SessionEntity getSceneByName(String name) {
        return repository.findBySessionName(name);
    }

    @Override
    public SceneResponseDTO joinWithPassword(PasswordDTO passwordDTO) {
        SessionEntity session = getScenePassword(passwordDTO.getPassword());
        //TODO: hataları özelleştir live değilse, active değilse vs
        if(session != null && session.isActive()){
            return modelConverter.entityToDTO(session);
        }
        else {
            throw new BadRequestException(Error.SESSION_NOT_FOUND.getErrorCode(),
                    Error.SESSION_NOT_FOUND.getErrorMessage());
        }
    }

    @Override
    public SessionEntity getScenePassword(String password) {
        return repository.findByScenePassword(password);
    }

    @Override
    public void join(JoinDTO joinDTO) {
        SessionEntity session = getSceneByName(joinDTO.getName());

        if(session == null){
            throw new BadRequestException(Error.SESSION_NOT_FOUND.getErrorCode(),
                    Error.SESSION_NOT_FOUND.getErrorMessage());
        }
        else if(!Objects.equals(session.getScenePassword(), joinDTO.getPassword()))
        {
            throw new BadRequestException(Error.SCENE_PASSWORD_DOESNT_MATCH.getErrorCode(),
                    Error.SCENE_PASSWORD_DOESNT_MATCH.getErrorMessage());
        }
        else if(!session.isActive())
        {
            throw new BadRequestException(Error.SESSION_IS_NOT_ACTIVE.getErrorCode(),
                    Error.SESSION_IS_NOT_ACTIVE.getErrorMessage());
        }
    }

    @Override
    public SessionEntity findFirstByActiveFalse() {
        return repository.findFirstByActiveFalse();
    }

    @Override
    public void validateScene(SceneRequestDTO sceneRequestDTO) {
        //TODO hata isimlendirmelerini düzelt
        SessionRequestDTO dto = (SessionRequestDTO) sceneRequestDTO;
        if(getSceneByName(dto.getSessionName()) != null){
            throw new BadRequestException(Error.DUPLICATE_SCENE_NAME.getErrorCode(),
                    Error.DUPLICATE_SCENE_NAME.getErrorMessage());
        }

    }
}
