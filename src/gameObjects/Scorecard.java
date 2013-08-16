package gameObjects;

import enumerations.MonsterType;

public class Scorecard {

    private int groundMonstersKilled = 0;
    private int flyingMonstersKilled = 0;
    
    public Scorecard(){
        
    }
    
    public void addMonsterKills(MonsterType monsterType, int count){
        
        switch (monsterType){
        case FlyingMonster:
            flyingMonstersKilled += count;
            break;
        case GroundMonster:
            groundMonstersKilled += count;
            break;
        }
        
    }
    
    public int getScore(){
        
        return groundMonstersKilled + flyingMonstersKilled;
        
    }
    
}