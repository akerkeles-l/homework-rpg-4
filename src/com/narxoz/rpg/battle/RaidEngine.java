package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;
import java.util.Random;

public class RaidEngine {
    private StringBuilder battleLog;

    public RaidEngine setRandomSeed(long seed) {
        new Random(seed);
        return this;
    }

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, Skill teamASkill, Skill teamBSkill) {
        battleLog = new StringBuilder();
        RaidResult result = new RaidResult();
        
        logLine("=== RAID BATTLE START ===");
        logLine("Team A: " + teamA.getName());
        logLine("Team B: " + teamB.getName());
        logLine("Team A uses: " + teamASkill.getSkillName() + " (" + teamASkill.getEffectName() + ")");
        logLine("Team B uses: " + teamBSkill.getSkillName() + " (" + teamBSkill.getEffectName() + ")");
        
        int round = 1;
        String winner = null;
        
        while (winner == null) {
            logLine("\n--- ROUND " + round + " ---");
            
            logLine("Team A casts " + teamASkill.getSkillName() + ":");
            teamASkill.cast(teamB);
            
            if (!teamB.isAlive()) {
                winner = teamA.getName();
                break;
            }
            
            logLine("Team B casts " + teamBSkill.getSkillName() + ":");
            teamBSkill.cast(teamA);
            
            if (!teamA.isAlive()) {
                winner = teamB.getName();
                break;
            }
            
            logLine("\nStatus after round " + round + ":");
            logHealth(teamA, "Team A");
            logHealth(teamB, "Team B");
            
            round++;
            
            if (round > 100) {
                winner = "DRAW (max rounds reached)";
                break;
            }
        }
        
        logLine("\n=== RAID BATTLE END ===");
        logLine("Winner: " + winner);
        logLine("Total rounds: " + round);
        
        result.setWinner(winner);
        result.setRounds(round);
        
        for (String line : battleLog.toString().split("\n")) {
            result.addLine(line);
        }
        
        return result;
    }
    
    private void logLine(String line) {
        battleLog.append(line).append("\n");
        System.out.println(line);
    }
    
    private void logHealth(CombatNode team, String teamName) {
        if (team.getChildren().isEmpty()) {
            logLine("  " + teamName + " - " + team.getName() + ": " + team.getHealth() + " HP");
        } else {
            logLine("  " + teamName + " " + team.getName() + ":");
            for (CombatNode member : team.getChildren()) {
                if (member.isAlive()) {
                    logLine("    " + member.getName() + ": " + member.getHealth() + " HP");
                }
            }
        }
    }
}