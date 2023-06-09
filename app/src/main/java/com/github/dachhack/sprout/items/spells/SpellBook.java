package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.hero.Hero;
import com.github.dachhack.sprout.effects.Speck;
import com.github.dachhack.sprout.items.Item;
import com.github.dachhack.sprout.items.bags.Bag;
import com.github.dachhack.sprout.items.scrolls.ScrollOfUpgrade;
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
        MORTAL_STRIKE, POISONED_BLADE, PILLAR_OF_FIRE, BASH, RESPITE,
        POWERFUL_SLAM, LIQUIDATION, BATTLE_CRY, CHARGE, MAGIC_SHIELD, ARCANE_BOOST,
        FROST_NOVA, FIRE_BALL, DEEP_FREEZE, SPRINT, BLINK, BLIND_FURY, RECKLESSNESS,
        BACK_STAB, RUPTURE, SWEEPING_STRIKES, AMPHIBIAN, PYROMANCER, ELECTROMANCER,
        NIGHT_VISION, CRYOMANCER, EXECUTE, SHIELD_WALL, FIRE_ELEMENTAL, BLOODY_DEMON,
        THIRST_FOR_KNOWLEDGE, GENIUS, MAD, VAMPIRE, UNDEAD, WEREWOLF, DODGE_STYLE,
        ARMOR_PENETRATION, HAMSTRING, SLASH, ACCURATE_ATTACK, KILL_COMMAND, CHARM,
        DEATH_AURA, COMBO_ATTACK, ALCHEMIST, JEWELER, HIGH_LUCK, RENEW
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
            case SLASH:
                return new Slash();
            case RENEW:
                return new Renew();
            case RESPITE:
                return new Respite();
            case LIQUIDATION:
                return new Liquidation();
            case ARCANE_BOOST:
                return new ArcaneBoost();
            default:
                return null;
        }
    }


    public void learnSpell(spellList spell){
        boolean z = true;
        int index = -1;
        Spell skill = getSpell(spell);
        for(Spell sp: spells){
            if(sp.name().equals(skill.name())){
                z = false;
                index = spells.indexOf(sp);
            }
        }
        if(z){
            this.spells.add(skill);
            GLog.p("You learned a new spell " + skill.name().toLowerCase() + "!");
            ScrollOfUpgrade.upgrade(Dungeon.hero, Speck.MASTERY);
        }else{
            spells.get(index).lvlUp();
            GLog.p("Power of your " + spells.get(index).name().toLowerCase() + " has been increased!");
            ScrollOfUpgrade.upgrade(Dungeon.hero, Speck.UP);
        }
    }
    public void learnSpell(spellList spell, int upgrade){
        if(!spells.contains(getSpell(spell))){
            GLog.n("You haven't s such skill.");
            return;
        }
        getSpell(spell).lvl += upgrade;
        GLog.p("Power of your " + getSpell(spell).name().toLowerCase() + " has been increased!");
        ScrollOfUpgrade.upgrade(Dungeon.hero, Speck.UP);
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
