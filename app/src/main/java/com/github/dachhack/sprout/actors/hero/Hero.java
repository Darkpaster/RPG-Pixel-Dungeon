/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.github.dachhack.sprout.actors.hero;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.Badges;
import com.github.dachhack.sprout.Bones;
import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.GamesInProgress;
import com.github.dachhack.sprout.ResultDescriptions;
import com.github.dachhack.sprout.Statistics;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.buffs.*;
import com.github.dachhack.sprout.actors.mobs.Mob;
import com.github.dachhack.sprout.actors.mobs.npcs.NPC;
import com.github.dachhack.sprout.actors.mobs.pets.PET;
import com.github.dachhack.sprout.effects.CellEmitter;
import com.github.dachhack.sprout.effects.CheckedCell;
import com.github.dachhack.sprout.effects.Flare;
import com.github.dachhack.sprout.effects.Speck;
import com.github.dachhack.sprout.items.*;
import com.github.dachhack.sprout.items.Heap.Type;
import com.github.dachhack.sprout.items.armor.glyphs.Viscosity;
import com.github.dachhack.sprout.items.artifacts.*;
import com.github.dachhack.sprout.items.bags.Bag;
import com.github.dachhack.sprout.items.keys.GoldenKey;
import com.github.dachhack.sprout.items.keys.GoldenSkeletonKey;
import com.github.dachhack.sprout.items.keys.IronKey;
import com.github.dachhack.sprout.items.keys.Key;
import com.github.dachhack.sprout.items.keys.SkeletonKey;
import com.github.dachhack.sprout.items.misc.AutoPotion.AutoHealPotion;
import com.github.dachhack.sprout.items.potions.Potion;
import com.github.dachhack.sprout.items.potions.PotionOfHealing;
import com.github.dachhack.sprout.items.potions.PotionOfMight;
import com.github.dachhack.sprout.items.potions.PotionOfStrength;
import com.github.dachhack.sprout.items.rings.RingOfElements;
import com.github.dachhack.sprout.items.rings.RingOfEvasion;
import com.github.dachhack.sprout.items.rings.RingOfForce;
import com.github.dachhack.sprout.items.rings.RingOfFuror;
import com.github.dachhack.sprout.items.rings.RingOfHaste;
import com.github.dachhack.sprout.items.rings.RingOfMight;
import com.github.dachhack.sprout.items.rings.RingOfTenacity;
import com.github.dachhack.sprout.items.scrolls.Scroll;
import com.github.dachhack.sprout.items.scrolls.ScrollOfMagicMapping;
import com.github.dachhack.sprout.items.scrolls.ScrollOfMagicalInfusion;
import com.github.dachhack.sprout.items.scrolls.ScrollOfRecharging;
import com.github.dachhack.sprout.items.scrolls.ScrollOfUpgrade;
import com.github.dachhack.sprout.items.spells.Spell;
import com.github.dachhack.sprout.items.spells.SpellBook;
import com.github.dachhack.sprout.items.wands.Wand;
import com.github.dachhack.sprout.items.weapon.melee.MeleeWeapon;
import com.github.dachhack.sprout.items.weapon.missiles.MissileWeapon;
import com.github.dachhack.sprout.levels.Level;
import com.github.dachhack.sprout.levels.Terrain;
import com.github.dachhack.sprout.levels.features.AlchemyPot;
import com.github.dachhack.sprout.levels.features.Chasm;
import com.github.dachhack.sprout.levels.features.Sign;
import com.github.dachhack.sprout.plants.Earthroot;
import com.github.dachhack.sprout.plants.Sungrass;
import com.github.dachhack.sprout.scenes.GameScene;
import com.github.dachhack.sprout.scenes.InterlevelScene;
import com.github.dachhack.sprout.scenes.SurfaceScene;
import com.github.dachhack.sprout.sprites.CharSprite;
import com.github.dachhack.sprout.sprites.HeroSprite;
import com.github.dachhack.sprout.ui.AttackIndicator;
import com.github.dachhack.sprout.ui.BuffIndicator;
import com.github.dachhack.sprout.ui.QuickSlotButton;
import com.github.dachhack.sprout.utils.GLog;
import com.github.dachhack.sprout.windows.WndAscend;
import com.github.dachhack.sprout.windows.WndDescend;
import com.github.dachhack.sprout.windows.WndDewVial;
import com.github.dachhack.sprout.windows.WndLevelUp;
import com.github.dachhack.sprout.windows.WndMessage;
import com.github.dachhack.sprout.windows.WndResurrect;
import com.github.dachhack.sprout.windows.WndTradeItem;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Hero extends Char {

	private static final String TXT_LEAVE = "One does not simply leave Pixel Dungeon.";
	private static final String TXT_OVERFILL = "HP Overfilled by %s";

	private static final String TXT_LEVEL_UP = "level up!";
	private static final String TXT_NEW_LEVEL = "Welcome to level %d! Now you are healthier and more focused. "
			+ "It's easier for you to hit enemies and dodge their attacks. Your main attribute has been increased.";

	public static final String TXT_YOU_NOW_HAVE = "You now have %s";

	private static final String TXT_SOMETHING_ELSE = "There is something else here";
	private static final String TXT_LOCKED_CHEST = "This chest is locked and you don't have matching key";
	private static final String TXT_LOCKED_DOOR = "You don't have a matching key";
	private static final String TXT_NOTICED_SMTH = "You noticed something";

	private static final String TXT_WAIT = "...";
	private static final String TXT_SEARCH = "search";

	public static final int STARTING_STR = 10;

	private static final float TIME_TO_REST = 1f;
	private static final float TIME_TO_SEARCH = 2f;

	private int curPos = 0;

	public boolean invisRegen = false;
	public boolean invisSpeed = false;
	public boolean eatingEnergy = false; //+ energy for eating

	public boolean daggerMaster = false; //+ crit dmg/chance for wearing daggers
	public boolean invisAttacks = false; //attacks dont detach invis
	public boolean bombsDamage = false; //powered bombs

	public boolean shadowStep = false;
	public boolean rapcha = false;
	public boolean poisons = false;

	public float regeneration_delay;

	public float liqDmg = 3.0f;
	public int regeneration_power;

	public HeroClass heroClass = HeroClass.ROGUE;
	public HeroSubClass subClass = HeroSubClass.NONE;

	private int attackSkill = 10;
	private int defenseSkill = 5;

	public boolean levelup = false;

	public boolean ready = false;

	public boolean liquid = false;

	public boolean haspet = false;
	public boolean petfollow = false;
	public int petType = 0;
	public int petLevel = 0;
	public int petKills = 0;
	public int petHP = 0;
	public int petExperience = 0;
	public int petCooldown = 0;

	public int petCount = 0;
	public int DR;


	private boolean damageInterrupt = true;
	public boolean critDmg = false;
	public HeroAction curAction = null;
	public HeroAction lastAction = null;

	private Char enemy;

	private Item theKey;
	private Item theSkeletonKey;

	public boolean restoreHealth = false;

	public MissileWeapon rangedWeapon = null;
	public Belongings belongings;

	public int STR;
	public boolean weakened = false;

	public float awareness;

	public int lvl = 1;
	public int exp = 0;

	private int magicLevel;
	private int physicLevel;
	public int bonusDamage = 0;
	private int masteryLevel;

	private int basePhysic;
	private int baseMastery;
	private int baseMagic;

	public float critStrikeChance;
	public float critDamage;

//	public int MP;
//	public int MT;

	public int rage;
	public int rageTotal;

	public int energy;
	public int energyTotal;

	public float ambushDamage;

	public float additAS;

	public int missingEnergy = 0;

	public SpellBook spellbook;



	private ArrayList<Mob> visibleEnemies;

	public Hero() {
		super();
		name = "you";

		HP = HT = 20;
		MP = MT = 0;
		rage = rageTotal = 0;
		energy = energyTotal = 0;
		STR = STARTING_STR;
		awareness = 0.1f;
		speed = 1;
		DR = 0;
		additAS = 0.0f;
		ambushDamage = 1.0f;
		critStrikeChance = 0;
		critDamage = 2;
		magicLevel = 1;
		masteryLevel = 1;
		physicLevel = 1;
		baseMastery = masteryLevel;
		basePhysic = physicLevel;
		baseMagic = magicLevel;
		regeneration_delay = 10;
		regeneration_power = 1;

		belongings = new Belongings(this);
		spellbook = new SpellBook();

		visibleEnemies = new ArrayList<Mob>();
	}

	public int STR() {
		int STR = this.STR;

		for (Buff buff : buffs(RingOfMight.Might.class)) {
			STR += ((RingOfMight.Might) buff).level;
		}

		return weakened ? STR - 2 : STR;
	}

	private static final String ATTACK = "attackSkill";
	private static final String DEFENSE = "defenseSkill";
	private static final String STRENGTH = "STR";
	private static final String LEVEL = "lvl";
	private static final String EXPERIENCE = "exp";
	private static final String HASPET = "haspet";
	private static final String PETFOLLOW = "petfollow";
	private static final String LEVELUP = "levelup";
	private static final String PETTYPE = "petType";
	private static final String PETLEVEL = "petLevel";
	private static final String PETKILLS = "petKills";
	private static final String PETHP = "petHP";
	private static final String PETEXP = "petExperience";
	private static final String PETCOOLDOWN = "petCooldown";
	private static final String PETCOUNT = "petCount";
	private static final String MAGICLEVEL = "magicLevel";
	private static final String SPEEDLEVEL = "speedLevel";
	private static final String REGENERATION_DELAY = "regenDelay";
	private static final String REGENERATION_POWER = "regenPower";

	private static final String CRITICAL = "criticalStrike";
	private static final String CRITICAL_CHANCE = "criticalChance";

	private static final String AMBUSH = "ambush";

	private static final String ENERGY = "emergy";

	private static final String RAGE = "rage";

	private static final String MASTERY_LEVEL = "mastery level";
	private static final String PHYSIC_LEVEL = "physic level";
	private static final String MANA = "mana";
	private static final String MANA_TOTAL = "mana total";
	private static final String ENERGY_TOTAL = "energy total";
	private static final String RAGE_TOTAL = "rage total";
	private static final String BONUS_DMG = "bonus damage";
	private static final String TXT_DR = "dr";

	private static final String ADD_AS = "addAS";
	private static final String BASE_MASTERY = "base mastery";
	private static final String BASE_PHYSIC = "base physical";
	private static final String BASE_MAGIC = "base magic";
	private static final String LIQ_DMG = "liq dmg";
	private static final String INVISIBILITY_REGEN = "invis regen";
	private static final String INVISIBILITY_SPEED = "invis speed";
	private static final String EATING_ENERGY = "eating energy";
	private static final String SPELL_BOOK = "spell book";


	@Override
	public void storeInBundle(Bundle bundle) {

		super.storeInBundle(bundle);

		heroClass.storeInBundle(bundle);
		subClass.storeInBundle(bundle);

		bundle.put(ATTACK, attackSkill);
		bundle.put(DEFENSE, defenseSkill);

		bundle.put(STRENGTH, STR);

		bundle.put(LEVEL, lvl);
		bundle.put(EXPERIENCE, exp);
		bundle.put(HASPET, haspet);
		bundle.put(PETFOLLOW, petfollow);
		bundle.put(PETTYPE, petType);
		bundle.put(PETLEVEL, petLevel);
		bundle.put(PETKILLS, petKills);
		bundle.put(PETHP, petHP);
		bundle.put(PETEXP, petExperience);
		bundle.put(PETCOOLDOWN, petCooldown);
		bundle.put(PETCOUNT, petCount);
		bundle.put(MAGICLEVEL, magicLevel);
		bundle.put(PHYSIC_LEVEL, physicLevel);
		bundle.put(MASTERY_LEVEL, masteryLevel);
		bundle.put(AMBUSH, ambushDamage);
		bundle.put(CRITICAL, critDamage);
		bundle.put(CRITICAL_CHANCE, critStrikeChance);
		bundle.put(SPEEDLEVEL, speed);
		bundle.put(REGENERATION_DELAY, regeneration_delay);
		bundle.put(REGENERATION_POWER, regeneration_power);
		bundle.put(LEVELUP, levelup);

		bundle.put(ENERGY, energy);
		bundle.put(RAGE, rage);
		bundle.put(MANA, MP);
		bundle.put(MANA_TOTAL, MT);
		bundle.put(ENERGY_TOTAL, energyTotal);
		bundle.put(RAGE_TOTAL, rageTotal);

		bundle.put(BONUS_DMG, bonusDamage);
		bundle.put(TXT_DR, DR);
		bundle.put(ADD_AS, additAS);
		bundle.put(BASE_MAGIC, baseMagic);
		bundle.put(BASE_MASTERY, baseMastery);
		bundle.put(BASE_PHYSIC, basePhysic);
		bundle.put(LIQ_DMG, liqDmg);
		bundle.put(INVISIBILITY_REGEN, invisRegen);
		bundle.put(INVISIBILITY_SPEED, invisSpeed);
		bundle.put(EATING_ENERGY, eatingEnergy);

		spellbook.storeInBundle(bundle);

		belongings.storeInBundle(bundle);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);

		heroClass = HeroClass.restoreInBundle(bundle);
		subClass = HeroSubClass.restoreInBundle(bundle);

		attackSkill = bundle.getInt(ATTACK);
		defenseSkill = bundle.getInt(DEFENSE);

		STR = bundle.getInt(STRENGTH);
		updateAwareness();

		lvl = bundle.getInt(LEVEL);
		exp = bundle.getInt(EXPERIENCE);
		haspet = bundle.getBoolean(HASPET);
		petfollow = bundle.getBoolean(PETFOLLOW);
		petType = bundle.getInt(PETTYPE);
		petLevel = bundle.getInt(PETLEVEL);
		petKills = bundle.getInt(PETKILLS);
		petHP = bundle.getInt(PETHP);
		petExperience = bundle.getInt(PETEXP);
		petCooldown = bundle.getInt(PETCOOLDOWN);
		petCount = bundle.getInt(PETCOUNT);
		magicLevel = bundle.getInt(MAGICLEVEL);
		masteryLevel = bundle.getInt(MASTERY_LEVEL);
		physicLevel = bundle.getInt(PHYSIC_LEVEL);
		critDamage = bundle.getInt(CRITICAL);
		critStrikeChance = bundle.getInt(CRITICAL_CHANCE);
		ambushDamage = bundle.getInt(AMBUSH);
		speed = bundle.getInt(SPEEDLEVEL);
		regeneration_delay = bundle.getFloat(REGENERATION_DELAY);
		regeneration_power = bundle.getInt(REGENERATION_POWER);

		rage = bundle.getInt(RAGE);
		energy = bundle.getInt(ENERGY);
		energyTotal = bundle.getInt(ENERGY_TOTAL);
		rageTotal = bundle.getInt(RAGE_TOTAL);
		MP = bundle.getInt(MANA);
		MT = bundle.getInt(MANA_TOTAL);

		bonusDamage = bundle.getInt(BONUS_DMG);
		DR = bundle.getInt(TXT_DR);

		additAS = bundle.getInt(ADD_AS);
		baseMastery = bundle.getInt(BASE_MASTERY);
		baseMagic = bundle.getInt(BASE_MAGIC);
		basePhysic = bundle.getInt(BASE_PHYSIC);
		liqDmg = bundle.getInt(LIQ_DMG);
		invisRegen = bundle.getBoolean(INVISIBILITY_REGEN);
		invisSpeed = bundle.getBoolean(INVISIBILITY_SPEED);
		eatingEnergy = bundle.getBoolean(EATING_ENERGY);
		levelup = bundle.getBoolean(LEVELUP);

		spellbook.clear();
		spellbook.restoreFromBundle(bundle);

		belongings.restoreFromBundle(bundle);
	}

	public static void preview(GamesInProgress.Info info, Bundle bundle) {
		info.level = bundle.getInt(LEVEL);
	}

	public String className() {
		return subClass == null || subClass == HeroSubClass.NONE ? heroClass
				.title() : subClass.title();
	}

	public String givenName() {
		return name.equals("you") ? className() : name;
	}

	public void live() {
		Buff.affect(this, Regeneration.class);
		Buff.affect(this, Hunger.class);
		Buff.affect(this, ManaRegen.class);
		Buff.affect(this, RageRegen.class);
		if(Dungeon.hero.heroClass == HeroClass.ROGUE){
			Buff.affect(this, EnergyRegen.class);
		}

	}

	public void adjustStats(){
		if(physicLevel != basePhysic){
			int diff = physicLevel - basePhysic;
			int i = physicLevel / 5 - basePhysic / 5;
			int i2 = physicLevel / 2 - basePhysic / 2;
			int i4 = physicLevel / 10 - basePhysic / 10;
			int i3 = physicLevel / 20 - basePhysic / 20;
			if(i > 0){
				STR += i;
				regeneration_delay -= i * 0.50f;
			}
			if(i4 > 0){
				regeneration_power += i4;
			}
			if(i2 > 0){
				if(i3 == 0){
					DR += i2 * Math.max(physicLevel / 20 + 1, 1);
				}else{
					DR += i2 * Math.max(Random.NormalIntRange(basePhysic / 20 + 1, physicLevel / 20 + 1), 1);
				}

			}
			if(diff > 0){
				if(i4 == 0){
					bonusDamage += diff * Math.max(physicLevel / 10 + 1, 1);
					HT += diff * Math.max(physicLevel / 10 + 1, 1);
				}else{
					bonusDamage += diff * Math.max(Random.NormalIntRange(basePhysic / 10 + 1, physicLevel / 10 + 1), 1);
					HT += diff * Math.max(Random.NormalIntRange(basePhysic / 10 + 1, physicLevel / 10 + 1), 1);
				}
			}
			basePhysic = physicLevel;
		}
		if(masteryLevel != baseMastery){
			int diff = masteryLevel - baseMastery;
//			int i = masteryLevel / 5 - baseMastery / 5;
//			int i2 = masteryLevel / 2 - baseMastery / 2;
//			int i3 = masteryLevel / 10 - baseMastery / 10;
			if(diff > 0){
				float ad = attackDelay();
				float part = 1.0f - critStrikeChance;
				for (int j = 0; j < diff; j++) {
					ambushDamage += 0.03f;
					speed += 0.02f;
					critDamage += 0.02f;
					additAS += ad * 0.03f;
					critStrikeChance += part * 0.03f;

					ad = attackDelay();
					part = 1.0f - critStrikeChance;
				}
			}
//			if(i > 0){
//				critDamage += 0.1f * i;
//			}
			baseMastery = masteryLevel;

		}
		if(magicLevel != baseMagic){
			int diff = magicLevel - baseMagic;
			if(diff > 0){
				MT += diff * 5;
			}
			baseMagic = magicLevel;
		}
		MP = MT;
		HP = HT;
	}

	public int getMasteryLevel(){
		return masteryLevel;
	}
	public int getPhysicLevel(){return physicLevel;}
	public int getMagicLevel(){return magicLevel;}
	public void increasePhysicalLvl(int value){physicLevel += value; adjustStats();}
	public void increaseMagicalLvl(int value){magicLevel += value; adjustStats();}
	public void increaseMasteryLvl(int value){masteryLevel += value; adjustStats();}

	public int tier() {
		return belongings.armor == null ? 0 : belongings.armor.tier;
	}

	public boolean shoot(Char enemy, MissileWeapon wep) {

		rangedWeapon = wep;
		boolean result = attack(enemy);
		Invisibility.dispel();
		rangedWeapon = null;

		return result;
	}

	@Override
	public int attackSkill(Char target) {
		float accuracy = 1;
		if (rangedWeapon != null && Level.distance(pos, target.pos) == 1) {
			accuracy *= 0.5f;
		}

		KindOfWeapon wep = rangedWeapon != null ? rangedWeapon
				: belongings.weapon;
		if (wep != null) {
			return (int) (attackSkill * accuracy * wep.acuracyFactor(this));
		} else {
			return (int) (attackSkill * accuracy);
		}
	}

	@Override
	public int defenseSkill(Char enemy) {

		int bonus = 0;
		for (Buff buff : buffs(RingOfEvasion.Evasion.class)) {
			bonus += ((RingOfEvasion.Evasion) buff).effectiveLevel;
		}

		float evasion = (float) Math.pow(1.15, bonus);
		if (paralysed) {
			evasion /= 2;
		}

		int aEnc = belongings.armor != null ? belongings.armor.STR - STR()
				: 9 - STR();

		if (aEnc > 0) {
			return (int) (defenseSkill * evasion / Math.pow(1.5, aEnc));
		} else {

			if (heroClass == HeroClass.ROGUE) {
				return (int) ((defenseSkill - aEnc) * evasion);
			} else {
				return (int) (defenseSkill * evasion);
			}
		}
	}

	@Override
	public int dr() {
		int dr = belongings.armor != null ? Math.max(belongings.armor.DR, 0)
				: 0;
		Barkskin barkskin = buff(Barkskin.class);
		if (barkskin != null) {
			dr += barkskin.level();
		}
		return DR + dr + (rage / 10 * Math.max(lvl / 2, 1));
	}

	@Override
	public int damageRoll() {
		KindOfWeapon wep = rangedWeapon != null ? rangedWeapon
				: belongings.weapon;
		int dmg;
		int bonus = 0;
		for (Buff buff : buffs(RingOfForce.Force.class)) {
			bonus += ((RingOfForce.Force) buff).level;
		}

		if (wep != null) {
			dmg = wep.damageRoll(this) + bonus;
		} else {
			int str = STR() - 8;
			dmg = bonus == 0 ? str > 1 ? Random.NormalIntRange(1, str) : 1
					: bonus > 0 ? str > 0 ? Random.NormalIntRange(str / 2
							+ bonus, (int) (str * 0.5f * bonus) + str * 2) : 1
							: 0;
		}
		if (dmg < 0)
			dmg = 0;

		if (buff(Fury.class) != null){ dmg *= 1.5f; }

		if (buff(Strength.class) != null){ dmg *= 4f; Buff.detach(this, Strength.class);}

		if(this.heroClass == HeroClass.WARRIOR){
			int i = physicLevel / 4 + 1;
			int i2 = Math.max((rage / 5) * i, i);
			return (int) criticalStrike(dmg + bonusDamage + i2);
		}else{
			return (int) criticalStrike(dmg + bonusDamage);
		}

	}

	public int damageMin() {
		KindOfWeapon wep = rangedWeapon != null ? rangedWeapon
				: belongings.weapon;
		int dmg;
		int bonus = 0;
		for (Buff buff : buffs(RingOfForce.Force.class)) {
			bonus += ((RingOfForce.Force) buff).level;
		}

		if (wep != null) {
			dmg = wep.damageMin(this) + bonus;
		} else {
			int str = STR() - 8;
			dmg = bonus == 0 ? 1
					: bonus > 0 ? str > 0 ? str / 2 + bonus : 1
					: 0;
		}
		if (dmg < 0)
			dmg = 0;

		if (buff(Fury.class) != null){ dmg *= 1.5f; }

		if (buff(Strength.class) != null){ dmg *= 4f; Buff.detach(this, Strength.class);}

		if(this.heroClass == HeroClass.WARRIOR){
			int i = physicLevel / 4 + 1;
			int i2 = Math.max((rage / 5) * Math.max(i, 1), i);
			return (int) criticalStrike(dmg + bonusDamage + i2);
		}else{
			return (int) criticalStrike(dmg + bonusDamage);
		}

	}

	public int damageMax() {
		KindOfWeapon wep = rangedWeapon != null ? rangedWeapon
				: belongings.weapon;
		int dmg;
		int bonus = 0;
		for (Buff buff : buffs(RingOfForce.Force.class)) {
			bonus += ((RingOfForce.Force) buff).level;
		}

		if (wep != null) {
			dmg = wep.damageMax(this) + bonus;
		} else {
			int str = STR() - 8;
			dmg = bonus == 0 ? str
					: bonus > 0 ? str > 0 ? (int) (str * 0.5f * bonus) + str * 2 : 1
					: 0;
		}
		if (dmg < 0)
			dmg = 0;

		if (buff(Fury.class) != null){ dmg *= 1.5f; }

		if (buff(Strength.class) != null){ dmg *= 4f; Buff.detach(this, Strength.class);}

		if(this.heroClass == HeroClass.WARRIOR){
			int i = physicLevel / 4 + 1;
			int i2 = Math.max((rage / 5) * Math.max(i, 1), i);
			return (int) criticalStrike(dmg + bonusDamage + i2);
		}else{
			return (int) criticalStrike(dmg + bonusDamage);
		}

	}

	public float criticalStrike(float dmg){
		if(Random.Float() - critStrikeChance <= 0) {
			GLog.h("Critical Strike!");
			critDmg = true;
			return dmg * critDamage + critDamage;
		}else{
			critDmg = false;
			return dmg;
		}
	}

	public float getSpeed(){return speed;}


	@Override
	public float speed() {

		float speed = super.speed();

		int hasteLevel = 0;
		for (Buff buff : buffs(RingOfHaste.Haste.class)) {
			hasteLevel += ((RingOfHaste.Haste) buff).level;
		}

		if(haspet){
		  int pethaste=Dungeon.petHasteLevel;
		  PET heropet = checkpet();

		   if(pethaste>0 && hasteLevel>10 && heropet!=null){
			 hasteLevel=10;
		   }

		}



		if (hasteLevel != 0)
			speed *= Math.pow(1.2, hasteLevel);

		int aEnc = belongings.armor != null ? belongings.armor.STR - STR() : 0;
		if (aEnc > 0) {

			return (float) (speed * Math.pow(1.3, -aEnc));

		} else {

			return ((HeroSprite) sprite)
					.sprint(subClass == HeroSubClass.FREERUNNER
							&& !isStarving()) ? invisible > 0 ? 4f * speed
					: 1.5f * speed : speed;

		}
	}

	public float attackDelay() {
		KindOfWeapon wep = rangedWeapon != null ? rangedWeapon
				: belongings.weapon;
		if (wep != null) {

			return wep.speedFactor(this);

		} else {
			// Normally putting furor speed on unarmed attacks would be
			// unnecessary
			// But there's going to be that one guy who gets a furor+force ring
			// combo
			// This is for that one guy, you shall get your fists of fury!
			int bonus = 0;
			for (Buff buff : buffs(RingOfFuror.Furor.class)) {
				bonus += ((RingOfFuror.Furor) buff).level;
			}
			float f;
			if(missingEnergy != 0){
				f = missingEnergy * 5;
			}else{
				f = energy * 0.5f;
			}

			return (float) Math.max ((0.25 + (1 - 0.25) * Math.pow(0.8, bonus)) - (Dungeon.hero.additAS + f * 0.01f), 0.05f);
		}
	}

	@Override
	public void spend(float time) {
		TimekeepersHourglass.timeFreeze buff = buff(TimekeepersHourglass.timeFreeze.class);
		if (!(buff != null && buff.processTime(time)))
			super.spend(time);

		for(Spell spell: Dungeon.hero.spellbook.getSpells()){
			if(spell.isActive()) {
				spell.tick(time);
			}
			//GLog.p(spell.name() + " ticks on " + time);
		}
		if(!(curAction instanceof HeroAction.Move || curAction instanceof HeroAction.Attack)
				&& this.heroClass == HeroClass.ROGUE){
			crTime--;
			if(crTime == 0){
				Buff.affect(this, PoweredEnergyRegen.class);
			}
		}else{
			if(crTime != 3) {
				crTime = 3;
				Buff.detach(this, PoweredEnergyRegen.class);
			}
		}
	}
private float crTime = 3;
	public void spendAndNext(float time) {
		busy();
		spend(time);
		next();
	}

	@Override
	public boolean act() {

		super.act();

/*
		if(levelup){
			GameScene.show(new WndLevelUp(this));
			Dungeon.observe();
			ready();
		}
*/
		Statistics.moves++;

		if(Dungeon.dewDraw){Dungeon.level.currentmoves++;}

		if (paralysed) {

			curAction = null;

			spendAndNext(TICK);
			return false;
		}

		Egg egg = belongings.getItem(Egg.class);
		if (egg!=null){
			egg.moves++;
		}

		EasterEgg egg2 = belongings.getItem(EasterEgg.class);
		if (egg2!=null){
			egg2.moves++;
		}

		ShadowDragonEgg egg3 = belongings.getItem(ShadowDragonEgg.class);
		if (egg3!=null){
			egg3.moves++;
		}

		OtilukesJournal journal = belongings.getItem(OtilukesJournal.class);
		if (journal!=null && (Dungeon.depth < 26 || Dungeon.depth==55)
				&& (journal.level>1 || journal.rooms[0])
				&& journal.charge<journal.fullCharge){
			journal.charge++;
			if (journal.charge>=journal.fullCharge){
				GLog.p("Otilike's Journal is fully charged!");
			}
		}

		/*
		Heap heap = Dungeon.level.heaps.get(pos);
		if (heap != null){
			heap.dewcollect();
		}
		*/

		checkVisibleMobs();

		if (curAction == null) {

			if (restoreHealth) {
				if (isStarving() || HP >= HT) {
					restoreHealth = false;
				} else {
					//System.out.println("ajajajajajajaajajJAAJAJAJAJAJA");
					spend(TIME_TO_REST);
					next();
					return false;
				}
			}

			ready();
			return false;

		} else {


			restoreHealth = false;

			ready = false;

			if (curAction instanceof HeroAction.Move) {

				return actMove((HeroAction.Move) curAction);

			} else if (curAction instanceof HeroAction.Interact) {

				return actInteract((HeroAction.Interact) curAction);

			} else if (curAction instanceof HeroAction.InteractPet) {

				return actInteractPet((HeroAction.InteractPet) curAction);

			} else if (curAction instanceof HeroAction.Buy) {

				return actBuy((HeroAction.Buy) curAction);

			} else if (curAction instanceof HeroAction.PickUp) {

				return actPickUp((HeroAction.PickUp) curAction);

			} else if (curAction instanceof HeroAction.OpenChest) {

				return actOpenChest((HeroAction.OpenChest) curAction);

			} else if (curAction instanceof HeroAction.Unlock) {

				return actUnlock((HeroAction.Unlock) curAction);

			} else if (curAction instanceof HeroAction.Descend) {

				return actDescend((HeroAction.Descend) curAction);

			} else if (curAction instanceof HeroAction.Ascend) {

				return actAscend((HeroAction.Ascend) curAction);

			} else if (curAction instanceof HeroAction.Attack) {

				return actAttack((HeroAction.Attack) curAction);

			} else if (curAction instanceof HeroAction.Cook) {

				return actCook((HeroAction.Cook) curAction);

			}
		}

		return false;
	}

	public void busy() {
		ready = false;
	}

	private void ready() {
		sprite.idle();
		curAction = null;
		damageInterrupt = true;
		ready = true;

		AttackIndicator.updateState();

		GameScene.ready();
	}

	public void interrupt() {
		if (isAlive() && curAction != null
				&& curAction instanceof HeroAction.Move && curAction.dst != pos) {
			lastAction = curAction;
		}
		curAction = null;
	}

	public void resume() {
		curAction = lastAction;
		lastAction = null;
		damageInterrupt = false;
		act();
	}




	private boolean actMove(HeroAction.Move action) {

		if (getCloser(action.dst)) {

			boolean z = this.buff(PoweredEnergyRegen.class) == null;
			if ((this.buff(Invisibility.class) != null || this.buff(CloakOfShadows.cloakStealth.class) != null) && invisRegen) {
							if (z) {
				Buff.affect(this, PoweredEnergyRegen.class);
			}
			}else{
				if(!z){
					Buff.detach(this, PoweredEnergyRegen.class);
					crTime = 0;
				}
			}

			return true;

		} else {
			if (Dungeon.level.map[pos] == Terrain.SIGN && pos != Dungeon.level.pitSign) {
				Sign.read(pos);
			} else if (Dungeon.level.map[pos] == Terrain.SIGN && pos == Dungeon.level.pitSign){
				Sign.readPit(pos);
			}
			ready();

			return false;
		}
	}

	private boolean actInteract(HeroAction.Interact action) {

		NPC npc = action.npc;

		if (Level.adjacent(pos, npc.pos)) {

			ready();
			sprite.turnTo(pos, npc.pos);
			npc.interact();
			return false;

		} else {

			if (Level.fieldOfView[npc.pos] && getCloser(npc.pos)) {

				return true;

			} else {
				ready();
				return false;
			}

		}
	}

	private boolean actInteractPet(HeroAction.InteractPet action) {

		PET pet = action.pet;

		if (Level.adjacent(pos, pet.pos)) {

			ready();
			sprite.turnTo(pos, pet.pos);
			pet.interact();
			return false;

		} else {

			if (Level.fieldOfView[pet.pos] && getCloser(pet.pos)) {

				return true;

			} else {
				ready();
				return false;
			}

		}
	}

	private boolean actBuy(HeroAction.Buy action) {
		int dst = action.dst;
		if (pos == dst || Level.adjacent(pos, dst)) {

			ready();

			Heap heap = Dungeon.level.heaps.get(dst);
			if (heap != null && heap.type == Type.FOR_SALE && heap.size() == 1) {
				GameScene.show(new WndTradeItem(heap, true));
			}

			return false;

		} else if (getCloser(dst)) {

			return true;

		} else {
			ready();
			return false;
		}
	}

	private boolean actCook(HeroAction.Cook action) {
		int dst = action.dst;
		if (Dungeon.visible[dst]) {

			ready();
			AlchemyPot.operate(this, dst);
			return false;

		} else if (getCloser(dst)) {

			return true;

		} else {
			ready();
			return false;
		}
	}

	private boolean actPickUp(HeroAction.PickUp action) {
		int dst = action.dst;
		if (pos == dst) {

			Heap heap = Dungeon.level.heaps.get(pos);
			if (heap != null) {
				Item item = heap.pickUp();
				if (item.doPickUp(this)) {

					if (item instanceof Dewdrop
							|| item instanceof TimekeepersHourglass.sandBag
							|| item instanceof DriedRose.Petal) {
						// Do Nothing
					} else {

						boolean important = ((item instanceof ScrollOfUpgrade || item instanceof ScrollOfMagicalInfusion) && ((Scroll) item)
								.isKnown())
								|| ((item instanceof PotionOfStrength || item instanceof PotionOfMight) && ((Potion) item)
										.isKnown());
						if (important) {
							GLog.p(TXT_YOU_NOW_HAVE, item.name());
						} else {
							GLog.i(TXT_YOU_NOW_HAVE, item.name());
						}

						// Alright, if anyone complains about not knowing the
						// vial doesn't revive
						// after this... I'm done, I'm just done.
						if (item instanceof DewVial) {
							GLog.w("Its revival power seems to have faded.");
							GameScene.show(new WndDewVial(item));
						}
                       /*
						if (item instanceof LevelDewdrop) {
							GLog.w("You found life drop!");
							GameScene.show(new WndLevelUp(item));

						}
						*/
					}

					if (!heap.isEmpty()) {
						GLog.i(TXT_SOMETHING_ELSE);
					}
					curAction = null;
				} else {
				/*
					Dungeon.level.drop(item, pos).sprite.drop();
					*/
					ready();
				}
			} else {
				ready();
			}

			return false;

		} else if (getCloser(dst)) {

			return true;

		} else {
			ready();
			return false;
		}
	}

	private boolean actOpenChest(HeroAction.OpenChest action) {
		int dst = action.dst;
		if (Level.adjacent(pos, dst) || pos == dst) {

			Heap heap = Dungeon.level.heaps.get(dst);
			if (heap != null
					&& (heap.type != Type.HEAP && heap.type != Type.FOR_SALE)) {

				theKey = null;
				theSkeletonKey = null;

				if (heap.type == Type.LOCKED_CHEST
						|| heap.type == Type.CRYSTAL_CHEST
						//|| heap.type == Type.MONSTERBOX
						) {

					theKey = belongings.getKey(GoldenKey.class, Dungeon.depth);
					theSkeletonKey = belongings.getKey(GoldenSkeletonKey.class, 0);

					if (theKey == null && theSkeletonKey == null) {
						GLog.w(TXT_LOCKED_CHEST);
						ready();
						return false;
					}
				}

				switch (heap.type) {
				case TOMB:
					Sample.INSTANCE.play(Assets.SND_TOMB);
					Camera.main.shake(1, 0.5f);
					break;
				case SKELETON:
				case REMAINS:
					break;
				default:
					Sample.INSTANCE.play(Assets.SND_UNLOCK);
				}

				spend(Key.TIME_TO_UNLOCK);
				sprite.operate(dst);

			} else {
				ready();
			}

			return false;

		} else if (getCloser(dst)) {

			return true;

		} else {
			ready();
			return false;
		}
	}

	private boolean actUnlock(HeroAction.Unlock action) {
		int doorCell = action.dst;
		if (Level.adjacent(pos, doorCell)) {

			theKey = null;
			int door = Dungeon.level.map[doorCell];

			if (door == Terrain.LOCKED_DOOR) {

				theKey = belongings.getKey(IronKey.class, Dungeon.depth);

			} else if (door == Terrain.LOCKED_EXIT) {

				theKey = belongings.getKey(SkeletonKey.class, Dungeon.depth);

			}

			if (theKey != null) {

				spend(Key.TIME_TO_UNLOCK);
				sprite.operate(doorCell);

				Sample.INSTANCE.play(Assets.SND_UNLOCK);

			} else {
				GLog.w(TXT_LOCKED_DOOR);
				ready();
			}

			return false;

		} else if (getCloser(doorCell)) {

			return true;

		} else {
			ready();
			return false;
		}
	}

	private PET checkpet(){
		for (Mob mob : Dungeon.level.mobs) {
			if(mob instanceof PET) {
				return (PET) mob;
			}
		}
		return null;
	}

	private boolean checkpetNear(){
		for (int n : Level.NEIGHBOURS8) {
			int c = pos + n;
			if (Actor.findChar(c) instanceof PET) {
				return true;
			}
		}
		return false;
	}

	private boolean actDescend(HeroAction.Descend action) {
		int stairs = action.dst;

		if (!Dungeon.level.forcedone &&
			 Dungeon.dewDraw && (
					 Dungeon.level.checkdew()>0
				     || Dungeon.hero.buff(Dewcharge.class) != null)
				    ) {

			GameScene.show(new WndDescend());
			ready();
			return false;
		}

		if (!Dungeon.level.forcedone &&
				 Dungeon.dewDraw &&
				 !Dungeon.level.cleared &&
				 !Dungeon.notClearableLevel(Dungeon.depth)
				 ) {

				GameScene.show(new WndDescend());
				ready();
				return false;
			}


		if (pos == stairs && pos == Dungeon.level.exit && !Dungeon.level.sealedlevel){

			curAction = null;

			if(Dungeon.dewDraw){
			 for (int i = 0; i < Level.LENGTH; i++) {
				Heap heap = Dungeon.level.heaps.get(i);
				if (heap != null)
					heap.dryup();
			  }

			 if (!Dungeon.level.cleared && Dungeon.dewDraw && !Dungeon.notClearableLevel(Dungeon.depth) ){
				 Dungeon.level.cleared=true;
				 Statistics.prevfloormoves=0;
			 }

			}


			PET pet = checkpet();
			if(pet!=null && checkpetNear()){
			  Dungeon.hero.petType=pet.type;
			  Dungeon.hero.petLevel=pet.level;
			  Dungeon.hero.petKills=pet.kills;
			  Dungeon.hero.petHP=pet.HP;
			  Dungeon.hero.petExperience=pet.experience;
			  Dungeon.hero.petCooldown=pet.cooldown;
			  pet.destroy();
			  petfollow=true;
			} else if (Dungeon.hero.haspet && Dungeon.hero.petfollow) {
				petfollow=true;
			} else {
				petfollow=false;
			}

			Buff buff = buff(TimekeepersHourglass.timeFreeze.class);
			if (buff != null) buff.detach();

			for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0]))
				if (mob instanceof DriedRose.GhostHero)
					mob.destroy();

			InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
			Game.switchScene(InterlevelScene.class);

			return false;

		} else if (getCloser(stairs)) {

			return true;

		} else {
			ready();
			return false;
		}
	}

	private boolean actAscend(HeroAction.Ascend action) {
		int stairs = action.dst;
		if (pos == stairs && pos == Dungeon.level.entrance) {

			if (Dungeon.depth == 1) {

				if (belongings.getItem(Amulet.class) == null) {
					GameScene.show(new WndMessage(TXT_LEAVE));
					ready();

				} else if (Dungeon.level.forcedone){
					Dungeon.win(ResultDescriptions.WIN);
					Dungeon.deleteGame(Dungeon.hero.heroClass, true);
					Game.switchScene(SurfaceScene.class);
				} else {
					GameScene.show(new WndAscend());
					ready();
				}

			} else if (Dungeon.depth == 34) {
				curAction = null;

				Hunger hunger = buff(Hunger.class);
				if (hunger != null && !hunger.isStarving()) {
					hunger.satisfy(-Hunger.STARVING / 10);
				}

				PET pet = checkpet();
				if(pet!=null && checkpetNear()){
				  Dungeon.hero.petType=pet.type;
				  Dungeon.hero.petLevel=pet.level;
				  Dungeon.hero.petKills=pet.kills;
				  Dungeon.hero.petHP=pet.HP;
				  Dungeon.hero.petExperience=pet.experience;
				  Dungeon.hero.petCooldown=pet.cooldown;
				  pet.destroy();
				  petfollow=true;
				} else if (Dungeon.hero.haspet && Dungeon.hero.petfollow) {
					petfollow=true;
				} else {
					petfollow=false;
				}

				Buff buff = buff(TimekeepersHourglass.timeFreeze.class);
				if (buff != null)
					buff.detach();

				for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0]))
					if (mob instanceof DriedRose.GhostHero)
						mob.destroy();

				InterlevelScene.mode = InterlevelScene.Mode.ASCEND;
				Game.switchScene(InterlevelScene.class);


		    } else if (Dungeon.depth == 41) {
			curAction = null;

			Hunger hunger = buff(Hunger.class);
			if (hunger != null && !hunger.isStarving()) {
				hunger.satisfy(-Hunger.STARVING / 10);
			}

			PET pet = checkpet();
			if(pet!=null && checkpetNear()){
			  Dungeon.hero.petType=pet.type;
			  Dungeon.hero.petLevel=pet.level;
			  Dungeon.hero.petKills=pet.kills;
			  Dungeon.hero.petHP=pet.HP;
			  Dungeon.hero.petExperience=pet.experience;
			  Dungeon.hero.petCooldown=pet.cooldown;
			  pet.destroy();
			  petfollow=true;
			} else if (Dungeon.hero.haspet && Dungeon.hero.petfollow) {
				petfollow=true;
			} else {
				petfollow=false;
			}

			Buff buff = buff(TimekeepersHourglass.timeFreeze.class);
			if (buff != null)
				buff.detach();

			for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0]))
				if (mob instanceof DriedRose.GhostHero)
					mob.destroy();

			InterlevelScene.mode = InterlevelScene.Mode.ASCEND;
			Game.switchScene(InterlevelScene.class);

		   } else if (Dungeon.depth > 26 && !Dungeon.townCheck(Dungeon.depth)){
				ready();
			} else if (Dungeon.depth == 25 || Dungeon.depth == 55 || Dungeon.depth == 99){
				ready();
			} else if (Dungeon.depth > 55 && Dungeon.level.locked){
				ready();
			} else {

				curAction = null;

				Hunger hunger = buff(Hunger.class);
				if (hunger != null && !hunger.isStarving()) {
					hunger.satisfy(-Hunger.STARVING / 10);
				}

				PET pet = checkpet();
				if(pet!=null && checkpetNear()){
				  Dungeon.hero.petType=pet.type;
				  Dungeon.hero.petLevel=pet.level;
				  Dungeon.hero.petKills=pet.kills;
				  Dungeon.hero.petHP=pet.HP;
				  Dungeon.hero.petExperience=pet.experience;
				  Dungeon.hero.petCooldown=pet.cooldown;
				  pet.destroy();
				  petfollow=true;
				} else if (Dungeon.hero.haspet && Dungeon.hero.petfollow) {
					petfollow=true;
				} else {
					petfollow=false;
				}

				Buff buff = buff(TimekeepersHourglass.timeFreeze.class);
				if (buff != null)
					buff.detach();

				for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0]))
					if (mob instanceof DriedRose.GhostHero)
						mob.destroy();

				InterlevelScene.mode = InterlevelScene.Mode.ASCEND;
				Game.switchScene(InterlevelScene.class);
			}

			return false;

		} else if (getCloser(stairs)) {

			return true;

		} else {
			ready();
			return false;
		}
	}

	private void rageAttack(){
		//int lvl = Math.max(physicLevel / 5, 2);
		rage += Random.NormalIntRange(3, 5);
		if(rage > rageTotal){
			rage = rageTotal;
		}
	}

	private void energyAttack(){
		//int lvl = Math.min(500 / masteryLevel, 14);
		if(liquidState()){
			energy -= Random.NormalIntRange(55, 65);
			liquid = true;
		}else{
			energy -= Random.NormalIntRange(8, 12);
			liquid = false;
		}
		if(energy < 0){
			missingEnergy = energy;
			energy = 0;
		}else{
			missingEnergy = 0;
		}
	}

	public boolean liquidState(){
		return energy == 100 && (this.buff(Invisibility.class) != null || this.buff(CloakOfShadows.cloakStealth.class) != null);
	}

	private boolean actAttack(HeroAction.Attack action) {


		enemy = action.target;

		boolean z = this.buff(PoweredEnergyRegen.class) == null;
		if(!z){
			Buff.detach(this, PoweredEnergyRegen.class);
			crTime = 0;
		}

		//rage += 3;

		if (Level.adjacent(pos, enemy.pos) && enemy.isAlive()
				&& !isCharmedBy(enemy)) {

			spend(attackDelay());
			sprite.attack(enemy.pos);

			return false;

		} else {

			if (Level.fieldOfView[enemy.pos] && getCloser(enemy.pos)) {

				return true;

			} else {
				ready();
				return false;
			}

		}
	}

	public void rest(boolean tillHealthy) {
		//search(true);
		spendAndNext(TIME_TO_REST);
		if (!tillHealthy) {
			sprite.showStatus(CharSprite.DEFAULT, TXT_WAIT);
		}
		restoreHealth = tillHealthy;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		KindOfWeapon wep = rangedWeapon != null ? rangedWeapon
				: belongings.weapon;

		if (wep != null)
			wep.proc(this, enemy, damage);

		switch (subClass) {
		case GLADIATOR:
			if (wep instanceof MeleeWeapon || wep == null) {
				damage += Buff.affect(this, Combo.class).hit(enemy, damage);
			}
			break;
		case BATTLEMAGE:
			if (wep instanceof Wand) {
				Wand wand = (Wand) wep;
				if (wand.curCharges < wand.maxCharges && damage > 0) {

					wand.curCharges++;
					if (Dungeon.quickslot.contains(wand)) {
						QuickSlotButton.refresh();
					}

					ScrollOfRecharging.charge(this);
				}
				damage += wand.curCharges;
			}
			break;
		case SNIPER:
			if (rangedWeapon != null) {
				Buff.prolong(this, SnipersMark.class, attackDelay() * 1.1f).object = enemy
						.id();
			}
			break;
		default:
		}

		return damage;
	}

	@Override
	public int defenseProc(Char enemy, int damage) {

		CapeOfThorns.Thorns thorns = buff(CapeOfThorns.Thorns.class);
		if (thorns != null) {
			damage = thorns.proc(damage, enemy);
		}

		Earthroot.Armor armor = buff(Earthroot.Armor.class);
		if (armor != null) {
			damage = armor.absorb(damage);
		}

		Sungrass.Health health = buff(Sungrass.Health.class);
		if (health != null) {
			health.absorb(damage);
		}

		if (belongings.armor != null) {
			damage = belongings.armor.proc(enemy, this, damage);
		}

		if(this.heroClass == HeroClass.WARRIOR){
			if(damage > HT / 3){
				rage += Random.NormalIntRange(13, 17);
				this.sprite.centerEmitter().start(Speck.factory(Speck.SCREAM), 0.3f,
						3);
				Sample.INSTANCE.play(Assets.SND_CHALLENGE);
			}else{
				rage += Random.NormalIntRange(4, 7);
			}
		}


		return damage;
	}

	@Override
	public void damage(int dmg, Object src) {
		if (buff(TimekeepersHourglass.timeStasis.class) != null)
			return;

		restoreHealth = false;

		if (!(src instanceof Hunger || src instanceof Viscosity.DeferedDamage)
				&& damageInterrupt)
			interrupt();

		if (this.buff(Drowsy.class) != null) {
			Buff.detach(this, Drowsy.class);
			GLog.w("The pain helps you resist the urge to sleep.");
		}

		int tenacity = 0;
		for (Buff buff : buffs(RingOfTenacity.Tenacity.class)) {
			tenacity += ((RingOfTenacity.Tenacity) buff).level;
		}
		if (tenacity != 0) // (HT - HP)/HT = heroes current % missing health.
			dmg = (int) Math.ceil(dmg
					* Math.pow(0.9, tenacity * ((float) (HT - HP) / HT)));

		super.damage(dmg, src);

		if (subClass == HeroSubClass.BERSERKER && 0 < HP
				&& HP <= HT * Fury.LEVEL) {
			Buff.affect(this, Fury.class);
		}

		if (this.buff(AutoHealPotion.class) != null && ((float) HP / HT)<.1) {
			PotionOfHealing pot = Dungeon.hero.belongings.getItem(PotionOfHealing.class);
			if (pot != null) {
				pot.detach(Dungeon.hero.belongings.backpack,1);
				/*
				if(!(Dungeon.hero.belongings.getItem(PotionOfHealing.class).quantity() > 0)){
					pot.detachAll(Dungeon.hero.belongings.backpack);
				}
				*/
				GLog.w("AutoPotion Triggered!");
				pot.apply(this);
			}
			else if (pot==null){
				GLog.w("AutoPotion triggered but you are not carrying any potions of healing!");
			}

		}

	}

	private void checkVisibleMobs() {
		ArrayList<Mob> visible = new ArrayList<Mob>();

		boolean newMob = false;
		Mob closest = null;

		for (Mob m : Dungeon.level.mobs) {
			if (Level.fieldOfView[m.pos] && m.hostile) {
				visible.add(m);
				if (!visibleEnemies.contains(m)) {
					newMob = true;
				}
				if (closest == null){
									closest = m;
								} else if (distance(closest) > distance(m)) {
									closest = m;
								}
			}
		}

			if (closest != null && (QuickSlotButton.lastTarget == null ||
												!QuickSlotButton.lastTarget.isAlive() ||
												!Dungeon.visible[QuickSlotButton.lastTarget.pos])){
								QuickSlotButton.target(closest);
		}

		if (newMob) {
			interrupt();
			restoreHealth = false;
		}

		visibleEnemies = visible;
	}

	public int visibleEnemies() {
		return visibleEnemies.size();
	}

	public Mob visibleEnemy(int index) {
		return visibleEnemies.get(index % visibleEnemies.size());
	}

	private boolean getCloser(final int target) {

		if (rooted) {
			Camera.main.shake(1, 1f);
			return false;
		}

		int step = -1;

		if (Level.adjacent(pos, target)) {

			if (Actor.findChar(target) == null) {
				if (Level.pit[target] && !flying && !Chasm.jumpConfirmed) {
					if (!Level.solid[target]) {
						Chasm.heroJump(this);
						interrupt();
					}
					return false;
				}
				if (Level.passable[target] || Level.avoid[target]) {
					step = target;
				}
			}

		} else {

			int len = Level.getLength();
			boolean[] p = Level.passable;
			boolean[] v = Dungeon.level.visited;
			boolean[] m = Dungeon.level.mapped;
			boolean[] passable = new boolean[len];
			for (int i = 0; i < len; i++) {
				passable[i] = p[i] && (v[i] || m[i]);
			}

			step = Dungeon.findPath(this, pos, target, passable,
					Level.fieldOfView);
		}

		if (step != -1) {

			int oldPos = pos;
			move(step);
			sprite.move(oldPos, pos);
			spend(1 / speed());

			return true;

		} else {

			return false;

		}

	}

	public boolean handle(int cell) {

		if (cell == -1) {
			return false;
		}

		Char ch;
		Heap heap;

		if (Dungeon.level.map[cell] == Terrain.ALCHEMY && cell != pos) {

			curAction = new HeroAction.Cook(cell);

		} else if (Level.fieldOfView[cell]
				&& (ch = Actor.findChar(cell)) instanceof Mob) {

			if (ch instanceof NPC) {
				curAction = new HeroAction.Interact((NPC) ch);
			} else if (ch instanceof PET) {
					curAction = new HeroAction.InteractPet((PET) ch);
			} else {
				curAction = new HeroAction.Attack(ch);
			}

		} else if ((heap = Dungeon.level.heaps.get(cell)) != null) {

			switch (heap.type) {
			case HEAP:
				curAction = new HeroAction.PickUp(cell);
				break;
			case FOR_SALE:
				curAction = heap.size() == 1 && heap.peek().price() > 0 ? new HeroAction.Buy(
						cell) : new HeroAction.PickUp(cell);
				break;
			default:
				curAction = new HeroAction.OpenChest(cell);
			}

		} else if (Dungeon.level.map[cell] == Terrain.LOCKED_DOOR
				|| Dungeon.level.map[cell] == Terrain.LOCKED_EXIT) {

			curAction = new HeroAction.Unlock(cell);

		} else if (cell == Dungeon.level.exit && (Dungeon.depth < 26 || Dungeon.townCheck(Dungeon.depth))) {

			curAction = new HeroAction.Descend(cell);

		} else if (cell == Dungeon.level.entrance) {

			curAction = new HeroAction.Ascend(cell);

		} else {

			curAction = new HeroAction.Move(cell);
			lastAction = null;

		}

		return act();
	}

	public boolean earnExp(int exp) {

		this.exp += exp;

		boolean levelUp = false;
		while (this.exp >= maxExp()) {
			this.exp -= maxExp();
			lvl++;

			HT += 5;
			HP += 5;
			attackSkill++;
			defenseSkill++;
			switch (this.heroClass){
				case WARRIOR: physicLevel++;
				if(lvl % 5 == 0) {magicLevel++; masteryLevel++;}break;
				case ROGUE:
				case HUNTRESS:
					masteryLevel++;
					if(lvl % 5 == 0) {magicLevel++; physicLevel++;}break;
				case MAGE: magicLevel++;
					if(lvl % 5 == 0) {physicLevel++; masteryLevel++;}break;
			}

			if (lvl < 10) {
				updateAwareness();
			}

			levelUp = true;
		}

		if (levelUp) {

			GLog.p(TXT_NEW_LEVEL, lvl);
			sprite.showStatus(CharSprite.POSITIVE, TXT_LEVEL_UP);
			Sample.INSTANCE.play(Assets.SND_LEVELUP);

			Badges.validateLevelReached();

			int value = HT - HP;
			if (value > 0) {
				HP += value;
				sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
			}

			if (subClass == HeroSubClass.WARLOCK) {

				int value2 = lvl;
				if (value2 > 0) {
					HP = HT+value2;
					sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);

					GLog.w(TXT_OVERFILL, lvl);
				}


			}

			buff(Hunger.class).satisfy(10);

			levelup = true;

			//if(lvl % 5 == 0){
				GameScene.show(new WndLevelUp(this, lvl));
			//}

			adjustStats();
		}

		if (subClass == HeroSubClass.WARLOCK) {

			int value = Math.min(HT - HP, 1 + (Dungeon.depth - 1) / 5);
			if (value > 0) {
				HP += value;
				sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
			}

			buff(Hunger.class).satisfy(100);
		}

		return levelUp;
	}

	public int maxExp() {
		return 5 + lvl * 5;
	}

	void updateAwareness() {
		awareness = (float) (1 - Math.pow((heroClass == HeroClass.ROGUE ? 0.85
				: 0.90), (1 + Math.min(lvl, 9)) * 0.5));
	}

	public boolean isStarving() {
		return buff(Hunger.class) != null
				&& buff(Hunger.class).isStarving();
	}

	@Override
	public void add(Buff buff) {

		if (buff(TimekeepersHourglass.timeStasis.class) != null)
			return;

		super.add(buff);

		if (sprite != null) {
			if (buff instanceof Burning) {
				GLog.w("You catch fire!");
				interrupt();
			} else if (buff instanceof Paralysis) {
				GLog.w("You are paralysed!");
				interrupt();
			} else if (buff instanceof Poison) {
				GLog.w("You are poisoned!");
				interrupt();
			} else if (buff instanceof Ooze) {
				GLog.w("Caustic ooze eats your flesh. Wash it away!");
			} else if (buff instanceof Roots) {
				GLog.w("You can't move!");
			} else if (buff instanceof Weakness) {
				GLog.w("You feel weakened!");
			} else if (buff instanceof Blindness) {
				GLog.w("You are blinded!");
			} else if (buff instanceof Fury) {
				GLog.w("You become furious!");
				sprite.showStatus(CharSprite.POSITIVE, "furious");
			} else if (buff instanceof Charm) {
				GLog.w("You are charmed!");
			} else if (buff instanceof Cripple) {
				GLog.w("You are crippled!");
			} else if (buff instanceof Bleeding) {
				GLog.w("You are bleeding!");
			} else if (buff instanceof RingOfMight.Might) {
				if (((RingOfMight.Might) buff).level > 0) {
					HT += ((RingOfMight.Might) buff).level * 5;
				}
			} else if (buff instanceof Vertigo) {
				GLog.w("Everything is spinning around you!");
				interrupt();
			}

			else if (buff instanceof Light) {
				sprite.add(CharSprite.State.ILLUMINATED);
			}
		}

		BuffIndicator.refreshHero();
	}

	@Override
	public void remove(Buff buff) {
		super.remove(buff);

		if (buff instanceof Light) {
			sprite.remove(CharSprite.State.ILLUMINATED);
		} else if (buff instanceof RingOfMight.Might) {
			if (((RingOfMight.Might) buff).level > 0) {
				HT -= ((RingOfMight.Might) buff).level * 5;
				HP = Math.min(HT, HP);
			}
		}

		BuffIndicator.refreshHero();
	}

	@Override
	public int stealth() {
		int stealth = super.stealth();
		for (Buff buff : buffs(RingOfEvasion.Evasion.class)) {
			stealth += ((RingOfEvasion.Evasion) buff).effectiveLevel;
		}
		return stealth;
	}

	@Override
	public void die(Object cause) {

		curAction = null;

		Ankh ankh = null;

		// look for ankhs in player inventory, prioritize ones which are
		// blessed.
		for (Item item : belongings) {
			if (item instanceof Ankh) {
				if (ankh == null || ((Ankh) item).isBlessed()) {
					ankh = (Ankh) item;
				}
			}
		}

		if (ankh != null && ankh.isBlessed()) {
			this.HP = HT;

			new Flare(8, 32).color(0xFFFF66, true).show(sprite, 2f);
			CellEmitter.get(this.pos)
					.start(Speck.factory(Speck.LIGHT), 0.2f, 3);

			ankh.detach(belongings.backpack);

			Sample.INSTANCE.play(Assets.SND_TELEPORT);
			GLog.w(Ankh.TXT_REVIVE);
			Statistics.ankhsUsed++;

			return;
		}

		Actor.fixTime();
		super.die(cause);

		if (ankh == null) {

			reallyDie(cause);

		} else {

			ankh.detach(belongings.backpack);
			Dungeon.deleteGame(Dungeon.hero.heroClass, false);
			GameScene.show(new WndResurrect(ankh, cause));

		}
	}

	public static void reallyDie(Object cause) {

		int length = Level.getLength();
		int[] map = Dungeon.level.map;
		boolean[] visited = Dungeon.level.visited;
		boolean[] discoverable = Level.discoverable;

		for (int i = 0; i < length; i++) {

			int terr = map[i];

			if (discoverable[i]) {

				visited[i] = true;
				if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {
					Level.set(i, Terrain.discover(terr));
					GameScene.updateMap(i);
				}
			}
		}

		Bones.leave();

		Dungeon.observe();

		Dungeon.hero.belongings.identify();

		int pos = Dungeon.hero.pos;

		ArrayList<Integer> passable = new ArrayList<Integer>();
		for (Integer ofs : Level.NEIGHBOURS8) {
			int cell = pos + ofs;
			if ((Level.passable[cell] || Level.avoid[cell])
					&& Dungeon.level.heaps.get(cell) == null) {
				passable.add(cell);
			}
		}
		Collections.shuffle(passable);

		ArrayList<Item> items = new ArrayList<Item>(
				Dungeon.hero.belongings.backpack.items);
		for (Integer cell : passable) {
			if (items.isEmpty()) {
				break;
			}

			Item item = Random.element(items);
			Dungeon.level.drop(item, cell).sprite.drop(pos);
			items.remove(item);
		}

		GameScene.gameOver();

		if (cause instanceof Hero.Doom) {
			((Hero.Doom) cause).onDeath();
		}

		Dungeon.deleteGame(Dungeon.hero.heroClass, true);
	}

	@Override
	public void move(int step) {
		super.move(step);

		if (!flying) {

			if (Level.water[pos]) {
				Sample.INSTANCE.play(Assets.SND_WATER, 1, 1,
						Random.Float(0.8f, 1.25f));
			} else {
				Sample.INSTANCE.play(Assets.SND_STEP);
			}
			Dungeon.level.press(pos, this);
		}

		//if (buff(LichenDrop.class) != null){Lichen.spawnAroundChance(pos);}

	}

	@Override
	public void onMotionComplete() {
		Dungeon.observe();
		search(false);

		super.onMotionComplete();
	}

	@Override
	public void onAttackComplete() {

		if(this.heroClass == HeroClass.WARRIOR){
			rageAttack();
		}else{
			if(this.heroClass != HeroClass.MAGE){
				energyAttack();
			}
		}

		AttackIndicator.target(enemy);

		attack(enemy);
		curAction = null;

		Invisibility.dispel();

		if(this.buff(Liquidation.class) != null) {
			Buff.detach(this, Liquidation.class);
		}

		super.onAttackComplete();
	}

	@Override
	public void onOperateComplete() {

		if (curAction instanceof HeroAction.Unlock) {

			if (theKey != null) {
				theKey.detach(belongings.backpack);
				theKey = null;
			}

			int doorCell = ((HeroAction.Unlock) curAction).dst;
			int door = Dungeon.level.map[doorCell];

			Level.set(doorCell, door == Terrain.LOCKED_DOOR ? Terrain.DOOR
					: Terrain.UNLOCKED_EXIT);
			GameScene.updateMap(doorCell);

		} else if (curAction instanceof HeroAction.OpenChest) {

			if (theKey != null) {
				theKey.detach(belongings.backpack);
				theKey = null;
			} else if (theKey == null && theSkeletonKey != null) {
				theSkeletonKey.detach(belongings.backpack);
				theSkeletonKey = null;
			}

			Heap heap = Dungeon.level.heaps
					.get(((HeroAction.OpenChest) curAction).dst);
			if (heap.type == Type.SKELETON || heap.type == Type.REMAINS) {
				Sample.INSTANCE.play(Assets.SND_BONES);
			}
			heap.open(this);
		}
		curAction = null;

		super.onOperateComplete();
	}

	public boolean search(boolean intentional) {

		boolean smthFound = false;

		int positive = 0;
		int negative = 0;

		int distance = 1 + positive + negative;

		float level = intentional ? (2 * awareness - awareness * awareness)
				: awareness;
		if (distance <= 0) {
			level /= 2 - distance;
			distance = 1;
		}

		int cx = pos % Level.getWidth();
		int cy = pos / Level.getWidth();
		int ax = cx - distance;
		if (ax < 0) {
			ax = 0;
		}
		int bx = cx + distance;
		if (bx >= Level.getWidth()) {
			bx = Level.getWidth() - 1;
		}
		int ay = cy - distance;
		if (ay < 0) {
			ay = 0;
		}
		int by = cy + distance;
		if (by >= Level.HEIGHT) {
			by = Level.HEIGHT - 1;
		}

		TalismanOfForesight.Foresight foresight = buff(TalismanOfForesight.Foresight.class);

		// cursed talisman of foresight makes unintentionally finding things
		// impossible.
		if (foresight != null && foresight.isCursed()) {
			level = -1;
		}

		for (int y = ay; y <= by; y++) {
			for (int x = ax, p = ax + y * Level.getWidth(); x <= bx; x++, p++) {

				if (Dungeon.visible[p]) {

					if (intentional) {
						sprite.parent.addToBack(new CheckedCell(p));
					}

					if (Level.secret[p]
							&& (intentional || Random.Float() < level)) {

						int oldValue = Dungeon.level.map[p];

						GameScene.discoverTile(p, oldValue);

						Level.set(p, Terrain.discover(oldValue));

						GameScene.updateMap(p);

						ScrollOfMagicMapping.discover(p);

						smthFound = true;

						if (foresight != null && !foresight.isCursed())
							foresight.charge();
					}
				}
			}
		}

		if (intentional) {
			sprite.showStatus(CharSprite.DEFAULT, TXT_SEARCH);
			sprite.operate(pos);
			if (foresight != null && foresight.isCursed()) {
				GLog.n("You can't concentrate, searching takes a while.");
				spendAndNext(TIME_TO_SEARCH * 3);
			} else {
				spendAndNext(TIME_TO_SEARCH);
			}

		}

		if (smthFound) {
			GLog.w(TXT_NOTICED_SMTH);
			Sample.INSTANCE.play(Assets.SND_SECRET);
			interrupt();
		}

		return smthFound;
	}

	public void resurrect(int resetLevel) {

		HP = HT;
		Dungeon.gold = 0;
		exp = 0;

		belongings.resurrect(resetLevel);

		live();
	}

	@Override
	public HashSet<Class<?>> resistances() {
		RingOfElements.Resistance r = buff(RingOfElements.Resistance.class);
		return r == null ? super.resistances() : r.resistances();
	}

	@Override
	public HashSet<Class<?>> immunities() {
		HashSet<Class<?>> immunities = new HashSet<Class<?>>();
		for (Buff buff : buffs()) {
			for (Class<?> immunity : buff.immunities)
				immunities.add(immunity);
		}
		return immunities;
	}

	@Override
	public void next() {
		super.next();
	}

	public int getAttackSkill(){
		return attackSkill;
	}
	public int getDefenseSkill(){
		return defenseSkill;
	}
	public void setAttackSkill(int acc){
		this.attackSkill = acc;
	}
	public void setDefenseSkill(int def){
		this.defenseSkill = def;
	}
	public static interface Doom {
		public void onDeath();
	}
}
