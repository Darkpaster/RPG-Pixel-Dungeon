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
package com.github.dachhack.sprout.windows;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.actors.hero.Hero;
import com.github.dachhack.sprout.actors.hero.HeroClass;
import com.github.dachhack.sprout.scenes.PixelScene;
import com.github.dachhack.sprout.sprites.CharSprite;
import com.github.dachhack.sprout.sprites.HeroSprite;
import com.github.dachhack.sprout.ui.RedButton;
import com.github.dachhack.sprout.ui.Window;
import com.github.dachhack.sprout.utils.GLog;
import com.github.dachhack.sprout.utils.Utils;
import com.watabou.noosa.BitmapTextMultiline;
import com.watabou.utils.Random;

import java.util.Locale;

public class WndLevelUp extends Window {

	private static final String TXT_MESSAGE_EVAP = "The Life Drop evaporates releasing it's power!";
	private static final String TXT_MESSAGE = "Which attribute do you want to increase?";
	private static final String TXT_SPEED = "Speed + %d";
	private static final String TXT_STR = "Strength + %d";
	private static final String TXT_MAGIC = "Magic + %d";
	private static final String TXT_TITLE = "Level %d %s";

	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 18;
	private static final float GAP = 2;

	private static float y = 0;

	public WndLevelUp(Hero hero) {

		super();

		IconTitle title = new IconTitle();
		title.icon(HeroSprite.avatar(hero.heroClass, hero.tier()));
		title.label(Utils.format(TXT_TITLE, hero.lvl, hero.className())
				.toUpperCase(Locale.ENGLISH), 9);
		title.color(Window.SHPX_COLOR);
		title.setRect(0, 0, WIDTH, 0);
		add(title);

		final int strpts = GetStr(hero);
		final int magpts = GetMag(hero);
		final int spdpts = GetSpd(hero);
		final int DRpts = getDR(hero);

		BitmapTextMultiline message = PixelScene.createMultiline(TXT_MESSAGE, 6);
		message.maxWidth = WIDTH;
		message.measure();
		message.y = title.bottom() + GAP;
		add(message);

		RedButton btnstr = new RedButton(Utils.format(TXT_STR, strpts).toUpperCase(Locale.ENGLISH)) {
			@Override
			protected void onClick() {
				Dungeon.hero.levelup=false;
				Dungeon.hero.STR+=strpts;
				Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE, Utils.format("+ %d str", strpts));
				GLog.p("Newfound strength surges through your body.");
				hide();
			}
		};
		btnstr.setRect(0, message.y + message.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnstr);
		y = btnstr.bottom() + GAP;

		RedButton btnmagic = new RedButton(Utils.capitalize(Utils.format(TXT_MAGIC, magpts).toUpperCase(Locale.ENGLISH))) {
			@Override
			protected void onClick() {
				Dungeon.hero.levelup=false;
				Dungeon.hero.MT += 2;
				Dungeon.hero.magicLevel+=magpts;
				Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE, Utils.format("+ %d magic", magpts));
				GLog.p("You feel the surge of arcane magics in your body.");
				hide();
			}
		};
		btnmagic.setRect(0, y, btnstr.width(), BTN_HEIGHT);
		if(Dungeon.hero.heroClass == HeroClass.MAGE){
			add(btnmagic);
			y = btnmagic.bottom() + GAP;
		}

		RedButton btnspeed = new RedButton(Utils.capitalize(Utils.format(TXT_SPEED, spdpts).toUpperCase(Locale.ENGLISH))) {
			@Override
			protected void onClick() {
				Dungeon.hero.levelup=false;
				Dungeon.hero.setSpeed(Dungeon.hero.getSpeed() + spdpts);
				Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE, Utils.format("+ %d speed", spdpts));
				GLog.p("You feel your body moving faster.");
				hide();
			}
		};
		btnspeed.setRect(0, y, btnmagic.width(), BTN_HEIGHT);
		if(Dungeon.hero.lvl % 15 == 0 || Dungeon.hero.heroClass == HeroClass.ROGUE && Dungeon.hero.lvl % 10 == 0){
			add(btnspeed);
			y = btnspeed.bottom() + GAP;
		}

		RedButton btnDR = new RedButton(Utils.capitalize(Utils.format(TXT_SPEED, DRpts).toUpperCase(Locale.ENGLISH))) {
			@Override
			protected void onClick() {
				Dungeon.hero.levelup=false;
				Dungeon.hero.DR += DRpts;
				Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE, Utils.format("+ %d dmg reduction", DRpts));
				GLog.p("You feel your body stronger than before.");
				hide();
			}
		};
		btnDR.setRect(0, y, WIDTH, BTN_HEIGHT);
		//if(Dungeon.hero.lvl % 15 == 0 || Dungeon.hero.heroClass == HeroClass.ROGUE && Dungeon.hero.lvl % 10 == 0){
			add(btnDR);
		//}

		resize(WIDTH, (int) btnspeed.bottom());
	}

	private int GetStr(Hero hero){
		int points = 0;
//
		if(hero.heroClass == HeroClass.WARRIOR){ points+=Random.Roll(1,2);}else{
			points = 1;
		}
//		if(hero.heroClass== HeroClass.ROGUE){ points+=Random.Roll(1,2);}
//		if(hero.heroClass== HeroClass.MAGE){ points+=Random.Roll(1,1);}
//		if(hero.heroClass== HeroClass.HUNTRESS){ points+=Random.Roll(1,2);}

		return points;

	}

	private int GetMag(Hero hero){
//		int points=0;
//
//		if(hero.heroClass== HeroClass.WARRIOR){ points+=Random.Roll(1,1);}
//		if(hero.heroClass== HeroClass.ROGUE){ points+=Random.Roll(1,2);}
//		if(hero.heroClass== HeroClass.MAGE){ points+=Random.Roll(1,3);}
//		if(hero.heroClass== HeroClass.HUNTRESS){ points+=Random.Roll(1,2);}

		return 1;

	}

	private int getDR(Hero hero){
		int points=0;
//
		if(hero.heroClass== HeroClass.WARRIOR){ points+=Random.Roll(1,2);}else {
			points = 1;
		}
//		if(hero.heroClass== HeroClass.ROGUE){ points+=Random.Roll(1,2);}
//		if(hero.heroClass== HeroClass.MAGE){ points+=Random.Roll(1,3);}
//		if(hero.heroClass== HeroClass.HUNTRESS){ points+=Random.Roll(1,2);}

		return points;

	}

	private int GetSpd(Hero hero){
		//int points = 1;

//		if(hero.heroClass== HeroClass.WARRIOR){ points+=Random.Roll(1,2);}
//		if(hero.heroClass== HeroClass.ROGUE){ points+=Random.Roll(1,3);}
//		if(hero.heroClass== HeroClass.MAGE){ points+=Random.Roll(1,2);}
//		if(hero.heroClass== HeroClass.HUNTRESS){ points+=Random.Roll(1,2);}


		return 1;

	}


}
