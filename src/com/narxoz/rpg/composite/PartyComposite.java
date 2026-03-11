package com.narxoz.rpg.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartyComposite implements CombatNode {
    private final String name;
    private final List<CombatNode> children = new ArrayList<>();

    public PartyComposite(String name) {
        this.name = name;
    }

    public void add(CombatNode node) {
        children.add(node);
    }

    public void remove(CombatNode node) {
        children.remove(node);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        int totalHealth = 0;
        for (CombatNode child : children) {
            totalHealth += child.getHealth();
        }
        return totalHealth;
    }

    @Override
    public int getAttackPower() {
        int totalAttack = 0;
        for (CombatNode child : children) {
            if (child.isAlive()) {
                totalAttack += child.getAttackPower();
            }
        }
        return totalAttack;
    }

    @Override
    public void takeDamage(int amount) {
        List<CombatNode> aliveChildren = getAliveChildren();
        
        if (aliveChildren.isEmpty()) {
            return;
        }
        
        int damagePerChild = amount / aliveChildren.size();
        int remainderDamage = amount % aliveChildren.size();
        
        for (CombatNode child : aliveChildren) {
            int damageToApply = damagePerChild;
            if (remainderDamage > 0) {
                damageToApply++;
                remainderDamage--;
            }
            child.takeDamage(damageToApply);
        }
    }

    @Override
    public boolean isAlive() {
        for (CombatNode child : children) {
            if (child.isAlive()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<CombatNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void printTree(String indent) {
        int totalHealth = getHealth();
        int totalAttack = getAttackPower();
        String status = isAlive() ? "ALIVE" : "DEFEATED";
        
        System.out.println(indent + "+ " + name + " [HP: " + totalHealth + ", ATK: " + totalAttack + ", " + status + "]");
        
        for (CombatNode child : children) {
            child.printTree(indent + "  ");
        }
    }

    private List<CombatNode> getAliveChildren() {
        List<CombatNode> aliveChildren = new ArrayList<>();
        for (CombatNode child : children) {
            if (child.isAlive()) {
                aliveChildren.add(child);
            }
        }
        return aliveChildren;
    }
}