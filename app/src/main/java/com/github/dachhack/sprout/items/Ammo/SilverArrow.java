package com.github.dachhack.sprout.items.Ammo;

import com.github.dachhack.sprout.sprites.ItemSpriteSheet;

/**
 * Created by matthewporritt on 8/12/17.
 */

public class SilverArrow extends Ammo {

    {
        name = "silver arrow";
        //image = ItemSpriteSheet.SILVERARROW;
        image = ItemSpriteSheet.DART;

        MIN = 0;
        MAX = 1;

        DIE = 2;
        SIDES = 4;

        speedFactor = 1;

        imbue = imbueFamily.COLD;
    }


    public SilverArrow() {
        this(1);
    }

    public SilverArrow(int number) {
        super();
        quantity = number;
    }

}
