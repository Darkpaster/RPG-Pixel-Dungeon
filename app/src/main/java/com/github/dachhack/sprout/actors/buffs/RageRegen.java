package com.github.dachhack.sprout.actors.buffs;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.actors.hero.Hero;
import com.github.dachhack.sprout.ui.BuffIndicator;

public class RageRegen extends Buff{

    //private static final float REGENERATION_DELAY = 100;

    @Override
    public boolean act() {
        Hero hero = Dungeon.hero;
        if (target.isAlive()) {

            if (hero.rage > 0 && hero.HP > hero.HT / 5) {
                if(hero.rage > hero.rageTotal - hero.rageTotal / 3){
                    hero.rage -= 3;
                }else if(hero.rage > hero.rageTotal / 2){
                    hero.rage -= 2;
                }else{
                    hero.rage -= 1;
                }
            }

            //int mLevel = hero.physicLevel;
            spend(1.0f);

        } else {

            diactivate();

        }

        return true;
    }

    @Override
    public int icon() {
        Hero hero = Dungeon.hero;
        if(hero.HP < hero.HT / 5){
            return BuffIndicator.FURY;
        }
        return super.icon();
    }
    @Override
    public String toString() {
        Hero hero = Dungeon.hero;
        if(hero.HP < hero.HT / 5){
            return "Furious";
        }
        return "";
    }

    @Override
    public String desc() {
        Hero hero = Dungeon.hero;
        if(hero.HP < hero.HT / 5){
            return "123213123";
        }
        return super.desc();
    }
}
