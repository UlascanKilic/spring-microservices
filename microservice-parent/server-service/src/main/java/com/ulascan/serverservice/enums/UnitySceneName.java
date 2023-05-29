package com.ulascan.serverservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public enum UnitySceneName {
    GAME_SCENE("GAMESCENE"),
    IDLE_SCENE("IDLESCENE"),
    CLASSROOM_SCENE("ClassroomScene");

    @Getter
    private final String sceneName;
}
