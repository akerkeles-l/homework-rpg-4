package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;

import java.util.Random;

public class RaidEngine {
    private Random random = new Random(1L);

    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public void reset() {
        this.random = new Random(1L);
    }

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, Skill teamASkill, Skill teamBSkill) {
        if (teamA == null || teamB == null || teamASkill == null || teamBSkill == null) {
            throw new IllegalArgumentException("Teams and skills cannot be null");
        }
        
        if (!teamA.isAlive() || !teamB.isAlive()) {
            throw new IllegalStateException("Both teams must be alive to start a raid");
        }
        
        RaidResult result = new RaidResult();
        int round = 0;
        final int MAX_ROUNDS = 100;
        
        result.addLine("=== RAID BATTLE START ===");
        result.addLine("Team A: " + teamA.getName() + " (Health: " + teamA.getHealth() + ")");
        result.addLine("Team B: " + teamB.getName() + " (Health: " + teamB.getHealth() + ")");
        result.addLine("Team A Skill: " + teamASkill.getName() + " (Damage: " + teamASkill.getDamage() + ")");
        result.addLine("Team B Skill: " + teamBSkill.getName() + " (Damage: " + teamBSkill.getDamage() + ")");
        result.addLine("");
        
        while (teamA.isAlive() && teamB.isAlive() && round < MAX_ROUNDS) {
            round++;
            result.addLine("=== ROUND " + round + " ===");
            
            performCast(teamA, teamB, teamASkill, "Team A", "Team B", result);
            
            if (!teamB.isAlive()) {
                result.addLine("Team B has been defeated!");
                break;
            }
            
            performCast(teamB, teamA, teamBSkill, "Team B", "Team A", result);
            
            result.addLine("Round " + round + " Status:");
            result.addLine("  Team A Health: " + teamA.getHealth());
            result.addLine("  Team B Health: " + teamB.getHealth());
            result.addLine("");
        }
        
        result.setRounds(round);
        
        if (!teamA.isAlive() && !teamB.isAlive()) {
            result.setWinner("Draw");
            result.addLine("The battle ended in a draw!");
        } else if (!teamB.isAlive()) {
            result.setWinner("Team A");
            result.addLine("Team A is victorious!");
        } else if (!teamA.isAlive()) {
            result.setWinner("Team B");
            result.addLine("Team B is victorious!");
        } else {
            result.setWinner("Draw (Max rounds)");
            result.addLine("Maximum rounds reached. The battle is a draw!");
        }
        
        return result;
    }
    
    private void performCast(CombatNode attacker, CombatNode defender, Skill skill, 
                           String attackerName, String defenderName, RaidResult result) {
        
        int baseDamage = skill.getDamage();
        boolean isCritical = random.nextInt(100) < 10;
        int finalDamage = isCritical ? baseDamage * 2 : baseDamage;
        
        result.addLine(attackerName + " " + attacker.getName() + 
                       " casts " + skill.getName() + 
                       " on " + defenderName + " " + defender.getName());
        
        if (isCritical) {
            result.addLine("  CRITICAL HIT! Damage doubled!");
        }
        
        result.addLine("  Deals " + finalDamage + " damage");
        
        defender.takeDamage(finalDamage);
        
        if (!defender.isAlive()) {
            result.addLine("  " + defenderName + " " + defender.getName() + " has been defeated!");
        }
    }
}