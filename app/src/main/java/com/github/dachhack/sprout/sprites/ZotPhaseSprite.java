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
package com.github.dachhack.sprout.sprites;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.actors.mobs.Shaman;
import com.github.dachhack.sprout.actors.mobs.ZotPhase;
import com.github.dachhack.sprout.effects.Lightning;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.MovieClip.Animation;

public class ZotPhaseSprite extends MobSprite {

	private int[] points = new int[2];

	public ZotPhaseSprite() {
		super();

		texture(Assets.ZOTPHASE);

		TextureFilm frames = new TextureFilm(texture, 18, 18);

		idle = new Animation(2, true);
		idle.frames(frames, 0, 0, 0, 1, 0);

		run = new Animation(8, false);
		run.frames(frames, 0, 1, 2);

		attack = new Animation(8, false);
		attack.frames(frames, 0, 2, 2);
		
		zap = new Animation(8, false);
		zap.frames(frames, 2, 3, 4);

		die = new Animation(8, false);
		die.frames(frames, 0, 5, 6, 7, 8, 9, 8);

		play(idle);
	}

	@Override
	public void zap(int pos) {

		points[0] = ch.pos;
		points[1] = pos;
		parent.add(new Lightning(points, 2, (ZotPhase) ch));

		turnTo(ch.pos, pos);
		play(zap);
	}
}
