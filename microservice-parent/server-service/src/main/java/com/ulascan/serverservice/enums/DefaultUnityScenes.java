package com.ulascan.serverservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public enum DefaultUnityScenes {

    DEFAULT_UNITY_SCENES(Set.of(
            UnityScene.GameScene
    ));

    @Getter
    private final Set<UnityScene> scenes;
}
