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
package com.github.dachhack.sprout.items.weapon.enchantments;

import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.effects.Speck;
import com.github.dachhack.sprout.items.weapon.Weapon;
import com.github.dachhack.sprout.items.weapon.melee.relic.RelicMeleeWeapon;
import com.github.dachhack.sprout.sprites.CharSprite;
import com.watabou.utils.Random;

public class Osmose extends Weapon.Enchantment {

	private static final String TXT_VAMPIRIC = "Osmose %s";

	@Override
	public boolean proc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage) {
		return false;
	}
	
	@Override
	public boolean proc(Weapon weapon, Char attacker, Char defender, int damage) {

		int level = Math.max(0, weapon.level);

		// lvl 0 - 33%
		// lvl 1 - 43%
		// lvl 2 - 50%
		int maxValue = damage * (level + 2) / (level + 6);
		int effValue = Math.min(Random.IntRange(0, maxValue), attacker.MT
				- attacker.MP);

		if (effValue > 0) {

			attacker.MP += effValue;
			attacker.sprite.emitter().start(Speck.factory(Speck.BONE), 0.4f, //OSMOSE
					1);
			attacker.sprite.showStatus(CharSprite.POSITIVE,
					Integer.toString(effValue));

			return true;

		} else {
			return false;
		}
	}

	@Override
	public String name(String weaponName) {
		return String.format(TXT_VAMPIRIC, weaponName);
	}

}
