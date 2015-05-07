package com.kbuffer.khaoticpixeldungeon.items.bags;

import com.kbuffer.khaoticpixeldungeon.items.Item;
import com.kbuffer.khaoticpixeldungeon.items.potions.Potion;
import com.kbuffer.khaoticpixeldungeon.sprites.ItemSpriteSheet;

/**
 * Created by debenhame on 05/02/2015.
 */
public class PotionBandolier extends Bag {

	{
		name = "potion bandolier";
		image = ItemSpriteSheet.BANDOLIER;

		size = 12;
	}

	@Override
	public boolean grab( Item item ) {
		return item instanceof Potion;
	}

	@Override
	public int price() {
		return 50;
	}

	@Override
	public String info() {
		return
			"This thick bandolier fits around your chest like a sash, it has many small vials to hold your potions.\n\n" +
			"The vials are made of tempered glass, and should be quite resistant to the cold.";
	}
}
