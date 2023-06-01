package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.hero.Hero;
import com.github.dachhack.sprout.items.Item;
import com.github.dachhack.sprout.items.bags.Bag;
import com.github.dachhack.sprout.utils.GLog;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class SpellBook extends Bag {
    public boolean active = true;

    private final ArrayList<Spell> spells = new ArrayList<>();

    private static final String TXT_SPELLS = "spells";

    public enum spellList{
        MORTAL_STRIKE, POISONED_BLADE, PILLAR_OF_FIRE
    }

    public ArrayList<Spell> getSpells(){return spells;}

    public Spell getSpell(spellList spell){
        switch (spell){
            case MORTAL_STRIKE:
                return new MortalStrike();
            case POISONED_BLADE:
                return new PoisonedBlade();
            case PILLAR_OF_FIRE:
                return new PillarOfFire();
            default:
                return null;
        }
    }


    public void learnSpell(spellList spell){
        boolean z = true;
        int index = -1;
        for(Spell sp: getSpells()){
            if(sp.name().equals(getSpell(spell).name())){
                z = false;
                index = spells.indexOf(sp);
            }
        }
        if(z){
            this.spells.add(getSpell(spell));
            GLog.p("You learned a new spell " + spell.toString().toLowerCase() + "!");
        }else{
            spells.get(index).lvl++;
            GLog.p("Power of your " + spell.toString().toLowerCase() + " has been increased!");
        }
    }

    public void clear(){
        spells.clear();
    }


    @Override
    public void storeInBundle(Bundle bundle) {
        bundle.put(TXT_SPELLS, spells);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        //spells = (Collection<Spell>) bundle.getCollection(TXT_SPELLS);
        if(spells.size() == 0){
            for (Bundlable b : bundle.getCollection(TXT_SPELLS)) {
                if (b != null) {
                    spells.add((Spell)b);
                }
            }
        }
    }


}
