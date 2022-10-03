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
package com.github.dachhack.sprout.actors.buffs;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.actors.hero.Hero;
import com.github.dachhack.sprout.items.artifacts.ChaliceOfBlood;

public class Regeneration extends Buff {

	private static final float REGENERATION_DELAY = 10;

	@Override
	public boolean act() {
		Hero hero = Dungeon.hero;
		if (target.isAlive()) {

			if (target.HP < target.HT && !((Hero) target).isStarving()) {
				if(target == hero){

					target.HP += hero.regeneration_power;
				}else{
					target.HP += 1;
				}
			}

			ChaliceOfBlood.chaliceRegen regenBuff = Dungeon.hero
					.buff(ChaliceOfBlood.chaliceRegen.class);

			if (regenBuff != null) {
				if (regenBuff.isCursed()) {
						spend(Math.max(hero.regeneration_delay * 1.5f, 0.50f));
				} else {
					spend(Math.max(hero.regeneration_delay - regenBuff.level(), 0.5f));
					//spend(hero.regeneration_delay - regenBuff.level());
				}
			}else
				if(target == hero){
					spend(Math.max(hero.regeneration_delay, 0.50f));
				}else{
					spend(REGENERATION_DELAY);
				}

		} else {

			diactivate();

		}

		return true;
	}
}
