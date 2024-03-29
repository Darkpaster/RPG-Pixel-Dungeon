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
import com.github.dachhack.sprout.actors.buffs.Invisibility;
import com.github.dachhack.sprout.actors.hero.Hero;
import com.github.dachhack.sprout.effects.SpellSprite;
import com.github.dachhack.sprout.effects.particles.EnergyParticle;
import com.github.dachhack.sprout.items.Generator;
import com.github.dachhack.sprout.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import static com.github.dachhack.sprout.items.Generator.Category.WAND;

public class ScrollOfRecharging extends Scroll {

	{
		name = "Scroll of Recharging";
		consumedValue = 10;
		mp_cost = 4 * Dungeon.hero.getMagicLevel() + 3;
	}

	@Override
	protected void doRead() {

		int count = curUser.belongings.charge(true);
		charge(curUser);

		Sample.INSTANCE.play(Assets.SND_READ);
		Invisibility.dispel();

		if (count > 0) {
			GLog.i("a surge of energy courses through your pack, recharging your wand"
					+ (count > 1 ? "s" : ""));
			SpellSprite.show(curUser, SpellSprite.CHARGE);
		} else {
			GLog.i("a surge of energy courses through your pack, but nothing happens");
		}
		setKnown();

//		if(curUser.magicLevel > 30){
//			int chance = Math.max(1000 / curUser.magicLevel, 2);
//			if(Random.Int(chance) == 0){
//				Dungeon.level.drop(Generator.random(WAND), curUser.pos);
//			}
//		}

		curUser.spendAndNext(TIME_TO_READ);
	}

	@Override
	public String desc() {
		updateCost();
		return "The raw magical power bound up in this parchment will, when released, "
				+ "recharge all of the reader's wands to full power. \n\n" + TXT_MAGIC_INFO + currentCost();
	}
	@Override
	protected void updateCost() {
		mp_cost = 4 * Dungeon.hero.getMagicLevel() + 3;
	}
	public static void charge(Hero hero) {
		hero.sprite.centerEmitter().burst(EnergyParticle.FACTORY, 15);
	}

	@Override
	public int price() {
		return isKnown() ? 40 * quantity : super.price();
	}
}
