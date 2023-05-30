package com.ulascan.serverservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UnityScene {
    GAME_SCENE("GameScene"),
    IDLE_SCENE("IdleScene"),
    CLASSROOM_SCENE("ClassroomScene");

    @Getter
    private final String sceneName;
}
