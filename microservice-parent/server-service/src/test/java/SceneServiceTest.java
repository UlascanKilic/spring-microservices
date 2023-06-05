import com.ulascan.serverservice.dto.JoinSceneDTO;
import com.ulascan.serverservice.dto.SceneByUserRequestDTO;
import com.ulascan.serverservice.dto.SceneByTypeRequestDTO;
import com.ulascan.serverservice.dto.SceneByUnityNameRequestDTO;
import com.ulascan.serverservice.dto.SceneRequestDTO;
import com.ulascan.serverservice.dto.SceneResponseDTO;
import com.ulascan.serverservice.dto.StartSceneResponseDTO;
import com.ulascan.serverservice.entity.Scene;
import com.ulascan.serverservice.enums.DefaultUnityScenes;
import com.ulascan.serverservice.enums.SceneType;
import com.ulascan.serverservice.enums.UnityScene;
import com.ulascan.serverservice.exception.BadRequestException;
import com.ulascan.serverservice.exception.Error;
import com.ulascan.serverservice.repository.SceneRepository;
import com.ulascan.serverservice.repository.ServerRepository;
import com.ulascan.serverservice.service.SceneService;
import com.ulascan.serverservice.service.ServerService;
import com.ulascan.serverservice.util.Constants;
import com.ulascan.serverservice.util.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.anyList;

class SceneServiceTest {

    @Mock
    private SceneRepository sceneRepository;

    @Mock
    private ServerService serverService;

    @Mock
    private ServerRepository serverRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private SceneService sceneService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllScenes() {
        // Mock the repository to return a list of scenes
        List<Scene> sceneList = new ArrayList<>();
        sceneList.add(new Scene());
        when(sceneRepository.findByActiveTrue()).thenReturn(sceneList);

        // Mock the mapper to map the scene entities to DTOs
        List<SceneResponseDTO> expectedDTOs = new ArrayList<>();
        expectedDTOs.add(new SceneResponseDTO());
        when(mapper.mapList(sceneList, SceneResponseDTO.class)).thenReturn(expectedDTOs);

        // Call the service method
        List<SceneResponseDTO> actualDTOs = sceneService.getAllScenes();

        // Verify the results
        assertEquals(expectedDTOs, actualDTOs);
        verify(sceneRepository, times(1)).findByActiveTrue();
        verify(mapper, times(1)).mapList(sceneList, SceneResponseDTO.class);
    }

    @Test
    void testStartScene_WithValidData_ShouldReturnStartSceneResponseDTO() {
        // Arrange
        SceneRequestDTO sceneRequestDTO = new SceneRequestDTO();
        sceneRequestDTO.setUnityScene(UnityScene.ClassroomScene);
        sceneRequestDTO.setSceneName("Test Scene");
        sceneRequestDTO.setHostFirstName("John");
        sceneRequestDTO.setHostLastName("Doe");
        sceneRequestDTO.setHostEmail("john.doe@example.com");
        sceneRequestDTO.setSceneType(SceneType.DEFAULT);
        sceneRequestDTO.setMaxUserCapacity(10);
        sceneRequestDTO.setPrivateScene(true);
        // Set the additional fields

        // Mock the behavior of serverService.findFreeServer() to return true (free server available)
        when(serverService.findFreeServer()).thenReturn(true);

        // Create a valid Scene object to be returned by the dtoToEntity method
        Scene scene = new Scene();
        scene.setScenePassword("generated_password");

        // Mock the behavior of mapper.dtoToEntity() to return the valid Scene object
        when(mapper.dtoToEntity(eq(sceneRequestDTO), any(Scene.class))).thenReturn(scene);

        // Mock the behavior of sceneRepository.save() to return the scene object
        when(sceneRepository.save(any(Scene.class))).thenReturn(scene);

        // Act
        StartSceneResponseDTO responseDTO = sceneService.startScene(sceneRequestDTO);

        // Assert
        assertNotNull(responseDTO);
        assertEquals("generated_password", responseDTO.getScenePassword());
        // Verify that sceneRepository.save() is called with the correct Scene object
        verify(sceneRepository).save(any(Scene.class));
    }

    @Test
    void testJoinScene_ExistingSceneAndPasswordMatch() {
        // Mock the joinSceneDTO
        JoinSceneDTO joinSceneDTO = new JoinSceneDTO();
        joinSceneDTO.setSceneName("TestScene");
        joinSceneDTO.setPassword("123456");

        // Mock the sceneRepository to return an existing scene with matching password
        Scene scene = new Scene();
        scene.setScenePassword("123456");
        when(sceneRepository.getSceneBySceneName(joinSceneDTO.getSceneName())).thenReturn(scene);

        // Call the service method
        assertDoesNotThrow(() -> sceneService.joinScene(joinSceneDTO));

        // Verify the results
        verify(sceneRepository, times(1)).getSceneBySceneName(joinSceneDTO.getSceneName());
    }

    @Test
    void testJoinScene_NonExistingScene() {
        // Mock the joinSceneDTO
        JoinSceneDTO joinSceneDTO = new JoinSceneDTO();
        joinSceneDTO.setSceneName("NonExistingScene");
        joinSceneDTO.setPassword("123456");

        // Mock the sceneRepository to return null (non-existing scene)
        when(sceneRepository.getSceneBySceneName(joinSceneDTO.getSceneName())).thenReturn(null);

        // Call the service method and verify that it throws a BadRequestException
        assertThrows(BadRequestException.class, () -> sceneService.joinScene(joinSceneDTO),
                "Expected BadRequestException to be thrown.");

        // Verify the results
        verify(sceneRepository, times(1)).getSceneBySceneName(joinSceneDTO.getSceneName());
    }

    @Test
    void testJoinScene_PasswordDoesntMatch() {
        // Mock the joinSceneDTO
        JoinSceneDTO joinSceneDTO = new JoinSceneDTO();
        joinSceneDTO.setSceneName("TestScene");
        joinSceneDTO.setPassword("123456");

        // Mock the sceneRepository to return an existing scene with non-matching password
        Scene scene = new Scene();
        scene.setScenePassword("654321");
        when(sceneRepository.getSceneBySceneName(joinSceneDTO.getSceneName())).thenReturn(scene);

        // Call the service method and verify that it throws a BadRequestException
        assertThrows(BadRequestException.class, () -> sceneService.joinScene(joinSceneDTO),
                "Expected BadRequestException to be thrown.");

        // Verify the results
        verify(sceneRepository, times(1)).getSceneBySceneName(joinSceneDTO.getSceneName());
    }

    @Test
    void testGetActiveScenesByType() {
        // Mock the dto
        SceneByTypeRequestDTO dto = new SceneByTypeRequestDTO();
        dto.setSceneType(SceneType.DEFAULT);

        // Mock the repository to return a list of scenes of the given type
        List<Scene> sceneList = new ArrayList<>();
        sceneList.add(new Scene());
        when(sceneRepository.findByActiveTrueAndSceneType(dto.getSceneType())).thenReturn(sceneList);

        // Mock the mapper to map the scene entities to DTOs
        List<SceneResponseDTO> expectedDTOs = new ArrayList<>();
        expectedDTOs.add(new SceneResponseDTO());
        when(mapper.mapList(sceneList, SceneResponseDTO.class)).thenReturn(expectedDTOs);

        // Call the service method
        List<SceneResponseDTO> actualDTOs = sceneService.getActiveScenesByType(dto);

        // Verify the results
        assertEquals(expectedDTOs, actualDTOs);
        verify(sceneRepository, times(1)).findByActiveTrueAndSceneType(dto.getSceneType());
        verify(mapper, times(1)).mapList(sceneList, SceneResponseDTO.class);
    }

    @Test
    void testFilterChainForStartScene_FreeServerAvailable_NoExceptionsThrown() {
        // Arrange
        SceneRequestDTO sceneRequestDTO = new SceneRequestDTO();
        when(serverService.findFreeServer()).thenReturn(true);

        // Create a valid Scene object to be returned by the dtoToEntity method
        Scene scene = new Scene();
        scene.setScenePassword("generated_password");

        // Mock the behavior of mapper.dtoToEntity() to return the valid Scene object
        when(mapper.dtoToEntity(eq(sceneRequestDTO), any(Scene.class))).thenReturn(scene);

        // Mock the behavior of sceneRepository.save() to return the scene object
        when(sceneRepository.save(any(Scene.class))).thenReturn(scene);
        // Act & Assert
        assertDoesNotThrow(() -> sceneService.startScene(sceneRequestDTO));

        // Verify
        verify(serverService, times(1)).findFreeServer();
        verifyNoMoreInteractions(serverService);
    }

    @Test
    void testFilterChainForStartScene_NoFreeServerAvailable_BadRequestExceptionThrown() {
        // Arrange
        SceneRequestDTO sceneRequestDTO = new SceneRequestDTO();
        when(serverService.findFreeServer()).thenReturn(false);

        // Act & Assert
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> sceneService.startScene(sceneRequestDTO));
        assertEquals(Error.NO_FREE_SERVER_FOUND.getErrorCode(), exception.getCode());
        assertEquals(Error.NO_FREE_SERVER_FOUND.getErrorMessage(), exception.getMessage());

        // Verify
        verify(serverService, times(1)).findFreeServer();
        verifyNoMoreInteractions(serverService);
    }

    @Test
    void testFilterChainForStartScene_WhenDuplicateSceneName_ShouldThrowBadRequestException() {
        // Arrange
        SceneRequestDTO sceneRequestDTO = new SceneRequestDTO();
        sceneRequestDTO.setSceneName("TestScene");

        when(serverService.findFreeServer()).thenReturn(true);
        when(sceneRepository.findBySceneName("TestScene")).thenReturn(Optional.of(new Scene()));

        // Act and Assert
        assertThrows(BadRequestException.class, () -> sceneService.startScene(sceneRequestDTO));

        verify(serverService, times(1)).findFreeServer();
        verify(sceneRepository, times(1)).findBySceneName("TestScene");
    }

    @Test
    void testFilterChainForStartScene_WhenHostAlreadyExists_ShouldThrowBadRequestException() {
        // Arrange
        SceneRequestDTO sceneRequestDTO = new SceneRequestDTO();
        sceneRequestDTO.setHostEmail("john.doe@example.com");

        when(serverService.findFreeServer()).thenReturn(true);
        when(sceneRepository.findByHostEmail("john.doe@example.com")).thenReturn(Optional.of(new Scene()));

        // Act and Assert
        assertThrows(BadRequestException.class, () -> sceneService.startScene(sceneRequestDTO));

        verify(serverService, times(1)).findFreeServer();
        verify(sceneRepository, times(1)).findByHostEmail("john.doe@example.com");
    }

    @Test
    void testGetScenesByUser_WithValidData_ShouldReturnSceneResponseDTOList() {
        // Arrange
        String hostEmail = "john.doe@example.com";
        SceneByUserRequestDTO dto = new SceneByUserRequestDTO(hostEmail);

        // Create sample Scene objects to be returned by the repository
        Scene scene1 = new Scene();
        scene1.setSceneName("Scene 1");

        Scene scene2 = new Scene();
        scene2.setSceneName("Scene 2");

        List<Scene> sceneList = Arrays.asList(scene1, scene2);

        // Mock the behavior of sceneRepository.findAllByHostEmail() to return the sample sceneList
        when(sceneRepository.findAllByHostEmail(hostEmail)).thenReturn(sceneList);

        // Create sample SceneResponseDTO objects to be returned by the mapper
        SceneResponseDTO dto1 = new SceneResponseDTO();
        dto1.setSceneName("Scene 1");

        SceneResponseDTO dto2 = new SceneResponseDTO();
        dto2.setSceneName("Scene 2");

        List<SceneResponseDTO> expectedDTOList = Arrays.asList(dto1, dto2);

        // Mock the behavior of mapper.mapList() to return the sample expectedDTOList
        when(mapper.mapList(sceneList, SceneResponseDTO.class)).thenReturn(expectedDTOList);

        // Act
        List<SceneResponseDTO> result = sceneService.getScenesByUser(dto);

        // Assert
        assertEquals(expectedDTOList, result);
    }

    @Test
    void testGetActiveScenesByUnityName_WithValidUnityScene_ShouldReturnSceneResponseDTOList() {
        // Arrange
        SceneByUnityNameRequestDTO requestDTO = new SceneByUnityNameRequestDTO();
        requestDTO.setUnityScene(UnityScene.ClassroomScene);

        Scene scene1 = new Scene();
        scene1.setSceneName("Scene 1");

        Scene scene2 = new Scene();
        scene2.setSceneName("Scene 2");

        List<Scene> sceneList = Arrays.asList(scene1, scene2);

        when(sceneRepository.findByActiveTrueAndUnityScene(requestDTO.getUnityScene())).thenReturn(sceneList);

        SceneResponseDTO responseDTO1 = new SceneResponseDTO();
        responseDTO1.setSceneName("Scene 1");

        SceneResponseDTO responseDTO2 = new SceneResponseDTO();
        responseDTO2.setSceneName("Scene 2");

        List<SceneResponseDTO> expectedResponse = Arrays.asList(responseDTO1, responseDTO2);

        when(mapper.mapList(sceneList, SceneResponseDTO.class)).thenReturn(expectedResponse);

        // Act
        List<SceneResponseDTO> actualResponse = sceneService.getActiveScenesByUnityName(requestDTO);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(sceneRepository, times(1)).findByActiveTrueAndUnityScene(requestDTO.getUnityScene());
        verify(mapper, times(1)).mapList(sceneList, SceneResponseDTO.class);
    }

    @Test
    void testGetActiveScenesByUnityName_WithInvalidUnityScene_ShouldReturnEmptyList() {
        // Arrange
        SceneByUnityNameRequestDTO requestDTO = new SceneByUnityNameRequestDTO();
        requestDTO.setUnityScene(null);

        when(sceneRepository.findByActiveTrueAndUnityScene(requestDTO.getUnityScene())).thenReturn(null);

        // Act
        List<SceneResponseDTO> actualResponse = sceneService.getActiveScenesByUnityName(requestDTO);

        // Assert
        assertEquals(0, actualResponse.size());
        verify(sceneRepository, times(1)).findByActiveTrueAndUnityScene(requestDTO.getUnityScene());
        verify(mapper, never()).mapList(anyList(), any());
    }

    @Test
    void testDeleteSceneByServerName_WithValidServerName_ShouldInvokeServerServiceDeleteSceneByServerName() {
        // Arrange
        String serverName = "TestServer";

        // Act
        sceneService.deleteSceneByServerName(serverName);

        // Assert
        verify(serverService, times(1)).deleteSceneByServerName(serverName);
    }

    @Test
    void testPostConstruct_WithMissingUnityScenes_ShouldSaveDefaultScenes() {

        Scene mockScene = Scene.builder()
                .unityScene(UnityScene.GameScene)
                .sceneName(Constants.getDefaultSceneName())
                .scenePassword(Constants.getDefaultPassword())
                .hostEmail(Constants.getDefaultEmail())
                .hostFirstName(Constants.getDefaultFirstName())
                .hostLastName(Constants.getDefaultLastName())
                .privateScene(false)
                .sceneType(SceneType.DEFAULT)
                .maxUserCapacity(Constants.getMaxUserCount())
                .active(false)
                .build();

        // Arrange
        List<UnityScene> sceneNameList = Arrays.asList(
                UnityScene.ClassroomScene, UnityScene.IdleScene
        );

        when(sceneRepository.findAllUnitySceneNames()).thenReturn(sceneNameList);

        when(sceneRepository.save(any(Scene.class))).thenReturn(mockScene);

        // Act
        sceneService.postConstruct();

        // Assert
        verify(sceneRepository, times(1)).findAllUnitySceneNames();
        verify(sceneRepository,
                times(DefaultUnityScenes.DEFAULT_UNITY_SCENES.getScenes().size())).save(any(Scene.class));

    }

    @Test
    void testPostConstruct_WithoutMissingUnityScenes_ShouldPass() {

        // Arrange
        List<UnityScene> sceneNameList = DefaultUnityScenes.DEFAULT_UNITY_SCENES.getScenes().stream().toList();

        when(sceneRepository.findAllUnitySceneNames()).thenReturn(sceneNameList);

        // Act
        sceneService.postConstruct();

        // Assert
        verify(sceneRepository, times(1)).findAllUnitySceneNames();
        verify(sceneRepository,
                times(0)).save(any(Scene.class));

    }

}
