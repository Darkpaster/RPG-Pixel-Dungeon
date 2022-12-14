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
import com.watabou.noosa.TextureFilm;

public class VelociroosterSprite extends MobSprite {

	public VelociroosterSprite() {
		super();

		 texture( Assets.VELOCIROOSTER );

	        TextureFilm frames = new TextureFilm( texture, 16, 16 );

	        idle = new Animation( 2, true );
	        idle.frames( frames, 0, 0, 1 );

	        run = new Animation( 10, true );
	        run.frames( frames, 3, 4, 5, 4 );

	        attack = new Animation( 14, false );
	        attack.frames( frames, 1, 2, 1 );

	        die = new Animation( 10, false );
	        die.frames( frames, 1, 6, 7 );

	        play( idle );
	}

	@Override
	public int blood() {
		return 0xFFFFEA80;
	}
}
