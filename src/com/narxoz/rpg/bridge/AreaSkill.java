package com.narxoz.rpg.bridge;

import com.narxoz.rpg.composite.CombatNode;

public class AreaSkill extends Skill {
    public AreaSkill(String skillName, int basePower, EffectImplementor effect) {
        super(skillName, basePower, effect);
    }

    @Override
    public void cast(CombatNode target) {
        int damage = resolvedDamage();
        System.out.println("  Casting AREA " + getSkillName() + " (" + getEffectName() + 
                          ") for " + damage + " damage on " + target.getName() + " and all members");
        
        if (target.getChildren().isEmpty()) {
            target.takeDamage(damage);
        } else {
           
            for (CombatNode child : target.getChildren()) {
                if (child.isAlive()) {
                    child.takeDamage(damage);
                }
            }
        }
    }
}