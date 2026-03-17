package com.narxoz.rpg.bridge;

import com.narxoz.rpg.composite.CombatNode;


public class SingleTargetSkill extends Skill {
    public SingleTargetSkill(String skillName, int basePower, EffectImplementor effect) {
        super(skillName, basePower, effect);
    }

    @Override
    public void cast(CombatNode target) {
        int damage = resolvedDamage();
        System.out.println("  Casting " + getSkillName() + " (" + getEffectName() + 
                          ") for " + damage + " damage on " + target.getName());
        target.takeDamage(damage);
    }
}