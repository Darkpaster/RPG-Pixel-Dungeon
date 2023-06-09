package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.GLog;

public class Electromancer extends Spell{


    public Electromancer(){
        image = ItemSpriteSheet.AXE;
        name = "Mortal Strike";
        active = true;
        lvl = 1;
        rage_cost = 20;
        cd = 10;
    }

    @Override
    protected void onUse() {
        super.onUse();

        GLog.p("Used!");
    }

    public String info() {
        return "Description. lvl " + lvl + skillInfo();
    }
}
