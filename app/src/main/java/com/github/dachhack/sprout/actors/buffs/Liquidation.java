package com.github.dachhack.sprout.actors.buffs;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.ui.BuffIndicator;

public class Liquidation extends Buff{
    @Override
    public String toString() {
        return "Liquidation";
    }

    @Override
    public int icon() {
        return BuffIndicator.LIQUIDATION;
    }
}
