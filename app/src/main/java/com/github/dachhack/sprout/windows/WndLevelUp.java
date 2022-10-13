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
import com.github.dachhack.sprout.items.Spellbook;
import com.github.dachhack.sprout.items.spells.SpellBook;
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
	private static final String TXT_MESSAGE_TALENTS = "Which talent do you want to learn?";
	private static final String TXT_SPEED = "Speed + %d";
	private static final String TXT_STR = "Strength + %d";
	private static final String TXT_MAGIC = "Magic + %d";
	private static final String TXT_TITLE = "Level %d %s";
	private static final String TXT_DR = "DR + %d";

	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 18;
	private static final float GAP = 2;

	private static float y = 0;

	public WndLevelUp(Hero hero, int lvl) {

		super();

		IconTitle title = new IconTitle();
		title.icon(HeroSprite.avatar(hero.heroClass, hero.tier()));
		title.label(Utils.format(TXT_TITLE, hero.lvl, hero.className())
				.toUpperCase(Locale.ENGLISH), 9);
		title.color(Window.SHPX_COLOR);
		title.setRect(0, 0, WIDTH, 0);
		add(title);

		switch (lvl){
			case 10: talents(title, lvl); break;
//			case 20: talents(title, lvl); break;
//			case 30: talents(title, lvl); break;
//			case 40: talents(title, lvl); break;
//			case 50: talents(title, lvl); break;
//			case 60: talents(title, lvl); break;
//			case 70: talents(title, lvl); break;
//			case 80: talents(title, lvl); break;
//			case 90: talents(title, lvl); break;
//			case 100: talents(title, lvl); break;
			default: stats(title, lvl);
		}
		//stats(title, lvl);
//		if(lvl == 10){
//			talents(title, lvl);
//		}else{
//			stats(title, lvl);
//		}
	}

	private void talents(IconTitle title, int lvl){
		BitmapTextMultiline message = PixelScene.createMultiline(TXT_MESSAGE_TALENTS, 6);
		message.maxWidth = WIDTH;
		message.measure();
		message.y = title.bottom() + GAP;
		add(message);



		switch (lvl){
			case 10:
				Dungeon.hero.spellbook.learnSpell(SpellBook.spellList.MORTAL_STRIKE);
				switch (Dungeon.hero.heroClass){
					case WARRIOR:
						System.out.println("WARRIOR");
						break;

					case ROGUE:
						System.out.println("ROGUE");

						RedButton btn = new RedButton("First talent".toUpperCase(Locale.ENGLISH)) {
							@Override
							protected void onClick() {
								Dungeon.hero.levelup = false;
								Dungeon.hero.invisRegen = true;
								//Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE, Utils.format("+ %d str", getStr(lvl)));
								//GLog.p("Newfound strength surges through your body.");
								hide();
							}
						};
						y = message.y + message.height() + GAP;
						btn.setRect(0, y, WIDTH,
								BTN_HEIGHT);
						add(btn);
						y = btn.bottom() + GAP;


						RedButton btn2 = new RedButton("Second talent".toUpperCase(Locale.ENGLISH)) {
							@Override
							protected void onClick() {
								Dungeon.hero.levelup = false;
								//Dungeon.hero.MT += 2;
								Dungeon.hero.invisSpeed = true;
								//Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE, Utils.format("+ %d magic", getMag(lvl)));
								//GLog.p("You feel the surge of arcane magics in your body.");
								hide();
							}
						};
						btn2.setRect(0, y, WIDTH, BTN_HEIGHT);
						add(btn2);
						y = btn2.bottom() + GAP;
						break;

					case MAGE:
						System.out.println("MAGE");

						break;

					case HUNTRESS:
						System.out.println("HUNTRESS");

						break;
				}

				break;
			case 20:



				break;
			case 30:


				break;
			case 40:


				break;
			case 50:



				break;
			case 60:



				break;
			case 70:



				break;
			case 80:



				break;
			case 90:



				break;
			case 100:



				break;
			default: stats(title, lvl);
		}

		resize(WIDTH, (int) y);
	}


	private void stats(IconTitle title, int lvl){
		BitmapTextMultiline message = PixelScene.createMultiline(TXT_MESSAGE, 6);
		message.maxWidth = WIDTH;
		message.measure();
		message.y = title.bottom() + GAP;
		add(message);



		//if(lvl % 3)
		RedButton btnstr = new RedButton(Utils.format(TXT_STR, getStr(lvl)).toUpperCase(Locale.ENGLISH)) {
			@Override
			protected void onClick() {
				Dungeon.hero.levelup = false;
				Dungeon.hero.STR += getStr(lvl);
				Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE, Utils.format("+ %d str", getStr(lvl)));
				GLog.p("Newfound strength surges through your body.");
				hide();
			}
		};
		if(lvl % 1 == 0){
		y = message.y + message.height() + GAP;
		btnstr.setRect(0, y, WIDTH,
				BTN_HEIGHT);
			add(btnstr);
			y = btnstr.bottom() + GAP;
		}

		RedButton btnmagic = new RedButton(Utils.capitalize(Utils.format(TXT_MAGIC, getMag(lvl)).toUpperCase(Locale.ENGLISH))) {
			@Override
			protected void onClick() {
				Dungeon.hero.levelup = false;
				//Dungeon.hero.MT += 2;
				Dungeon.hero.magicLevel += getMag(lvl);
				Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE, Utils.format("+ %d magic", getMag(lvl)));
				GLog.p("You feel the surge of arcane magics in your body.");
				hide();
			}
		};
		if(lvl % 2 == 0){
		btnmagic.setRect(0, y, WIDTH, BTN_HEIGHT);
			add(btnmagic);
			y = btnmagic.bottom() + GAP;
		}

		RedButton btnspeed = new RedButton(Utils.capitalize(Utils.format(TXT_SPEED, getSpd(lvl)).toUpperCase(Locale.ENGLISH)) + "%") {
			@Override
			protected void onClick() {
				Dungeon.hero.levelup=false;
				Dungeon.hero.setSpeed(Dungeon.hero.getSpeed() + getSpd(lvl) * 0.01f);
				Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE, Utils.format("+ %d", getSpd(lvl)) + "% speed");
				GLog.p("You feel your body moving faster.");
				hide();
			}
		};
		if(Dungeon.hero.lvl % 3 == 0){
		btnspeed.setRect(0, y, WIDTH, BTN_HEIGHT);
			add(btnspeed);
			y = btnspeed.bottom() + GAP;
		}


		RedButton btnDR = new RedButton(Utils.capitalize(Utils.format(TXT_DR, getDR(lvl)).toUpperCase(Locale.ENGLISH))) {
			@Override
			protected void onClick() {
				Dungeon.hero.levelup=false;
				Dungeon.hero.DR += 1;
				Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE, Utils.format("+ %d dmg reduction", getDR(lvl)));
				GLog.p("You feel your body stronger than before.");
				hide();
			}
		};
		if(Dungeon.hero.lvl % 5 == 0){
		btnDR.setRect(0, y, WIDTH, BTN_HEIGHT);
		add(btnDR);
		y = btnDR.bottom() + GAP;
		}

		resize(WIDTH, (int) y);
	}

	@Override
	public void onBackPressed() {
	}

	private int getStr(int lvl){

		return 1;

	}

	private int getMag(int lvl){

//		int points=0;
//
//		if(hero.heroClass== HeroClass.WARRIOR){ points+=Random.Roll(1,1);}
//		if(hero.heroClass== HeroClass.ROGUE){ points+=Random.Roll(1,2);}
//		if(hero.heroClass== HeroClass.MAGE){ points+=Random.Roll(1,3);}
//		if(hero.heroClass== HeroClass.HUNTRESS){ points+=Random.Roll(1,2);}

		return 1;

	}

	private int getPhys(int lvl){


		return 1;

	}

	private int getMas(int lvl){


		return 1;

	}

	private int getLiqDmg(int lvl){


		return 1;

	}

	private int getDR(int lvl){


		return 1;

	}

	private int getSpd(int lvl){


		return 1;

	}

	private int getAmbush(int lvl){


		return 1;

	}

	private int getDmg(int lvl){


		return 1;

	}

	private int getCrit(int lvl){

		return 1;

	}

	private int getCritChance(int lvl){

		return 1;

	}

	private int getAS(int lvl){

		return 1;

	}


}
