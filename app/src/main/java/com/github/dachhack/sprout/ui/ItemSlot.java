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
package com.github.dachhack.sprout.ui;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.items.Item;
import com.github.dachhack.sprout.items.armor.Armor;
import com.github.dachhack.sprout.items.keys.Key;
import com.github.dachhack.sprout.items.keys.SkeletonKey;
import com.github.dachhack.sprout.items.weapon.Weapon;
import com.github.dachhack.sprout.items.weapon.melee.MeleeWeapon;
import com.github.dachhack.sprout.scenes.PixelScene;
import com.github.dachhack.sprout.sprites.ItemSprite;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.Utils;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.ui.Button;

public class ItemSlot extends Button {

	public static final int DEGRADED = 0xFF4444;
	public static final int UPGRADED = 0x44FF44;
	public static final int WARNING = 0xFF8800;

	private static final float ENABLED = 1.0f;
	private static final float DISABLED = 0.3f;

	protected ItemSprite icon;
	protected BitmapText topLeft;
	protected BitmapText topRight;
	protected BitmapText bottomRight;

	private static final String TXT_STRENGTH = ":%d";
	private static final String TXT_TYPICAL_STR = "%d?";
	private static final String TXT_KEY_DEPTH = "\u007F%d";

	private static final String TXT_LEVEL = "%+d";
	private static final String TXT_CURSED = "";// "-";

	// Special "virtual items"
	public static final Item CHEST = new Item() {
		@Override
		public int image() {
			return ItemSpriteSheet.CHEST;
		};
	};
	public static final Item LOCKED_CHEST = new Item() {
		@Override
		public int image() {
			return ItemSpriteSheet.LOCKED_CHEST;
		};
	};
	public static final Item CRYSTAL_CHEST = new Item() {
		@Override
		public int image() {
			return ItemSpriteSheet.CRYSTAL_CHEST;
		};
	};
	public static final Item TOMB = new Item() {
		@Override
		public int image() {
			return ItemSpriteSheet.TOMB;
		};
	};
	public static final Item SKELETON = new Item() {
		@Override
		public int image() {
			return ItemSpriteSheet.BONES;
		};
	};
	public static final Item REMAINS = new Item() {
		@Override
		public int image() {
			return ItemSpriteSheet.REMAINS;
		};
	};

	public ItemSlot() {
		super();
	}

	public ItemSlot(Item item) {
		this();
		item(item);
	}

	@Override
	protected void createChildren() {

		super.createChildren();

		icon = new ItemSprite();
		add(icon);

		topLeft = new BitmapText(PixelScene.font1x);
		add(topLeft);

		topRight = new BitmapText(PixelScene.font1x);
		add(topRight);

		bottomRight = new BitmapText(PixelScene.font1x);
		add(bottomRight);
	}

	@Override
	protected void layout() {
		super.layout();

		icon.x = x + (width - icon.width) / 2;
		icon.y = y + (height - icon.height) / 2;

		if (topLeft != null) {
			topLeft.x = x;
			topLeft.y = y;
		}

		if (topRight != null) {
			topRight.x = x + (width - topRight.width());
			topRight.y = y;
		}

		if (bottomRight != null) {
			bottomRight.x = x + (width - bottomRight.width());
			bottomRight.y = y + (height - bottomRight.height());
		}
	}

	public void item(Item item) {
		if (item == null) {

			active = false;
			icon.visible = topLeft.visible = topRight.visible = bottomRight.visible = false;

		} else {

			active = true;
			icon.visible = topLeft.visible = topRight.visible = bottomRight.visible = true;

			icon.view(item.image(), item.glowing());

			topLeft.text(item.status());

			boolean isArmor = item instanceof Armor;
			boolean isWeapon = item instanceof Weapon;
			if (isArmor || isWeapon) {

				if (item.levelKnown
						|| (isWeapon && !(item instanceof MeleeWeapon))) {

					int str = isArmor ? ((Armor) item).STR
							: ((Weapon) item).STR;
					topRight.text(Utils.format(TXT_STRENGTH, str));
					if (str > Dungeon.hero.STR()) {
						topRight.hardlight(DEGRADED);
					} else {
						topRight.resetColor();
					}

				} else {

					topRight.text(Utils.format(TXT_TYPICAL_STR,
							isArmor ? ((Armor) item).typicalSTR()
									: ((MeleeWeapon) item).typicalSTR()));
					topRight.hardlight(WARNING);

				}
				topRight.measure();

			} else if (item instanceof Key && !(item instanceof SkeletonKey)) {
				topRight.text(Utils.format(TXT_KEY_DEPTH, ((Key) item).depth));
				topRight.measure();
			} else {

				topRight.text(null);

			}

			int level = item.visiblyUpgraded();

			if (level != 0) {
				bottomRight.text(item.levelKnown ? Utils.format(TXT_LEVEL,
						level) : TXT_CURSED);
				bottomRight.measure();
				bottomRight.hardlight(level > 0 ? UPGRADED : DEGRADED);
			} else {
				bottomRight.text(null);
			}

			layout();
		}
	}

	public void enable(boolean value) {

		active = value;

		float alpha = value ? ENABLED : DISABLED;
		icon.alpha(alpha);
		topLeft.alpha(alpha);
		topRight.alpha(alpha);
		bottomRight.alpha(alpha);
	}

	public void showParams(boolean value) {
		if (value) {
			add(topRight);
			add(bottomRight);
		} else {
			remove(topRight);
			remove(bottomRight);
		}
	}
}
