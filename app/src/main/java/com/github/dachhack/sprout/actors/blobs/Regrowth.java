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
package com.github.dachhack.sprout.actors.blobs;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.buffs.Roots;
import com.github.dachhack.sprout.effects.BlobEmitter;
import com.github.dachhack.sprout.effects.particles.LeafParticle;
import com.github.dachhack.sprout.levels.Level;
import com.github.dachhack.sprout.levels.Terrain;
import com.github.dachhack.sprout.scenes.GameScene;

public class Regrowth extends Blob {

	@Override
	protected void evolve() {
		super.evolve();

		if (volume > 0) {

			boolean mapUpdated = false;

			for (int i = 0; i < LENGTH; i++) {
				if (off[i] > 0) {
					int c = Dungeon.level.map[i];
					if (c == Terrain.EMPTY || c == Terrain.EMBERS
							|| c == Terrain.EMPTY_DECO) {

						Level.set(i, cur[i] > 9 ? Terrain.HIGH_GRASS
								: Terrain.GRASS);
						mapUpdated = true;

					} else if (c == Terrain.GRASS && cur[i] > 9) {

						Level.set(i, Terrain.HIGH_GRASS);
						mapUpdated = true;

					}

					Char ch = Actor.findChar(i);
					if (ch != null) {
						Buff.prolong(ch, Roots.class, TICK);
					}
				}
			}

			if (mapUpdated) {
				GameScene.updateMap();
				Dungeon.observe();
			}
		}
	}

	@Override
	public void use(BlobEmitter emitter) {
		super.use(emitter);

		emitter.start(LeafParticle.LEVEL_SPECIFIC, 0.2f, 0);
	}
}
