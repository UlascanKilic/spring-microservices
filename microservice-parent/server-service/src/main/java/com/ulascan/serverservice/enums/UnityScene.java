package com.ulascan.serverservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UnityScene {
    GameScene("GameScene"),
    IdleScene("IdleScene"),
    ClassroomScene("ClassroomScene");

    @Getter
    private final String sceneName;
}
