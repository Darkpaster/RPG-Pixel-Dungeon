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
package com.github.dachhack.sprout.items.potions;

import com.github.dachhack.sprout.Badges;
import com.github.dachhack.sprout.actors.hero.Hero;
import com.github.dachhack.sprout.sprites.CharSprite;
import com.github.dachhack.sprout.utils.GLog;

public class DragonsBlood extends Potion {

	{
		name = "Dragon's blood";

		bones = true;
	}

	@Override
	public void apply(Hero hero) {
		setKnown();
		hero.STR += 3;
		hero.HT += 25;
		hero.HP += 25;
		hero.DR += 2;
		hero.bonusDamage += 10;
		hero.sprite.showStatus(CharSprite.POSITIVE, "+5 str, +25 ht, +2 dr, +10 dmg");
		GLog.p("Newfound strength surges through your body.");

		Badges.validateStrengthAttained();
	}

	@Override
	public String desc() {
		return "This powerful blood will course through your body, permanently "
				+ "increasing your strength by 3 points, health by 25 points, defense by 2 points, and phys damage by 10 points.";
	}

	@Override
	public int price() {
		return isKnown() ? 10000 * quantity : super.price();
	}
}
