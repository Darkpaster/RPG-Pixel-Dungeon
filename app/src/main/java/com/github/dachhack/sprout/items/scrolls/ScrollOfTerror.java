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
package com.github.dachhack.sprout.items.scrolls;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.buffs.Invisibility;
import com.github.dachhack.sprout.actors.buffs.Terror;
import com.github.dachhack.sprout.actors.mobs.Mob;
import com.github.dachhack.sprout.effects.Flare;
import com.github.dachhack.sprout.levels.Level;
import com.github.dachhack.sprout.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class ScrollOfTerror extends Scroll {

	{
		name = "Scroll of Terror";
		consumedValue = 5;
		mp_cost = 2 * Dungeon.hero.getMagicLevel() + 3;
	}

	@Override
	protected void doRead() {

		new Flare(5, 32).color(0xFF0000, true).show(curUser.sprite, 2f);
		Sample.INSTANCE.play(Assets.SND_READ);
		Invisibility.dispel();

		int count = 0;
		Mob affected = null;
		for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
			if (Level.fieldOfView[mob.pos]) {
				Buff.affect(mob, Terror.class, Terror.DURATION + Dungeon.hero.getMagicLevel() / 5).object = curUser
						.id();

				count++;
				affected = mob;
			}
		}

		switch (count) {
		case 0:
			GLog.i("The scroll emits a brilliant flash of red light");
			break;
		case 1:
			GLog.i("The scroll emits a brilliant flash of red light and the "
					+ affected.name + " flees!");
			break;
		default:
			GLog.i("The scroll emits a brilliant flash of red light and the monsters flee!");
		}
		setKnown();

		curUser.spendAndNext(TIME_TO_READ);
	}

	@Override
	public String desc() {
		updateCost();
		return "A flash of red light will overwhelm all creatures in your field of view with terror, "
				+ "and they will turn and flee. Attacking a fleeing enemy will dispel the effect. \n\n" + TXT_MAGIC_INFO + currentCost();
	}
	@Override
	protected void updateCost() {
		mp_cost = 2 * Dungeon.hero.getMagicLevel() + 3;
	}
	@Override
	public int price() {
		return isKnown() ? 50 * quantity : super.price();
	}
}
