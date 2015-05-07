package com.kbuffer.khaoticpixeldungeon.items;

import com.kbuffer.khaoticpixeldungeon.actors.Char;
import com.kbuffer.khaoticpixeldungeon.items.EquipableItem;

/**
 * Created by Evan on 24/08/2014.
 */
public abstract class KindofMisc extends EquipableItem {

    public abstract void activate(Char ch);
}
