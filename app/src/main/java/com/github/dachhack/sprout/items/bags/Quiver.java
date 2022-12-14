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
package com.github.dachhack.sprout.items.bags;

import com.github.dachhack.sprout.items.Ammo.Arrow;
import com.github.dachhack.sprout.items.Ammo.SilverArrow;
import com.github.dachhack.sprout.items.Item;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;

public class Quiver extends Bag {

	{
		name = "quiver";
		//image = ItemSpriteSheet.QUIVER;
		image = ItemSpriteSheet.BONE;

		size = 10;
	}

	@Override
	public boolean grab(Item item) {
		return item instanceof Arrow || item instanceof SilverArrow;
	}

	@Override
	public int price() {
		return 50;
	}

	@Override
	public String info() {
		return "A sturdy open-ended pack that holds many arrows at the ready.";
	}
}
