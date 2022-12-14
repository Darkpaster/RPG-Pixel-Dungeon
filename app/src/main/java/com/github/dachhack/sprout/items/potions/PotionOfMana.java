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

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.actors.hero.Hero;
import com.github.dachhack.sprout.effects.Speck;
import com.github.dachhack.sprout.utils.GLog;

public class PotionOfMana extends Potion {

	{
		name = "Potion of Mana";

		bones = true;
	}

	@Override
	public void apply(Hero hero) {
		setKnown();
		mana(Dungeon.hero);
		GLog.p("Your mana refreshes.");
	}

	public static void mana(Hero hero) {

		hero.MP = hero.MP+Math.min(hero.MT, hero.MT-hero.MP);

		hero.sprite.emitter().start(Speck.factory(Speck.FORGE), 0.4f, 4); //OSMOSE
	}

	@Override
	public String desc() {
		return "An elixir that will grant the user fresh mana.";
	}

	@Override
	public int price() {
		return isKnown() ? 20 * quantity : super.price();
	}
}
