package com.github.dachhack.sprout.sprites;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.items.weapon.missiles.Dart;
import com.github.dachhack.sprout.levels.Level;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;

/**
 * Created by Evan on 09/10/2014.
 */
public class GnollArcherSprite extends MobSprite {

	private Animation cast;

	public GnollArcherSprite() {
		super();

		texture(Assets.GNOLLARCHER);

		TextureFilm frames = new TextureFilm(texture, 12, 15);

		idle = new Animation(2, true);
		idle.frames(frames, 0, 0, 0, 1, 0, 0, 1, 1);

		run = new Animation(12, true);
		run.frames(frames, 4, 5, 6, 7);

		attack = new Animation(12, false);
		attack.frames(frames, 2, 3, 0);
		
		cast = attack.clone();

		die = new Animation(12, false);
		die.frames(frames, 8, 9, 10);

		play(idle);
	}

	@Override
	public void attack(int cell) {
		if (!Level.adjacent(cell, ch.pos)) {

			((MissileSprite) parent.recycle(MissileSprite.class)).reset(ch.pos,
					cell, new Dart(), new Callback() {
						@Override
						public void call() {
							ch.onAttackComplete();
						}
					});

			play(cast);
			turnTo(ch.pos, cell);

		} else {

			super.attack(cell);

		}
	}
}
