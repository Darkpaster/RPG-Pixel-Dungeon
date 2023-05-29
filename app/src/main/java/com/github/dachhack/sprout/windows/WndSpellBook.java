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
import com.github.dachhack.sprout.Statistics;
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
import com.watabou.noosa.Group;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;

import static com.github.dachhack.sprout.scenes.InterlevelScene.mode;

public class WndSpellBook extends WndTabbed {

	protected static final int COLS_P = 4;
	protected static final int COLS_L = 6;

	protected static final int SLOT_SIZE = 26;
	protected static final int SLOT_MARGIN = 1;

	protected static final int TITLE_HEIGHT = 12;

	private Listener listener;
	//private WndSpellBook.Mode mode;
	private String title;

	private ActiveTab activeTab;
	private PassiveTab passiveTab;

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
		activeTab = new ActiveTab(SB);
		add(activeTab);
		passiveTab = new PassiveTab(SB);
		add(passiveTab);
		add(new LabeledTab("Active") {
			@Override
			protected void select(boolean value) {
				super.select(value);
				activeTab.visible = activeTab.active = selected;
			}
		});

		add(new LabeledTab("Passive") {
			@Override
			protected void select(boolean value) {
				super.select(value);
				passiveTab.visible = passiveTab.active = selected;
			}
		});
		//resize(100, (int) Math.max(activeTab.height(), passiveTab.height()));

		layoutTabs();

		select(0);
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

	protected void placeItems(SpellBook SB, boolean active) {

		boolean backpack = (SB == Dungeon.hero.spellbook);
		if (!backpack) {
			count = nCols;
			col = 0;
			row = 1;
		}

		// Items in the bag
		for (Spell spell : SB.getSpells()) {
			if(active == spell.isActive()) {
				placeItem(spell);
			}
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
		tab.select(true);
		//GameScene.show(new WndBag(((WndBag.BagTab) tab).bag, listener, mode, title));
	}

	@Override
	protected int tabHeight() {
		return 20;
	}

	private class ActiveTab extends Group{


		public ActiveTab(SpellBook SB){
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

			placeItems(SB, true);

			resize(slotsWidth, slotsHeight + TITLE_HEIGHT);
		}
	}

	protected class PassiveTab extends Group {

		public PassiveTab(SpellBook SB){
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

			placeItems(SB, false);

			resize(slotsWidth, slotsHeight + TITLE_HEIGHT);
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
			GLog.n("onClick item");
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
