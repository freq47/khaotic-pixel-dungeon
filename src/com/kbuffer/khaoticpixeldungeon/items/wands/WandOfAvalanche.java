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
package com.kbuffer.khaoticpixeldungeon.items.wands;

import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.kbuffer.khaoticpixeldungeon.Assets;
import com.kbuffer.khaoticpixeldungeon.Dungeon;
import com.kbuffer.khaoticpixeldungeon.ResultDescriptions;
import com.kbuffer.khaoticpixeldungeon.actors.Actor;
import com.kbuffer.khaoticpixeldungeon.actors.Char;
import com.kbuffer.khaoticpixeldungeon.actors.buffs.Buff;
import com.kbuffer.khaoticpixeldungeon.actors.buffs.Paralysis;
import com.kbuffer.khaoticpixeldungeon.effects.CellEmitter;
import com.kbuffer.khaoticpixeldungeon.effects.MagicMissile;
import com.kbuffer.khaoticpixeldungeon.effects.Speck;
import com.kbuffer.khaoticpixeldungeon.levels.Level;
import com.kbuffer.khaoticpixeldungeon.mechanics.Ballistica;
import com.kbuffer.khaoticpixeldungeon.utils.BArray;
import com.kbuffer.khaoticpixeldungeon.utils.GLog;
import com.kbuffer.khaoticpixeldungeon.utils.Utils;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class WandOfAvalanche extends Wand {

	{
		name = "Wand of Avalanche";
		hitChars = false;
	}
	
	@Override
	protected void onZap( int cell ) {
		
		Sample.INSTANCE.play( Assets.SND_ROCKS );
		
		int level = level();
		
		Ballistica.distance = Math.min( Ballistica.distance, 8 + level );
		
		int size = 1 + level / 3;
		PathFinder.buildDistanceMap( cell, BArray.not( Level.solid, null ), size );
		
		for (int i=0; i < Level.LENGTH; i++) {
			
			int d = PathFinder.distance[i];
			
			if (d < Integer.MAX_VALUE) {
				
				Char ch = Actor.findChar( i ); 
				if (ch != null) {
					
					ch.sprite.flash();
					
					ch.damage( Random.Int( 2, 6 + (size - d) * 2 ), this );
					
					if (ch.isAlive() && Random.Int( 2 + d ) == 0) {
						Buff.prolong( ch, Paralysis.class, Random.IntRange( 2, 6 ) );
					}
				}

				CellEmitter.get( i ).start( Speck.factory( Speck.ROCK ), 0.07f, 3 + (size - d) );
				Camera.main.shake( 3, 0.07f * (3 + (size - d)) );
			}
		}
		
		if (!curUser.isAlive()) {
			Dungeon.fail( Utils.format( ResultDescriptions.ITEM, name ) );
			GLog.n( "You killed yourself with your own Wand of Avalanche..." );
		}
	}
	
	protected void fx( int cell, Callback callback ) {
		MagicMissile.earth( curUser.sprite.parent, curUser.pos, cell, callback );
		Sample.INSTANCE.play( Assets.SND_ZAP );
	}
	
	@Override
	public String desc() {
		return
			"When a discharge of this wand hits a wall (or any other solid obstacle) it causes " +
			"an avalanche of stones, damaging and stunning all creatures in the affected area.";
	}
}
