package enumerations;

import gameObjects.GameObject;

public enum MonsterType {

    GroundMonster,
    FlyingMonster;

    public static MonsterType getMonsterTypeFromClassName(Class<? extends GameObject> className) {
        
        String[] classNameString = className.toString().split("\\.");
        MonsterType monsterType = valueOf(classNameString[1]);
        
        return monsterType;
        
    }
    
}