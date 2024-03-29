package com.github.dachhack.sprout.actors.buffs;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.actors.hero.Hero;
import com.github.dachhack.sprout.items.artifacts.CloakOfShadows;

public class EnergyRegen extends Buff{

    private static final float REGENERATION_DELAY = 1;

    @Override
    public boolean act() {
        Hero hero = Dungeon.hero;
        if (target.isAlive()) {

            if (hero.energy < hero.energyTotal && !((Hero) target).isStarving()) {
                if(hero.buff(PoweredEnergyRegen.class) != null){
                    hero.energy += 2;
                }else{
                    hero.energy += 1;
                }
            }

            if(hero.liquidState()){
                if(hero.buff(Liquidation.class) == null){
                    hero.liquid = true;
                    Buff.affect(hero, Liquidation.class);
                }
            }else{
                if(hero.buff(Liquidation.class) != null) {
                    hero.liquid = false;
                    Buff.detach(hero, Liquidation.class);
                }
            }

            float mLevel = hero.getMasteryLevel() > 1 ? hero.getMasteryLevel() * 0.5f * 0.01f : 0.01f;
            spend(Math.max(REGENERATION_DELAY - mLevel, 0.50f));

        } else {

            diactivate();

        }

        return true;
    }
}
