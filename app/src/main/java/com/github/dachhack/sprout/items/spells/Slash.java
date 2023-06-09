package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.GLog;

public class Slash extends Spell{


    public Slash(){
        image = ItemSpriteSheet.SWORD;
        name = "Slash";
        active = true;
        lvl = 1;
        rage_cost = 10;
        cd = 5;
    }

    @Override
    protected void onUse() {
        super.onUse();
        GLog.p("Used!");
    }

    public String info() {
        return "Description." + skillInfo();
    }
}
