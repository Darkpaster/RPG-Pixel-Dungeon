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
package com.github.dachhack.sprout.effects;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.DungeonTilemap;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.levels.Level;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.Image;

public class DoubleSlash extends Image {

	private static final float TIME_TO_FADE = 0.8f;

	private float time;

	public DoubleSlash() {
		super(Effects.get(Effects.Type.LIQUIDATION));
		origin.set(width / 2, height / 2);
	}
//	public Wound(Effects.Type type) {
//		super(Effects.get(type));
//		origin.set(width / 2, height / 2);
//	}

	public void reset(int p) {
		revive();

		x = (p % Level.getWidth()) * DungeonTilemap.SIZE
				+ (DungeonTilemap.SIZE - width) / 2;
		y = (p / Level.getWidth()) * DungeonTilemap.SIZE
				+ (DungeonTilemap.SIZE - height) / 2;

		time = TIME_TO_FADE;
	}

	@Override
	public void update() {
		super.update();

		if ((time -= Game.elapsed) <= 0) {
			kill();
		} else {
			float p = time / TIME_TO_FADE;
			alpha(p);
			scale.x = 1 + p;
		}
	}

	public static void hit(Char ch) {
		hit(ch, 0);
	}

	public static void hit(Char ch, float angle) {
		if (ch.sprite.parent != null) {
			DoubleSlash w = (DoubleSlash) ch.sprite.parent.recycle(DoubleSlash.class);
			ch.sprite.parent.bringToFront(w);
			w.reset(ch.pos);
			w.angle = angle;
		}
	}

	public static void hit(int pos) {
		hit(pos, 0);
	}

	public static void hit(int pos, float angle) {
		Group parent = Dungeon.hero.sprite.parent;
		DoubleSlash w = (DoubleSlash) parent.recycle(DoubleSlash.class);
		parent.bringToFront(w);
		w.reset(pos);
		w.angle = angle;
	}
}