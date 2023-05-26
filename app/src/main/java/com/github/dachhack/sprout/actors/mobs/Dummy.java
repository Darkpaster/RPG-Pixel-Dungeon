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

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.effects.Speck;
import com.github.dachhack.sprout.items.Item;
import com.github.dachhack.sprout.items.food.Meat;
import com.github.dachhack.sprout.items.potions.PotionOfMending;
import com.github.dachhack.sprout.items.weapon.enchantments.Leech;
import com.github.dachhack.sprout.sprites.BatSprite;
import com.github.dachhack.sprout.sprites.ShellSprite;
import com.watabou.utils.Random;

import java.util.HashSet;

public class Dummy extends Mob {

	{
		name = "Dummy";
		spriteClass = ShellSprite.class;

		state = PASSIVE;

		HP = HT = 1000000;
		defenseSkill = 0;
		speed = 0f;

		EXP = 9999;
		maxLvl = 1000;

//		loot = new PotionOfMending();
//		lootChance = 0.1667f; // by default, see die()
//
//		lootOther = new Meat();
//		lootChanceOther = 0.5f; // by default, see die()
	}

	@Override
	public int damageRoll() {
		return 0;
	}

	@Override
	protected float attackDelay() {
		return 100000f;
	}

	@Override
	public int dr() {
		return 0;
	}

	@Override
	public String defenseVerb() {
		return "evaded";
	}

	@Override
	public String description() {
		return "A simple dummy.";
	}
}
