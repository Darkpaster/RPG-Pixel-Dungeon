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

import com.github.dachhack.sprout.Badges;
import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.effects.Identification;
import com.github.dachhack.sprout.effects.particles.ShadowParticle;
import com.github.dachhack.sprout.items.Item;
import com.github.dachhack.sprout.utils.GLog;
import com.github.dachhack.sprout.windows.WndBag;
import com.watabou.utils.Random;

public class ScrollOfIdentify extends InventoryScroll {

	{
		name = "Scroll of Identify";
		inventoryTitle = "Select an item to identify";
		mode = WndBag.Mode.UNIDENTIFED;
		consumedValue = 10;
		mp_cost = 2 * Dungeon.hero.magicLevel + 3;
		bones = true;
	}

	@Override
	protected void onItemSelected(Item item) {

		curUser.sprite.parent.add(new Identification(curUser.sprite.center()
				.offset(0, -16)));

		item.identify();
		GLog.i("It is " + item);
		if(item.cursed && Random.Int(100) < Dungeon.hero.magicLevel){
			item.uncurse();
			Dungeon.hero.sprite.emitter().start(ShadowParticle.UP, 0.05f, 10);
			GLog.p("Your understanding of magic allowed you to read this scroll with additional effect.");
		}

		Badges.validateItemLevelAquired(item);
	}

	@Override
	public String desc() {
		updateCost();
		return "Permanently reveals all of the secrets of a single item. \n\n" + TXT_MAGIC_INFO + currentCost();
	}

	@Override
	protected void updateCost() {
		mp_cost = 2 * Dungeon.hero.magicLevel + 3;
	}

	@Override
	public int price() {
		return isKnown() ? 30 * quantity : super.price();
	}
}
