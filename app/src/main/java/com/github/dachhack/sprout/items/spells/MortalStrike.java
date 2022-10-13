package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.actors.hero.Hero;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.GLog;

public class MortalStrike extends Spell{


    public MortalStrike(){
        image = ItemSpriteSheet.AXE;
        name = "Mortal Strike";
        spellType = "active";
        rage_cost = 20;
        cd = 10;
    }

    @Override
    protected void onUse() {
        super.onUse();

        GLog.p("Used!");
    }

    public String info() {
        return "Description. " + lvl + skillInfo();
    }
}
