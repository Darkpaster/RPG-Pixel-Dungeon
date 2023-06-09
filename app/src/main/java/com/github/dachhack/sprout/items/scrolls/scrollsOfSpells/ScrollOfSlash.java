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
package com.github.dachhack.sprout.items.scrolls.scrollsOfSpells;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.actors.buffs.Amok;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.buffs.Invisibility;
import com.github.dachhack.sprout.actors.mobs.Mimic;
import com.github.dachhack.sprout.actors.mobs.Mob;
import com.github.dachhack.sprout.effects.Speck;
import com.github.dachhack.sprout.items.Heap;
import com.github.dachhack.sprout.items.scrolls.Scroll;
import com.github.dachhack.sprout.items.spells.SpellBook;
import com.github.dachhack.sprout.levels.Level;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class ScrollOfSlash extends Scroll {

	{
		name = "Scroll of Slash";
		consumedValue = 5;
		//image = ItemSpriteSheet.BOOKOFDEAD;
		setKnown();
		//mp_cost = 2 * Dungeon.hero.getMagicLevel() + 3;
	}

	@Override
	protected void updateCost() {

	}

	@Override
	protected void doRead() {
		Dungeon.hero.spellbook.learnSpell(SpellBook.spellList.SLASH);
		Sample.INSTANCE.play(Assets.SND_READ);
		Invisibility.dispel();
		curUser.spendAndNext(TIME_TO_READ);
	}
	@Override
	public String desc() {
		return "Allows to learn new skill.";
	}
}
