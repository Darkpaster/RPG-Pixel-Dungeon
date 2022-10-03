package com.github.dachhack.sprout.actors.buffs;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.actors.hero.Hero;

public class EnergyRegen extends Buff{

    private static final float REGENERATION_DELAY = 10;

    @Override
    public boolean act() {
        Hero hero = Dungeon.hero;
        if (target.isAlive()) {

            if (hero.energy < hero.energyTotal && !((Hero) target).isStarving()) {
                hero.energy += 1;
            }

            int mLevel = Math.max(hero.masteryLevel / 5, 1);
            spend(Math.max(REGENERATION_DELAY / mLevel, 0.50f));

        } else {

            diactivate();

        }

        return true;
    }
}
