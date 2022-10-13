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

import android.graphics.RectF;
import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.ShatteredPixelDungeon;
import com.github.dachhack.sprout.actors.hero.Belongings;
import com.github.dachhack.sprout.actors.hero.Hero;
import com.github.dachhack.sprout.items.*;
import com.github.dachhack.sprout.items.bags.*;
import com.github.dachhack.sprout.items.spells.Spell;
import com.github.dachhack.sprout.items.spells.SpellBook;
import com.github.dachhack.sprout.scenes.GameScene;
import com.github.dachhack.sprout.scenes.PixelScene;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.ui.Icons;
import com.github.dachhack.sprout.ui.ItemSlot;
import com.github.dachhack.sprout.ui.QuickSlotButton;
import com.github.dachhack.sprout.utils.GLog;
import com.github.dachhack.sprout.utils.Utils;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;

public class WndSpellBook extends WndTabbed {

//	public enum Mode {
//		ALL, UNIDENTIFED, UPGRADEABLE, QUICKSLOT, FOR_SALE, WEAPON, ARMOR, ENCHANTABLE,
//		WAND, SEED, FOOD, POTION, SCROLL, EQUIPMENT, ADAMANT, REINFORCED, UPGRADEABLESIMPLE,
//		NOTREINFORCED, UPGRADEDEW, JOURNALPAGES
//	}

	protected static final int COLS_P = 4;
	protected static final int COLS_L = 6;

	protected static final int SLOT_SIZE = 26;
	protected static final int SLOT_MARGIN = 1;

	protected static final int TITLE_HEIGHT = 12;

	private Listener listener;
	//private WndSpellBook.Mode mode;
	private String title;

	private int nCols;
	private int nRows;

	protected int count;
	protected int col;
	protected int row;

	//private static Mode lastMode;
	private static SpellBook lastSB;

	public WndSpellBook(SpellBook SB, Listener listener, String title) {
		super();

		this.listener = listener;
		this.title = title;

		lastSB = SB;

		nCols = ShatteredPixelDungeon.landscape() ? COLS_L : COLS_P;
		nRows = (Belongings.BACKPACK_SIZE + 4 + 1) / nCols
				+ ((Belongings.BACKPACK_SIZE + 4 + 1) % nCols > 0 ? 1 : 0);

		int slotsWidth = SLOT_SIZE * nCols + SLOT_MARGIN * (nCols - 1);
		int slotsHeight = SLOT_SIZE * nRows + SLOT_MARGIN * (nRows - 1);

		BitmapText txtTitle = PixelScene.createText(title != null ? title
				: Utils.capitalize(SB.name()), 9);
		txtTitle.hardlight(TITLE_COLOR);
		txtTitle.measure();
		txtTitle.x = (int) (slotsWidth - txtTitle.width()) / 2;
		txtTitle.y = (int) (TITLE_HEIGHT - txtTitle.height()) / 2;
		add(txtTitle);

		placeItems(SB);

		resize(slotsWidth, slotsHeight + TITLE_HEIGHT);

//		Belongings stuff = Dungeon.hero.belongings;
//		Bag[] bags = { stuff.backpack, stuff.getItem(SeedPouch.class),
//				stuff.getItem(ScrollHolder.class),
//				stuff.getItem(PotionBandolier.class),
//				stuff.getItem(WandHolster.class),
//				stuff.getItem(KeyRing.class),
//				stuff.getItem(AnkhChain.class)};

//		for (Bag b : bags) {
//			if (b != null) {
//				BagTab tab = new BagTab(b);
//				add(tab);
//				tab.select(b == SB);
//			}
//		}
		BitmapText txt = PixelScene.createText("Active", 7);
		BitmapText txt2 = PixelScene.createText("Passive", 7);
		txt.measure();
		txt2.measure();

		BagTab tab = new BagTab(txt);
		BagTab tab2 = new BagTab(txt2);
		add(tab);
		add(tab2);
		tab.select(true);

		layoutTabs();
	}

//	public static WndSpellBook lastBag(Listener listener, Mode mode, String title) {
//
//		if (mode == lastMode && lastSB != null
//				&& Dungeon.hero.belongings.backpack.contains(lastSB)) {
//
//			return new WndSpellBook(lastSB, listener, mode, title);
//
//		} else {
//
////			return new WndSpellBook(Dungeon.hero.belongings.backpack, listener, mode,
////					title);
//			//GLog.n();
//			return null;
//
//		}
//	}

//	public static WndSpellBook getBag(Class<? extends Bag> bagClass,
//                                      Listener listener, Mode mode, String title) {
//		Bag bag = Dungeon.hero.belongings.getItem(bagClass);
//		return bag != null ? new WndSpellBook(bag, listener, mode, title) : lastBag(
//				listener, mode, title);
//	}

	protected void placeItems(SpellBook SB) {

		// Equipped items
//		Belongings stuff = Dungeon.hero.belongings;
//		placeItem(stuff.weapon != null ? stuff.weapon : new Placeholder(
//				ItemSpriteSheet.WEAPON));
//		placeItem(stuff.armor != null ? stuff.armor : new Placeholder(
//				ItemSpriteSheet.ARMOR));
//		placeItem(stuff.misc1 != null ? stuff.misc1 : new Placeholder(
//				ItemSpriteSheet.RING));
//		placeItem(stuff.misc2 != null ? stuff.misc2 : new Placeholder(
//				ItemSpriteSheet.RING));

		boolean backpack = (SB == Dungeon.hero.spellbook);
		if (!backpack) {
			count = nCols;
			col = 0;
			row = 1;
		}

		// Items in the bag
		for (Spell spell : SB.getSpells()) {
			placeItem(spell);
		}

		// Free Space
		while (count - nCols < 24) {
			placeItem(null);
		}

		// Gold
//		if (container == Dungeon.hero.belongings.backpack) {
//			row = nRows - 1;
//			col = nCols - 1;
//			placeItem(new Gold(Dungeon.gold));
//		}
	}

	protected void placeItem(final Item item) {

		int x = col * (SLOT_SIZE + SLOT_MARGIN);
		int y = TITLE_HEIGHT + row * (SLOT_SIZE + SLOT_MARGIN);

		add(new ItemButton(item).setPos(x, y));

		if (++col >= nCols) {
			col = 0;
			row++;
		}

		count++;
	}

	@Override
	public void onMenuPressed() {
		if (listener == null) {
			hide();
		}
	}

	@Override
	public void onBackPressed() {
		if (listener != null) {
			listener.onSelect(null);
		}
		super.onBackPressed();
	}

	@Override
	protected void onClick(Tab tab) {
		hide();
		//GameScene.show(new WndSpellBook(((BagTab) tab).bag, listener, mode, title));
	}

	@Override
	protected int tabHeight() {
		return 20;
	}

	private class BagTab extends Tab {

		private Image icon;
		private BitmapText text;

		private Bag bag;

		public BagTab(BitmapText text) {
			super();

			//this.bag = bag;
			this.text = text;

			//icon = icon();
			add(this.text);
		}

		@Override
		protected void select(boolean value) {
			super.select(value);
			text.am = selected ? 1.0f : 0.6f;
		}

		@Override
		protected void layout() {
			super.layout();

			//icon.copy(icon());
			text.x = x + (width - text.width) / 2;
			text.y = y + (height - text.height) / 2 - 2 - (selected ? 0 : 1);
//			if (!selected && text.y < y + CUT) {
//				RectF frame = icon.frame();
//				frame.top += (y + CUT - icon.y) / icon.texture.height;
//				icon.frame(frame);
//				icon.y = y + CUT;
//			}
		}

//		private Image icon() {
//			if (bag instanceof SeedPouch) {
//				return Icons.get(Icons.SEED_POUCH);
//			} else if (bag instanceof ScrollHolder) {
//				return Icons.get(Icons.SCROLL_HOLDER);
//			} else if (bag instanceof WandHolster) {
//				return Icons.get(Icons.WAND_HOLSTER);
//			} else if (bag instanceof PotionBandolier) {
//				return Icons.get(Icons.POTION_BANDOLIER);
//			} else if (bag instanceof AnkhChain) {
//				return Icons.get(Icons.ANKH_CHAIN);
//			} else if (bag instanceof KeyRing) {
//				return Icons.get(Icons.KEYRING);
//			} else {
//				return Icons.get(Icons.BACKPACK);
//			}
//		}
	}

	private static class Placeholder extends Item {
		{
			name = null;
		}

		public Placeholder(int image) {
			this.image = image;
		}

		@Override
		public boolean isIdentified() {
			return true;
		}

		@Override
		public boolean isEquipped(Hero hero) {
			return true;
		}
	}

	private class ItemButton extends ItemSlot {

		private static final int NORMAL = 0xFF4A4D44;
		private static final int EQUIPPED = 0xFF63665B;

		private Item item;
		private ColorBlock bg;

		public ItemButton(Item item) {

			super(item);

			this.item = item;
			if (item instanceof Gold) {
				bg.visible = false;
			}

			width = height = SLOT_SIZE;
		}

		@Override
		protected void createChildren() {
			bg = new ColorBlock(SLOT_SIZE, SLOT_SIZE, NORMAL);
			add(bg);

			super.createChildren();
		}

		@Override
		protected void layout() {
			bg.x = x;
			bg.y = y;

			super.layout();
		}

//		@Override
//		public void item(Item item) {
//
//			super.item(item);
//			if (item != null) {
//
//				bg.texture(TextureCache.createSolid(item
//						.isEquipped(Dungeon.hero) ? EQUIPPED : NORMAL));
//				if (item.cursed && item.cursedKnown) {
//					bg.ra = +0.2f;
//					bg.ga = -0.1f;
//				} else if (!item.isIdentified()) {
//					bg.ra = 0.1f;
//					bg.ba = 0.1f;
//				}
//
//				if (item.name() == null) {
//					enable(false);
//				} else {
//
//					 int levelLimit = Math.max(5, 5+Math.round(Statistics.deepestFloor/3));
//				     if (Dungeon.hero.heroClass == HeroClass.MAGE){levelLimit++;}
//
//					enable(mode == Mode.FOR_SALE
//							&& (item.price() > 0)
//							&& (!item.isEquipped(Dungeon.hero) || !item.cursed)
//
//							|| mode == Mode.UPGRADEABLE
//							&& ((item.isUpgradable() && item.level<15 && !item.isReinforced())
//									||  item.isUpgradable() && item.isReinforced())
//							|| mode == Mode.UPGRADEDEW
//							&& (item.isUpgradable() && item.level < levelLimit)
//							|| mode == Mode.UPGRADEABLESIMPLE
//							&& item.isUpgradable()
//							|| mode == Mode.ADAMANT
//							&& (item instanceof AdamantArmor || item instanceof AdamantRing || item instanceof AdamantWand || item instanceof AdamantWeapon)
//							|| mode == Mode.REINFORCED
//							&& item.isReinforced()
//							|| mode == Mode.NOTREINFORCED
//							&& (!item.isReinforced() && item.isUpgradable())
//							|| mode == Mode.UNIDENTIFED
//							&& !item.isIdentified()
//							|| mode == Mode.QUICKSLOT
//							&& (item.defaultAction != null)
//							|| mode == Mode.WEAPON
//							&& (item instanceof MeleeWeapon || item instanceof Boomerang || item instanceof JupitersWraith)
//							|| mode == Mode.ARMOR
//							&& (item instanceof Armor)
//							|| mode == Mode.ENCHANTABLE
//							&& (item instanceof MeleeWeapon	|| item instanceof Boomerang || item instanceof Armor)
//							|| mode == Mode.JOURNALPAGES
//							&& (item instanceof JournalPage)
//							|| mode == Mode.WAND && (item instanceof Wand)
//							|| mode == Mode.SEED && (item instanceof Seed)
//							|| mode == Mode.FOOD && (item instanceof Food)
//							|| mode == Mode.POTION && (item instanceof Potion)
//							|| mode == Mode.SCROLL && (item instanceof Scroll)
//							|| mode == Mode.EQUIPMENT
//							&& (item instanceof EquipableItem)
//							|| mode == Mode.ALL);
//				}
//			} else {
//				bg.color(NORMAL);
//			}
//		}

		@Override
		protected void onTouchDown() {
			bg.brightness(1.5f);
			Sample.INSTANCE.play(Assets.SND_CLICK, 0.7f, 0.7f, 1.2f);
		};

		@Override
		protected void onTouchUp() {
			bg.brightness(1.0f);
		};

		@Override
		protected void onClick() {
			if (listener != null) {

				hide();
				listener.onSelect(item);

			} else {

				WndSpellBook.this.add(new WndItem(WndSpellBook.this, item));

			}
		}

		@Override
		protected boolean onLongClick() {
			if (listener == null && item.defaultAction != null) {
				hide();
				Dungeon.quickslot.setSlot(0, item);
				QuickSlotButton.refresh();
				return true;
			} else {
				return false;
			}
		}
	}

	public interface Listener {
		void onSelect(Item item);
	}
}
