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
package com.github.dachhack.sprout.actors.mobs;

import java.util.HashSet;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.buffs.Cripple;
import com.github.dachhack.sprout.actors.buffs.Light;
import com.github.dachhack.sprout.actors.buffs.Poison;
import com.github.dachhack.sprout.items.Item;
import com.github.dachhack.sprout.items.food.MysteryMeat;
import com.github.dachhack.sprout.items.potions.PotionOfHealing;
import com.github.dachhack.sprout.items.weapon.enchantments.Leech;
import com.github.dachhack.sprout.levels.Level;
import com.github.dachhack.sprout.mechanics.Ballistica;
import com.github.dachhack.sprout.sprites.ScorpioSprite;
import com.watabou.utils.Random;

public class Scorpio extends Mob {

	{
		name = "scorpio";
		spriteClass = ScorpioSprite.class;

		HP = HT = 95+(adj(0)*Random.NormalIntRange(1, 3));
		defenseSkill = 24+adj(1);
		viewDistance = Light.DISTANCE;

		EXP = 14;
		maxLvl = 25;

		loot = new PotionOfHealing();
		lootChance = 0.2f;
		
		lootOther = new MysteryMeat();
		lootChanceOther = 0.333f; // by default, see die()
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(20, 52+adj(0));
	}

	@Override
	public int attackSkill(Char target) {
		return 36+adj(1);
	}

	@Override
	public int dr() {
		return 16+adj(1);
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return !Level.adjacent(pos, enemy.pos)
				&& Ballistica.cast(pos, enemy.pos, false, true) == enemy.pos;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(2) == 0) {
			Buff.prolong(enemy, Cripple.class, Cripple.DURATION);
		}

		return damage;
	}

	@Override
	protected boolean getCloser(int target) {
		if (state == HUNTING) {
			return enemySeen && getFurther(target);
		} else {
			return super.getCloser(target);
		}
	}

	@Override
	protected Item createLoot() {
		// 5/count+5 total chance of getting healing, failing the 2nd roll drops
		// mystery meat instead.
		if (Random.Int(5 + Dungeon.limitedDrops.scorpioHP.count) <= 4) {
			Dungeon.limitedDrops.scorpioHP.count++;
			return (Item) loot;
		} else {
			return new MysteryMeat();
		}
	}

	@Override
	public String description() {
		return "These huge arachnid-like demonic creatures avoid close combat by all means, "
				+ "firing crippling serrated spikes from long distances.";
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(Leech.class);
		RESISTANCES.add(Poison.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}
}
