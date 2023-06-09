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

import java.util.ArrayList;
import java.util.Locale;

public class WndLevelUp extends Window {

	private static final String TXT_MESSAGE_EVAP = "The Life Drop evaporates releasing it's power!";
	private static final String TXT_MESSAGE = "Which attribute do you want to increase?";
	private static final String TXT_MESSAGE_TALENTS = "Which talent do you want to learn?";
	private static final String TXT_SPEED = "Speed  + %d";
	private static final String TXT_AS = "Attack speed  + %d";
	private static final String TXT_STR = "Strength  + %d";
	private static final String TXT_MAGIC = "Magic  + %d";
	private static final String TXT_PHYSIC = "Physic  + %d";
	private static final String TXT_MASTERY = "Mastery  + %d";
	private static final String TXT_TITLE = "Level %d %s";
	private static final String TXT_DR = "DR  + %d";
	private static final String TXT_HP_REGEN = "HP regen (turns)  + %d";
	private static final String TXT_HP_REGEN_power = "HP regen (power)  + %d";
	private static final String TXT_HT = "Health  + %d";
	private static final String TXT_MP = "MP  + %d";
	private static final String TXT_DMG = "Damage  + %d";
	private static final String TXT_ACCURACY = "Accuracy  + %d";
	private static final String TXT_EVASION = "Evasion  + %d";
	private static final String TXT_CRIT_RATE = "Crit rate  + %d";
	private static final String TXT_CRIT_DMG = "Crit dmg  + %d";
	private static final String TXT_AMBUSH = "Ambush dmg  + %d";

	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 18;
	private static final float GAP = 2;

	private static final int NUMBER_OF_STAT_BUTTONS = 4;

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

		if (lvl % 10 == 0) {
			talents(title, lvl, hero);
		} else {
			stats(title, lvl, hero);
		}
	}

	private void talents(IconTitle title, int lvl, Hero hero){
		BitmapTextMultiline message = PixelScene.createMultiline(TXT_MESSAGE_TALENTS, 6);
		message.maxWidth = WIDTH;
		message.measure();
		message.y = title.bottom() + GAP;
		add(message);



		switch (lvl){
			case 10:
				hero.spellbook.learnSpell(SpellBook.spellList.MORTAL_STRIKE);
				switch (hero.heroClass){
					case WARRIOR:
						System.out.println("WARRIOR");
						break;

					case ROGUE:
						System.out.println("ROGUE");

						RedButton btn = new RedButton("First talent".toUpperCase(Locale.ENGLISH)) {
							@Override
							protected void onClick() {
								hero.levelup = false;
								hero.invisRegen = true;
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
								hero.levelup = false;
								//Dungeon.hero.MT += 2;
								hero.invisSpeed = true;
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
		}

		resize(WIDTH, (int) y);
	}


	private void stats(IconTitle title, int lvl, Hero hero){
		BitmapTextMultiline message = PixelScene.createMultiline(TXT_MESSAGE, 6);
		message.maxWidth = WIDTH;
		message.measure();
		message.y = title.bottom() + GAP;
		add(message);


		ArrayList<RedButton> buttons = new ArrayList<>();
		RedButton btnStr = new RedButton(Utils.format(TXT_STR, getStr()).toUpperCase(Locale.ENGLISH)) {
			@Override
			protected void onClick() {
				hero.levelup = false;
				hero.STR += getStr();
				hero.sprite.showStatus(CharSprite.POSITIVE, Utils.format(TXT_STR, getStr()));
				GLog.p("Newfound strength surges through your body.");
				hide();
			}
		};
		buttons.add(btnStr);
		RedButton btnSpeed = new RedButton(Utils.capitalizeUp(Utils.format(TXT_SPEED, getSpd())) + "%") {
			@Override
			protected void onClick() {
				hero.levelup=false;
				hero.setSpeed(hero.getSpeed() + getSpd() * 0.01f);
				hero.sprite.showStatus(CharSprite.POSITIVE, Utils.format(TXT_SPEED, getSpd()) + "%");
				GLog.p("You feel your body moving faster.");
				hide();
			}
		};
		buttons.add(btnSpeed);
		RedButton btnAS = new RedButton(Utils.capitalizeUp(Utils.format(TXT_AS, getAS())) + "%") {
			@Override
			protected void onClick() {
				hero.levelup=false;
				hero.additAS += getAS() * 0.01f;
				hero.sprite.showStatus(CharSprite.POSITIVE, Utils.format(TXT_AS, getAS()) + "%");
				GLog.p("Your attacks faster now.");
				hide();
			}
		};
		buttons.add(btnAS);
		RedButton btnDR = new RedButton(Utils.capitalizeUp(Utils.format(TXT_DR, getDR()))) {
			@Override
			protected void onClick() {
				hero.levelup=false;
				hero.DR += getDR();
				hero.sprite.showStatus(CharSprite.POSITIVE, Utils.format(TXT_DR, getDR()));
				GLog.p("You feel your skin durable.");
				hide();
			}
		};
		buttons.add(btnDR);
		RedButton btnHT = new RedButton(Utils.capitalizeUp(Utils.format(TXT_HT, getHT()))) {
			@Override
			protected void onClick() {
				hero.levelup=false;
				hero.HT += getHT();
				hero.sprite.showStatus(CharSprite.POSITIVE, Utils.format(TXT_HT, getHT()));
				GLog.p("You feel healthy.");
				hide();
			}
		};
		buttons.add(btnHT);
		RedButton btnMP = new RedButton(Utils.capitalizeUp(Utils.format(TXT_MP, getMP()))) {
			@Override
			protected void onClick() {
				hero.levelup=false;
				hero.MT += getMP();
				hero.sprite.showStatus(CharSprite.POSITIVE, Utils.format(TXT_MP, getMP()));
				GLog.p("You feel a new enveloping aura.");
				hide();
			}
		};
		buttons.add(btnMP);
		RedButton btnDMG = new RedButton(Utils.capitalizeUp(Utils.format(TXT_DMG, getDmg()))) {
			@Override
			protected void onClick() {
				hero.levelup=false;
				hero.bonusDamage += getDmg();
				hero.sprite.showStatus(CharSprite.POSITIVE, Utils.format(TXT_DMG, getDmg()));
				GLog.p("You feel powerful.");
				hide();
			}
		};
		buttons.add(btnDMG);
		RedButton btnMagic = new RedButton(Utils.capitalizeUp(Utils.format(TXT_MAGIC, getMag()))) {
			@Override
			protected void onClick() {
				hero.levelup = false;
				//Dungeon.hero.MT += 2;
				hero.increaseMagicalLvl(getMag());
				hero.sprite.showStatus(CharSprite.POSITIVE, Utils.format(TXT_MAGIC, getMag()));
				GLog.p("You feel the surge of arcane magics in your body.");
				hide();
			}
		};
		buttons.add(btnMagic);
		RedButton btnPhys = new RedButton(Utils.capitalizeUp(Utils.format(TXT_PHYSIC, getPhys()))) {
			@Override
			protected void onClick() {
				hero.levelup=false;
				hero.increasePhysicalLvl(getPhys());
				hero.sprite.showStatus(CharSprite.POSITIVE, Utils.format(TXT_PHYSIC, getPhys()));
				GLog.p("You feel your body stronger than before.");
				hide();
			}
		};
		buttons.add(btnPhys);
		RedButton btnMastery = new RedButton(Utils.capitalizeUp(Utils.format(TXT_MASTERY, getMas()))) {
			@Override
			protected void onClick() {
				hero.levelup=false;
				hero.increaseMasteryLvl(getMas());
				hero.sprite.showStatus(CharSprite.POSITIVE, Utils.format(TXT_MASTERY, getMas()));
				GLog.p("You feel skillfully than before.");
				hide();
			}
		};
		buttons.add(btnMastery);
		y = message.y + message.height() + GAP;
		//пофиксил (dmg min/max)


		for(int j = 0; j < NUMBER_OF_STAT_BUTTONS; j++){
			int i = Random.Int(buttons.size());
			buttons.get(i).setRect(0, y, WIDTH,
					BTN_HEIGHT);
			add(buttons.get(i));
			y = buttons.get(i).bottom() + GAP;
			buttons.remove(i);
		}

		resize(WIDTH, (int) y);
	}

	@Override
	public void onBackPressed() {
	}

	private int getStr(){
		return 1;
	}

	private int getMag(){

//		int points=0;
//
//		if(hero.heroClass== HeroClass.WARRIOR){ points+=Random.Roll(1,1);}
//		if(hero.heroClass== HeroClass.ROGUE){ points+=Random.Roll(1,2);}
//		if(hero.heroClass== HeroClass.MAGE){ points+=Random.Roll(1,3);}
//		if(hero.heroClass== HeroClass.HUNTRESS){ points+=Random.Roll(1,2);}

		return 1;

	}

	private int getPhys(){
		return 1;
	}

	private int getMas(){
		return 1;
	}

//	private int getLiqDmg(){
//
//
//		return 1;
//
//	}

	private int getDR(){
		return 1;

	}
	private int getRegenRate(){
		return 1;

	}
	private int getRegenPower(){
		return 1;
	}
	private int getSpd(){
		return 5;

	}
	private int getAmbush(){
		return 1;

	}
	private int getDmg(){
		return 1;

	}
	private int getCrit(){
		return 1;
	}
	private int getCritRate(){
		return 1;
	}
	private int getAS(){
		return 2;
	}
	private int getHT(){
		return 5;
	}
	private int getMP(){
		return 5;
	}
	private int getEvasion(){
		return 1;
	}
	private int getAccuracy(){
		return 1;
	}

}
