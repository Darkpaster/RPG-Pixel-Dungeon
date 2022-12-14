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
package com.github.dachhack.sprout.items.weapon.melee;

import com.github.dachhack.sprout.sprites.ItemSpriteSheet;

public class AssassinsKnife extends MeleeWeapon {

	{
		name = "Assassin's Knife";
		image = ItemSpriteSheet.ASSASSINSKNIFE;
	}

	public AssassinsKnife() {
		super(2, 10, 1f, .75f, 1, 12);
	}

	@Override
	public String desc() {
		return "Deadly quick blades designed to strike vital organs";
	}
}
