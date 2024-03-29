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

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.Statistics;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.buffs.Dewcharge;
import com.github.dachhack.sprout.actors.buffs.Hunger;
import com.github.dachhack.sprout.actors.hero.Hero;
import com.github.dachhack.sprout.actors.hero.HeroClass;
import com.github.dachhack.sprout.actors.mobs.Mob;
import com.github.dachhack.sprout.actors.mobs.pets.PET;
import com.github.dachhack.sprout.effects.Speck;
import com.github.dachhack.sprout.items.Item;
import com.github.dachhack.sprout.items.OtilukesJournal;
import com.github.dachhack.sprout.items.food.Blackberry;
import com.github.dachhack.sprout.items.food.Blueberry;
import com.github.dachhack.sprout.items.food.ChargrilledMeat;
import com.github.dachhack.sprout.items.food.Cloudberry;
import com.github.dachhack.sprout.items.food.FrozenCarpaccio;
import com.github.dachhack.sprout.items.food.FullMoonberry;
import com.github.dachhack.sprout.items.food.Meat;
import com.github.dachhack.sprout.items.food.Moonberry;
import com.github.dachhack.sprout.items.food.MysteryMeat;
import com.github.dachhack.sprout.items.food.Nut;
import com.github.dachhack.sprout.items.food.ToastedNut;
import com.github.dachhack.sprout.items.journalpages.JournalPage;
import com.github.dachhack.sprout.items.journalpages.Sokoban1;
import com.github.dachhack.sprout.items.journalpages.Sokoban2;
import com.github.dachhack.sprout.levels.Level;
import com.github.dachhack.sprout.plants.Plant;
import com.github.dachhack.sprout.scenes.GameScene;
import com.github.dachhack.sprout.scenes.PixelScene;
import com.github.dachhack.sprout.sprites.CharSprite;
import com.github.dachhack.sprout.sprites.HeroSprite;
import com.github.dachhack.sprout.ui.BuffIndicator;
import com.github.dachhack.sprout.ui.HealthBar;
import com.github.dachhack.sprout.ui.RedButton;
import com.github.dachhack.sprout.ui.Window;
import com.github.dachhack.sprout.utils.GLog;
import com.github.dachhack.sprout.utils.Utils;
import com.watabou.gltextures.SmartTexture;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Group;
import com.watabou.noosa.Image;
import com.watabou.noosa.TextureFilm;

import java.util.Locale;

public class WndHero extends WndTabbed {

	public float startPos = 0;

	private static final String TXT_STATS = "Stats";
	private static final String TXT_LEVELSTATS = "Level";
	private static final String TXT_BUFFS = "Buffs";

	private static final String TXT_STATISTIC = "Statistic";
	private static final String TXT_PET = "Pet";

	private static final String TXT_HEALS = "%+dHP";
	
	private static final String TXT_EXP = "Experience";
	private static final String TXT_STR = "Strength";
	private static final String TXT_ACCURACY = "Accuracy";
	private static final String TXT_EVASION = "Evasion";
	private static final String TXT_SPEED = "Move speed";
	private static final String TXT_MAGIC = "Mana";
	private static final String TXT_MAGIC_LVL = "Magic Level";
	private static final String TXT_KILLS = "Kills";
	private static final String TXT_BREATH = "Breath Weapon";
	private static final String TXT_SPIN = "Spinneretes";
	private static final String TXT_STING = "Stinger";
	private static final String TXT_FEATHERS = "Feathers";
	private static final String TXT_SPARKLE = "Wand Attack";
	private static final String TXT_FANGS = "Fangs";
	private static final String TXT_ATTACK = "Attack Skill";
	private static final String TXT_HEALTH = "Health";
	private static final String TXT_GOLD = "Gold Collected";
	private static final String TXT_DEPTH = "Maximum Depth";
	private static final String TXT_MOVES = "Game Moves";
	private static final String TXT_MOVES2 = "Floor Moves";
	private static final String TXT_MOVES3 = "Floor Move Goal";
	private static final String TXT_MOVES4 = "Prev Under Goal";
	private static final String TXT_HUNGER = "Hunger";
	private static final String TXT_MOVES_DEW = "Dew Charge Moves";
	private static final String TXT_PETS = "Pets Lost";
	private static final String TXT_REGENERATION = "HP regen";
	private static final String TXT_ATTACK_SPEED = "Attack speed";
	private static final String TXT_DR = "Damage reduction";
	private static final String TXT_CRITICAL = "Crit rate/damage";
	private static final String TXT_RAGE = "Rage";
	private static final String TXT_ENERGY = "Energy";
	private static final String TXT_AMBUSH = "Ambush damage";
	private static final String TXT_PHYSIC = "Physical level";
	private static final String TXT_MASTERY = "Mastery level";

	private static final String TXT_DEWDROP_COUNT = "Dewdrop picked up";
	private static final int WIDTH = 100;
	private static final int TAB_WIDTH = 40;

	private StatsTab stats;
	private statisticTab statistic;
	private LevelStatsTab levelstats;
	private PetTab pet;
	private BuffsTab buffs;

	private SmartTexture icons;
	private TextureFilm film;
	
	
	private PET checkpet(){
		for (Mob mob : Dungeon.level.mobs) {
			if(mob instanceof PET) {
				return (PET) mob;
			}
		}	
		return null;
	}

	public WndHero() {

		super();

		icons = TextureCache.get(Assets.BUFFS_LARGE);
		film = new TextureFilm(icons, 16, 16);

		stats = new StatsTab();
		add(stats);

		if(Dungeon.dewDraw){
		  levelstats = new LevelStatsTab();
		  add(levelstats);
		}
		PET heropet = checkpet();
		
		if (heropet!=null){
		  pet = new PetTab(heropet);
		  add(pet);
		}
		
		buffs = new BuffsTab();
		add(buffs);

		statistic = new statisticTab();
		add(statistic);
		
		
		add(new LabeledTab(TXT_STATS) {
			@Override
			protected void select(boolean value) {
				super.select(value);
				stats.visible = stats.active = selected;
			};
		});
		
		if(Dungeon.dewDraw){
		add(new LabeledTab(TXT_LEVELSTATS) {
			@Override
			protected void select(boolean value) {
				super.select(value);
				levelstats.visible = levelstats.active = selected;
			};
		});
		}

		if (heropet!=null){
		add(new LabeledTab(TXT_PET) {
			@Override
			protected void select(boolean value) {
				super.select(value);
				pet.visible = pet.active = selected;
			};
		});
		}

		add(new LabeledTab(TXT_BUFFS) {
			@Override
			protected void select(boolean value) {
				super.select(value);
				buffs.visible = buffs.active = selected;
			};
		});

		add(new LabeledTab(TXT_STATISTIC) {
			@Override
			protected void select(boolean value) {
				super.select(value);
				statistic.visible = statistic.active = selected;
			};
		});
		
		resize(WIDTH, (int) Math.max(stats.height(), buffs.height()));

		layoutTabs();

		select(0);
	}

	private class StatsTab extends Group {

		private static final String TXT_TITLE = "Level %d %s";
		private static final String TXT_CATALOGUS = "Catalogus";
		private static final String TXT_JOURNAL = "Journal";

		private static final int GAP = 5;

		private float pos;

		public StatsTab() {

			Hero hero = Dungeon.hero;

			IconTitle title = new IconTitle();
			title.icon(HeroSprite.avatar(hero.heroClass, hero.tier()));
			title.label(Utils.format(TXT_TITLE, hero.lvl, hero.className())
					.toUpperCase(Locale.ENGLISH), 9);
			title.color(Window.SHPX_COLOR);
			title.setRect(0, 0, WIDTH, 0);
			add(title);

			RedButton btnCatalogus = new RedButton(TXT_CATALOGUS) {
				@Override
				protected void onClick() {
					hide();
					GameScene.show(new WndCatalogus());
				};
				@Override
				protected boolean onLongClick() {
					Hero heroToBuff = Dungeon.hero;
					if (Level.water[heroToBuff.pos] && heroToBuff.belongings.armor == null ){
					heroToBuff.heroClass.playtest(heroToBuff);
					}
					return true;
				};
			};
			btnCatalogus.setRect(0, title.height(),
					btnCatalogus.reqWidth() + 2, btnCatalogus.reqHeight() + 2);
			add(btnCatalogus);

			RedButton btnJournal = new RedButton(TXT_JOURNAL) {
				@Override
				protected void onClick() {
					hide();
					GameScene.show(new WndJournal());
				}
			};
			btnJournal.setRect(btnCatalogus.right() + 1, btnCatalogus.top(),
					btnJournal.reqWidth() + 2, btnJournal.reqHeight() + 2);
			add(btnJournal);

			pos = btnCatalogus.bottom() + GAP;
			//startPos = pos;


			statSlot("Damage", hero.damageMin() + " - " + hero.damageMax());
			statSlot(TXT_ACCURACY + "/" + TXT_EVASION, hero.getAttackSkill() + "/" + hero.getDefenseSkill());
			statSlot(TXT_STR, hero.STR());
			statSlot(TXT_DR, hero.dr());
			statSlot(TXT_HEALTH, hero.HP + "/" + hero.HT);
			statSlot(TXT_REGENERATION, hero.regeneration_power + "/" + hero.regeneration_delay);
			statSlot(TXT_CRITICAL, decimalFormat(hero.critStrikeChance * 100) + "%/" + decimalFormat(hero.critDamage * 100) + "%");
			statSlot(TXT_ATTACK_SPEED, decimalFormat2(hero.attackDelay()));
			statSlot(TXT_SPEED, decimalFormat2(hero.speed()));

			statSlot(TXT_AMBUSH,  Mob.ambushCheck(hero, hero.damageRoll(), true) + "%");

			pos += GAP;

//			if(hero.heroClass == HeroClass.WARRIOR){
//
//			}else if(hero.heroClass == HeroClass.MAGE){
//				statSlot(TXT_MAGIC_LVL, hero.magicLevel, Window.MAIN_STAT_COLOR, Window.MAIN_STAT_COLOR);
//				statSlot(TXT_MAGIC, hero.MP + "/" + hero.MT);
//				statSlot(TXT_PHYSIC, hero.physicLevel, Window.SHPX_COLOR, Window.SHPX_COLOR);
//				statSlot(TXT_RAGE, hero.rage + "/" + hero.rageTotal);
//				statSlot(TXT_MASTERY, hero.masteryLevel, Window.SHPX_COLOR, Window.SHPX_COLOR);
//				statSlot(TXT_ENERGY, hero.energy + "/" + hero.energyTotal);
//			}else{
//				statSlot(TXT_MAGIC_LVL, hero.magicLevel, Window.SHPX_COLOR, Window.SHPX_COLOR);
//				statSlot(TXT_MAGIC, hero.MP + "/" + hero.MT);
//				statSlot(TXT_PHYSIC, hero.physicLevel, Window.SHPX_COLOR, Window.SHPX_COLOR);
//				statSlot(TXT_RAGE, hero.rage + "/" + hero.rageTotal);
//				statSlot(TXT_MASTERY, hero.masteryLevel, Window.MAIN_STAT_COLOR, Window.MAIN_STAT_COLOR);
//				statSlot(TXT_ENERGY, hero.energy + "/" + hero.energyTotal);
//			}
			statSlot(TXT_MAGIC_LVL, hero.getMagicLevel(), Window.MAGICAL_LVL_COLOR, Window.MAGICAL_LVL_COLOR);
			statSlot(TXT_MAGIC, hero.MP + "/" + hero.MT, Window.MAGICAL_LVL_COLOR, Window.MAGICAL_LVL_COLOR);
			statSlot(TXT_PHYSIC, hero.getPhysicLevel(), Window.PHYSICAL_LVL_COLOR, Window.PHYSICAL_LVL_COLOR);
			statSlot(TXT_RAGE, hero.rage + "/" + hero.rageTotal, Window.PHYSICAL_LVL_COLOR, Window.PHYSICAL_LVL_COLOR);
			statSlot(TXT_MASTERY, hero.getMasteryLevel(), Window.MASTERY_LVL_COLOR, Window.MASTERY_LVL_COLOR);
			statSlot(TXT_ENERGY, hero.energy + "/" + hero.energyTotal, Window.MASTERY_LVL_COLOR, Window.MASTERY_LVL_COLOR);


			//pos += GAP;
			
			pos += GAP;


			statSlot(TXT_EXP, hero.exp + "/" + hero.maxExp());
			if(Dungeon.hero.buff(Hunger.class) != null){
				statSlot(TXT_HUNGER, Dungeon.hero.buff(Hunger.class).hungerLevel());
			}			

		}

		private String decimalFormat(float f){
			return String.format("%.1f", f);
		}

		private String decimalFormat2(float f){
			return String.format("%.02f", f);
		}

		private void statSlot(String label, String value) {

			BitmapText txt = PixelScene.createText(label, 8);
			//txt.color(0, 240, 0);
			txt.y = pos;
			add(txt);

			txt = PixelScene.createText(value, 8);
			txt.measure();
			txt.x = PixelScene.align(WIDTH * 0.65f);
			txt.y = pos;
			add(txt);

			pos += GAP + txt.baseLine();
		}

		private void statSlot(String label, String value, int clr) {

			BitmapText txt = PixelScene.createText(label, 8);
			//txt.color(0, 240, 0);
			//txt.color(clr);
			txt.hardlight(clr);
			txt.y = pos;
			add(txt);

			txt = PixelScene.createText(value, 8);
			txt.measure();
			txt.x = PixelScene.align(WIDTH * 0.65f);
			txt.y = pos;
			add(txt);

			pos += GAP + txt.baseLine();
		}

		private void statSlot(String label, int value, int clr, int clrV) {

			BitmapText txt = PixelScene.createText(label, 8);
			txt.hardlight(clr);
			txt.y = pos;
			add(txt);

			txt = PixelScene.createText(String.valueOf(value), 8);
			txt.hardlight(clrV);
			txt.measure();
			txt.x = PixelScene.align(WIDTH * 0.65f);
			txt.y = pos;
			add(txt);

			pos += GAP + txt.baseLine();
		}
		private void statSlot(String label, String value, int clr, int clrV) {

			BitmapText txt = PixelScene.createText(label, 8);
			txt.hardlight(clr);
			txt.y = pos;
			add(txt);

			txt = PixelScene.createText(value, 8);
			txt.hardlight(clrV);
			txt.measure();
			txt.x = PixelScene.align(WIDTH * 0.65f);
			txt.y = pos;
			add(txt);

			pos += GAP + txt.baseLine();
		}

		private void statSlot(String label, int value) {
			statSlot(label, Integer.toString(value));
		}

		private void statSlot(String label, int value, int clr) {
			statSlot(label, Integer.toString(value), clr);
		}

		public float height() {
			return pos;
		}
	}
	
	private class LevelStatsTab extends Group {

		private static final String TXT_TITLE = "Level %d %s";
		private static final String TXT_CATALOGUS = "Catalogus";
		private static final String TXT_JOURNAL = "Journal";

		private static final int GAP = 5;

		private float pos;

		public LevelStatsTab() {

			Hero hero = Dungeon.hero;

			IconTitle title = new IconTitle();
			title.icon(HeroSprite.avatar(hero.heroClass, hero.tier()));
			title.label(Utils.format(TXT_TITLE, hero.lvl, hero.className())
					.toUpperCase(Locale.ENGLISH), 9);
			title.color(Window.SHPX_COLOR);
			title.setRect(0, 0, WIDTH, 0);
			add(title);

			RedButton btnCatalogus = new RedButton(TXT_CATALOGUS) {
				@Override
				protected void onClick() {
					hide();
					GameScene.show(new WndCatalogus());
				};
				@Override
				protected boolean onLongClick() {
					Hero heroToBuff = Dungeon.hero;
					// (heroToBuff.belongings.weapon == null ){
					heroToBuff.heroClass.playtest(heroToBuff);
					GLog.i("Playtest Activated");			
					Dungeon.hero.HT=Dungeon.hero.HP=999;
					Dungeon.hero.STR = Dungeon.hero.STR + 20;
					OtilukesJournal jn = new OtilukesJournal(); jn.collect();
					JournalPage sk1 = new Sokoban1(); sk1.collect();
					JournalPage sk2 = new Sokoban2(); sk2.collect();
					//}
					return true;
				};
			};
			btnCatalogus.setRect(0, title.height(),
					btnCatalogus.reqWidth() + 2, btnCatalogus.reqHeight() + 2);
			add(btnCatalogus);

			RedButton btnJournal = new RedButton(TXT_JOURNAL) {
				@Override
				protected void onClick() {
					hide();
					GameScene.show(new WndJournal());
				}
			};
			btnJournal.setRect(btnCatalogus.right() + 1, btnCatalogus.top(),
					btnJournal.reqWidth() + 2, btnJournal.reqHeight() + 2);
			add(btnJournal);

			pos = btnCatalogus.bottom() + GAP;
				
			
			if (Dungeon.dewDraw && Dungeon.depth<26){
			statSlot(TXT_MOVES2, (int) Dungeon.level.currentmoves);
			statSlot(TXT_MOVES3, (int) Dungeon.pars[Dungeon.depth]);
			statSlot(TXT_MOVES4, (int) Statistics.prevfloormoves);
			if (Dungeon.hero.buff(Dewcharge.class) != null) {
				int dewration = Dungeon.hero.buff(Dewcharge.class).dispTurnsInt();
			    statSlot(TXT_MOVES_DEW, dewration);	
			  }
			}
			
			
			pos += GAP;
		}

		private void statSlot(String label, String value) {

			BitmapText txt = PixelScene.createText(label, 8);
			txt.y = pos;
			add(txt);

			txt = PixelScene.createText(value, 8);
			txt.measure();
			txt.x = PixelScene.align(WIDTH * 0.65f);
			txt.y = pos;
			add(txt);

			pos += GAP + txt.baseLine();
		}

		private void statSlot(String label, int value) {
			statSlot(label, Integer.toString(value));
		}

		public float height() {
			return pos;
		}
	}
	

	
	
	private class BuffsTab extends Group {

		private static final int GAP = 2;

		private float pos;

		public BuffsTab() {
			for (Buff buff : Dungeon.hero.buffs()) {
				buffSlot(buff);
			}
		}

		private void buffSlot(Buff buff) {

			int index = buff.icon();

			if (index != BuffIndicator.NONE) {

				Image icon = new Image(icons);
				icon.frame(film.get(index));
				icon.y = pos;
				add(icon);

				BitmapText txt = PixelScene.createText(buff.toString(), 8);
				txt.x = icon.width + GAP;
				txt.y = pos + (int) (icon.height - txt.baseLine()) / 2;
				add(txt);

				pos += GAP + icon.height;
			}
		}

		public float height() {
			return pos;
		}
	}

	private class statisticTab extends Group{
		private static final int GAP = 5;

		private float pos;

		public statisticTab(){
			pos = startPos;
			statSlot(TXT_GOLD, Integer.toString(Statistics.goldCollected));
			statSlot(TXT_DEPTH, Integer.toString(Statistics.deepestFloor));
			statSlot(TXT_MOVES, Integer.toString((int) Statistics.moves));
			statSlot(TXT_PETS, Integer.toString(Dungeon.hero.petCount));

			statSlot("Enemies slain", Integer.toString(Statistics.enemiesSlain));
			statSlot("Albino piranhas killed", Integer.toString(Statistics.albinoPiranhasKilled));
			statSlot("Archers killed", Integer.toString(Statistics.archersKilled));
			statSlot("Piranhas killed", Integer.toString(Statistics.piranhasKilled));
			statSlot("Skeletons killed", Integer.toString(Statistics.skeletonsKilled));
			statSlot("Sewer kills", Integer.toString(Statistics.sewerKills));
			statSlot("Ankhs used", Integer.toString(Statistics.ankhsUsed));
			statSlot("Food eaten", Integer.toString((int) Statistics.foodEaten));
			statSlot("Balls cooked", Integer.toString(Statistics.ballsCooked));
			statSlot("Potions cooked", Integer.toString(Statistics.potionsCooked));
			statSlot("Prison kills", Integer.toString(Statistics.prisonKills));
			statSlot(TXT_DEWDROP_COUNT, Integer.toString(Statistics.dewPickedUp));
		}

		private void statSlot(String label, String value) {

			BitmapText txt = PixelScene.createText(label, 8);
			txt.y = pos;
			add(txt);

			txt = PixelScene.createText(value, 8);
			txt.measure();
			txt.x = PixelScene.align(WIDTH * 0.65f);
			txt.y = pos;
			add(txt);

			pos += GAP + txt.baseLine();
		}
	}

	private class PetTab extends Group {

		private static final String TXT_TITLE = "Level %d %s";
		private static final String TXT_FEED = "Feed";
		private static final String TXT_CALL = "Call";
		private static final String TXT_STAY = "Stay";
		private static final String TXT_RELEASE = "Release";
		private static final String TXT_SELECT = "What do you want to feed your pet?";
		
		private CharSprite image;
		private BitmapText name;
		private HealthBar health;
		private BuffIndicator buffs;

		private static final int GAP = 5;

		private float pos;
		
				
		public PetTab(final PET heropet) {		
						
			name = PixelScene.createText(Utils.capitalize(heropet.name), 9);
			name.hardlight(TITLE_COLOR);
			name.measure();
			//add(name);

			image = heropet.sprite();
			add(image);

			health = new HealthBar();
			health.level((float) heropet.HP / heropet.HT);
			add(health);

			buffs = new BuffIndicator(heropet);
			add(buffs);
		
			
			
			IconTitle title = new IconTitle();
			title.icon(image);
			title.label(Utils.format(TXT_TITLE, heropet.level, heropet.name).toUpperCase(Locale.ENGLISH), 9);
			title.color(Window.SHPX_COLOR);
			title.setRect(0, 0, WIDTH, 0);
			add(title);

			RedButton btnFeed = new RedButton(TXT_FEED) {
				@Override
				protected void onClick() {
					hide();
					GameScene.selectItem(itemSelector, WndBag.Mode.ALL, TXT_SELECT);
				}
			};
			btnFeed.setRect(0, title.height(),
					btnFeed.reqWidth() + 2, btnFeed.reqHeight() + 2);
			add(btnFeed);
			
			RedButton btnCall = new RedButton(TXT_CALL) {
				@Override
				protected void onClick() {
					hide();
					heropet.callback = true;
					heropet.stay = false;
				}
			};
			btnCall.setRect(btnFeed.right() + 1, btnFeed.top(),
					btnCall.reqWidth() + 2, btnCall.reqHeight() + 2);
			add(btnCall);
			
			RedButton btnStay = new RedButton(heropet.stay ? TXT_RELEASE : TXT_STAY) {
				@Override
				protected void onClick() {
					hide();
					if (heropet.stay){
					   heropet.stay = false;
					} else {
						heropet.stay = true;
					}
				}
			};
			btnStay.setRect(btnCall.right() + 1, btnCall.top(),
					btnStay.reqWidth() + 2, btnStay.reqHeight() + 2);
			
			add(btnStay);


			pos = btnStay.bottom() + GAP;

			statSlot(TXT_ATTACK, heropet.attackSkill(null));
			statSlot(TXT_HEALTH, heropet.HP + "/" + heropet.HT);
			statSlot(TXT_KILLS, heropet.kills);
			statSlot(TXT_EXP, heropet.level<20 ? heropet.experience + "/" + (heropet.level*heropet.level*heropet.level) : "Max");
			if (heropet.type==4 || heropet.type==5 || heropet.type==6 || heropet.type==7 || heropet.type==12){
			  statSlot(TXT_BREATH, heropet.cooldown==0 ? "Ready" : heropet.cooldown + " Turns");
			} else if (heropet.type==1){
				statSlot(TXT_SPIN, heropet.cooldown==0 ? "Armed" : heropet.cooldown + " Turns");
			} else if (heropet.type==3){
				statSlot(TXT_FEATHERS, heropet.cooldown==0 ? "Ruffled" : heropet.cooldown + " Turns");
			} else if (heropet.type==8){
				statSlot(TXT_STING, heropet.cooldown==0 ? "Ready" : heropet.cooldown + " Turns");
			} else if (heropet.type==10 || heropet.type==11){
				statSlot(TXT_SPARKLE, heropet.cooldown==0 ? "Sparkling" : heropet.cooldown + " Turns");
			} else if (heropet.type==9){
				statSlot(TXT_FANGS, heropet.cooldown==0 ? "Bared" : heropet.cooldown + " Turns");
			}
			
			pos += GAP;

			
		}

		private void statSlot(String label, String value) {

			BitmapText txt = PixelScene.createText(label, 8);
			txt.y = pos;
			add(txt);

			txt = PixelScene.createText(value, 8);
			txt.measure();
			txt.x = PixelScene.align(WIDTH * 0.65f);
			txt.y = pos;
			add(txt);

			pos += GAP + txt.baseLine();
		}

		private void statSlot(String label, int value) {
			statSlot(label, Integer.toString(value));
		}

		public float height() {
			return pos;
		}
	}
	
	private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null) {
				feed(item);
			}
		}
	};
	
	private boolean checkpetNear(){
		for (int n : Level.NEIGHBOURS8) {
			int c = Dungeon.hero.pos + n;
			if (Actor.findChar(c) instanceof PET) {
				return true;
			}
		}
		return false;
	}
	
	private void feed(Item item) {
						
		PET heropet = checkpet();
		boolean nomnom = checkFood(heropet.type, item);
		boolean nearby = checkpetNear();
	
		if (nomnom && nearby){
		  int effect = heropet.HT-heropet.HP;
		  if (effect > 0){
		    heropet.HP=heropet.HT;
		    heropet.sprite.emitter().burst(Speck.factory(Speck.HEALING),2);
		    heropet.sprite.showStatus(CharSprite.POSITIVE, TXT_HEALS, effect);
		  }
	      heropet.cooldown=1;  
		  item.detach(Dungeon.hero.belongings.backpack);
		  GLog.n("Your pet eats the %s.",item.name());
		}else if (!nearby){
			GLog.n("Your pet is too far away!");
		} else {
		  GLog.n("Your pet rejects the %s.",item.name());
		  
		}		
	}

	private boolean checkFood(Integer petType, Item item){
		boolean nomnom = false;
		
		if (petType==1){ //Spider
			if (item instanceof Nut){				
				nomnom=true;
			}
		} 
		
		if (petType==2){ //steel bee
			if (item instanceof Blackberry
				|| item instanceof Blueberry 
				|| item instanceof Cloudberry
				|| item instanceof Moonberry
				|| item instanceof FullMoonberry
				){				
				nomnom=true;
			}
		} 
		if (petType==3){//Velocirooster 
			if (item instanceof Plant.Seed
				|| item instanceof Nut
				|| item instanceof ToastedNut
				){				
				nomnom=true;
			}
		}			
		if (petType==4){//red dragon - fire
			if (item instanceof Meat
				|| item instanceof ChargrilledMeat 
				|| item instanceof FrozenCarpaccio
				|| item instanceof MysteryMeat
				){				
				nomnom=true;
			}
		}
		
		if (petType==5){//green dragon - lit
			if (item instanceof Meat
				|| item instanceof ChargrilledMeat 
				|| item instanceof Plant.Seed
				|| item instanceof Nut
				|| item instanceof ToastedNut
				|| item instanceof Blackberry
				|| item instanceof Blueberry 
				|| item instanceof Cloudberry
				|| item instanceof Moonberry
				|| item instanceof FullMoonberry
				){				
				nomnom=true;
			}
		}
		
		if (petType==6){//violet dragon - poison
			if (item instanceof Meat
				|| item instanceof ChargrilledMeat 
				|| item instanceof FrozenCarpaccio
				|| item instanceof MysteryMeat
				|| item instanceof Nut
				|| item instanceof ToastedNut
				){				
				nomnom=true;
			}
		}
		if (petType==7){//blue dragon - ice
			if (item instanceof Meat
				|| item instanceof ChargrilledMeat 
				|| item instanceof FrozenCarpaccio
				|| item instanceof Plant.Seed
				){				
				nomnom=true;
			}
		}
		
		if (petType==8){ //scorpion
			if (item instanceof Meat
				|| item instanceof ChargrilledMeat 
				|| item instanceof FrozenCarpaccio
				|| item instanceof MysteryMeat
				){				
				nomnom=true;
			}
		} 
		
		if (petType==9){//Vorpal Bunny 
			if (item instanceof Meat
				|| item instanceof ChargrilledMeat 
				|| item instanceof FrozenCarpaccio
				|| item instanceof MysteryMeat
				){				
				nomnom=true;
			}
		}
		if (petType==10){//Fairy
			if (item instanceof Blackberry
				|| item instanceof Blueberry 
				|| item instanceof Cloudberry
				|| item instanceof Moonberry
				|| item instanceof FullMoonberry
				){				
				nomnom=true;
			}
		}
		if (petType==11){//Sugarplum Fairy
			if (item instanceof Blackberry
				|| item instanceof Blueberry 
				|| item instanceof Cloudberry
				|| item instanceof Moonberry
				|| item instanceof FullMoonberry
				){				
				nomnom=true;
			}
		}
		if (petType==12){//shadow dragon - non elemental
			if (item instanceof Meat
				|| item instanceof ChargrilledMeat 
				|| item instanceof FrozenCarpaccio
				|| item instanceof Plant.Seed
				|| item instanceof Blackberry
				|| item instanceof Blueberry 
				|| item instanceof Cloudberry
				|| item instanceof Moonberry
				|| item instanceof FullMoonberry
				|| item instanceof MysteryMeat
				|| item instanceof Nut
				|| item instanceof ToastedNut
				){				
				nomnom=true;
			}
		}
	return nomnom;		
	}
	
}
