package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.GLog;

public class Renew extends Spell{


    public Renew(){
        image = ItemSpriteSheet.REDDEWDROP;
        name = "Renew";
        active = true;
        lvl = 1;
        mp_cost = 5;
        cd = 25;
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
