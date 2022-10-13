package com.github.dachhack.sprout.actors.buffs;

import com.github.dachhack.sprout.ui.BuffIndicator;

public class PoweredEnergyRegen extends Buff{
    @Override
    public String toString() {
        return "Powered energy regen";
    }

    @Override
    public int icon() {
        return BuffIndicator.ENERGY_REGEN;
    }
}
