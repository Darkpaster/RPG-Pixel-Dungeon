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
package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.actors.hero.Hero;
import com.github.dachhack.sprout.items.Item;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

import java.util.ArrayList;
import java.util.Objects;

public class Spell extends Item{

	private static final String SPELL_LVL = "spell lvl";
	protected static final String AC_USE = "USE";
	protected boolean active;
	protected boolean ready;

	protected int mp_cost = 0;
	protected int rage_cost = 0;
	protected int energy_cost = 0;
	protected int cd;
	protected float current_cd;

	protected int lvl;

	{
		name = "spell";
		lvl = 1;
		cd = 0;
		current_cd = 0;
		image = ItemSpriteSheet.JOURNAL_PAGE;
		stackable = false;
		ready = true;
		active = false;
		unique = true;
	}

	public boolean isReady(){
		return ready;
	}
	public boolean isActive(){
		return active;
	}

	public void tick(float time){
		if(current_cd <= 0 && !ready){
			ready = true;
			GLog.p(name + " is ready!");
			return;
		}
		if(current_cd <= 0){
			return;
		}
		current_cd -= time;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = new ArrayList<>();

		int i2 = 0;
		int[] arr = {mp_cost, energy_cost, rage_cost};
		int[] arr2 = {Dungeon.hero.MP, Dungeon.hero.energy, Dungeon.hero.rage};
		for(int i = 0; i < arr.length; i++){
			if(arr[i] - (arr[0] + arr[1] + arr[2]) == 0){
				i2 = i;
			}
		}

		if(active && arr2[i2] >= arr[i2] && ready){
			actions.add(AC_USE);
		}

		return actions;
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);

		bundle.put(SPELL_LVL, lvl);
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		lvl = bundle.getInt(SPELL_LVL);
	}

	@Override
	public String name() {
		return name + " (" + (ready ? "ready" : current_cd) + ")";
	}

	private String spellCost(){
		String str;
		if(energy_cost > 0){
			str = energy_cost + " energy";
		}else if(rage_cost > 0){
			str = rage_cost + " rage";
		}else if(mp_cost > 0){
			str = mp_cost + " mana";
		}else{
			str = "0";
		}
		return str;
	}

	@Override
	public void execute(Hero hero, String action) {

		if (Objects.equals(action, AC_USE)) {
			onUse();
			ready = false;
			current_cd = cd;
		}
	}

	protected void onUse() {
		Dungeon.hero.energy -= energy_cost;
		Dungeon.hero.rage -= rage_cost;
		Dungeon.hero.MP -= mp_cost;
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public int price() {
		return 0;
	}


		public String info() {
		return "A Spell Page." + skillInfo();
	}

	protected String skillInfo(){
		return "\n\nCost: " + spellCost() + "\nCD: " + cd + " turns";
	}
}
