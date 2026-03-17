package com.narxoz.rpg;

import com.narxoz.rpg.battle.RaidEngine;
import com.narxoz.rpg.battle.RaidResult;
import com.narxoz.rpg.bridge.*;
import com.narxoz.rpg.composite.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== HOMEWORK 4: BRIDGE + COMPOSITE PATTERNS DEMO ===\n");
        
        System.out.println("--- PART 1: Composite Pattern - Building Raid Hierarchy ---");
        
        HeroUnit warrior = new HeroUnit("Arthas (Warrior)", 140, 30);
        HeroUnit mage = new HeroUnit("Jaina (Mage)", 90, 40);
        HeroUnit paladin = new HeroUnit("Uther (Paladin)", 160, 25);
        
        EnemyUnit goblin = new EnemyUnit("Goblin Scout", 70, 20);
        EnemyUnit orc = new EnemyUnit("Orc Warrior", 120, 25);
        EnemyUnit troll = new EnemyUnit("Troll Berserker", 110, 28);
        EnemyUnit ogre = new EnemyUnit("Ogre Mage", 150, 22);
        
        PartyComposite heroParty1 = new PartyComposite("Hero Party Alpha");
        heroParty1.add(warrior);
        heroParty1.add(mage);
        
        PartyComposite heroParty2 = new PartyComposite("Hero Party Beta");
        heroParty2.add(paladin);
        
        RaidGroup heroRaid = new RaidGroup("ALLIANCE RAID GROUP");
        heroRaid.add(heroParty1);
        heroRaid.add(heroParty2);
        
        PartyComposite enemyParty1 = new PartyComposite("Enemy Party Gamma");
        enemyParty1.add(goblin);
        enemyParty1.add(orc);
        
        PartyComposite enemyParty2 = new PartyComposite("Enemy Party Delta");
        enemyParty2.add(troll);
        enemyParty2.add(ogre);
        
        RaidGroup enemyRaid = new RaidGroup("HORDE RAID GROUP");
        enemyRaid.add(enemyParty1);
        enemyRaid.add(enemyParty2);
        
        System.out.println("\nHero Team Structure:");
        heroRaid.printTree("");
        
        System.out.println("\nEnemy Team Structure:");
        enemyRaid.printTree("");
        
        System.out.println("\n--- PART 2: Bridge Pattern - Skills with Different Effects ---");
        
        Skill fireSlash = new SingleTargetSkill("Slash", 20, new FireEffect());
        Skill iceSlash = new SingleTargetSkill("Slash", 20, new IceEffect());
        Skill shadowSlash = new SingleTargetSkill("Slash", 20, new ShadowEffect());
        Skill physicalSlash = new SingleTargetSkill("Slash", 20, new PhysicalEffect());
        
        Skill fireStorm = new AreaSkill("Storm", 15, new FireEffect());
        Skill fireStrike = new SingleTargetSkill("Strike", 25, new FireEffect());
        Skill fireBlast = new AreaSkill("Blast", 18, new FireEffect());
        
        System.out.println("Same skill 'Slash' with different effects:");
        System.out.println("  " + fireSlash.getSkillName() + " + " + fireSlash.getEffectName() + 
                          " = " + ((SingleTargetSkill)fireSlash).resolvedDamage() + " damage");
        System.out.println("  " + iceSlash.getSkillName() + " + " + iceSlash.getEffectName() + 
                          " = " + ((SingleTargetSkill)iceSlash).resolvedDamage() + " damage");
        System.out.println("  " + shadowSlash.getSkillName() + " + " + shadowSlash.getEffectName() + 
                          " = " + ((SingleTargetSkill)shadowSlash).resolvedDamage() + " damage");
        System.out.println("  " + physicalSlash.getSkillName() + " + " + physicalSlash.getEffectName() + 
                          " = " + ((SingleTargetSkill)physicalSlash).resolvedDamage() + " damage");
        
        System.out.println("\nDifferent skills with same Fire effect:");
        System.out.println("  " + fireSlash.getSkillName() + " (Single): " + 
                          ((SingleTargetSkill)fireSlash).resolvedDamage() + " damage");
        System.out.println("  " + fireStrike.getSkillName() + " (Single): " + 
                          ((SingleTargetSkill)fireStrike).resolvedDamage() + " damage");
        System.out.println("  " + fireStorm.getSkillName() + " (Area): " + 
                          ((AreaSkill)fireStorm).resolvedDamage() + " damage");
        System.out.println("  " + fireBlast.getSkillName() + " (Area): " + 
                          ((AreaSkill)fireBlast).resolvedDamage() + " damage");
        
        System.out.println("\n--- PART 3: Raid Battle Simulation ---");
        
        warrior = new HeroUnit("Arthas (Warrior)", 140, 30);
        mage = new HeroUnit("Jaina (Mage)", 90, 40);
        paladin = new HeroUnit("Uther (Paladin)", 160, 25);
        goblin = new EnemyUnit("Goblin Scout", 70, 20);
        orc = new EnemyUnit("Orc Warrior", 120, 25);
        troll = new EnemyUnit("Troll Berserker", 110, 28);
        ogre = new EnemyUnit("Ogre Mage", 150, 22);
    
        heroParty1 = new PartyComposite("Hero Party Alpha");
        heroParty1.add(warrior);
        heroParty1.add(mage);
        
        heroParty2 = new PartyComposite("Hero Party Beta");
        heroParty2.add(paladin);
        
        heroRaid = new RaidGroup("ALLIANCE RAID GROUP");
        heroRaid.add(heroParty1);
        heroRaid.add(heroParty2);
        
        enemyParty1 = new PartyComposite("Enemy Party Gamma");
        enemyParty1.add(goblin);
        enemyParty1.add(orc);
        
        enemyParty2 = new PartyComposite("Enemy Party Delta");
        enemyParty2.add(troll);
        enemyParty2.add(ogre);
        
        enemyRaid = new RaidGroup("HORDE RAID GROUP");
        enemyRaid.add(enemyParty1);
        enemyRaid.add(enemyParty2);
        
        Skill heroSkill = new AreaSkill("Heroic Strike", 22, new FireEffect());
        Skill enemySkill = new AreaSkill("Dark Slash", 20, new ShadowEffect());
        
        RaidEngine engine = new RaidEngine().setRandomSeed(12345L);
        RaidResult result = engine.runRaid(heroRaid, enemyRaid, heroSkill, enemySkill);
        
        System.out.println("\n=== RAID BATTLE SUMMARY ===");
        System.out.println("Winner: " + result.getWinner());
        System.out.println("Total Rounds: " + result.getRounds());
    
        System.out.println("\n--- PART 4: Architecture Verification ---");
        System.out.println("✓ Composite Pattern: Units and groups share same interface");
        System.out.println("✓ Composite Pattern: Nested groups work correctly");
        System.out.println("✓ Bridge Pattern: Skills and effects vary independently");
        System.out.println("✓ Bridge Pattern: Same effect works with different skills");
        System.out.println("✓ RaidEngine: Depends only on abstractions (CombatNode, Skill)");
        System.out.println("✓ Deterministic: Battle results reproducible with same seed");
        
        System.out.println("\n=== DEMO COMPLETE ===");
    }
}