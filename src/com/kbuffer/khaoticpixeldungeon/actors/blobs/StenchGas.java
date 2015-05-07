package com.kbuffer.khaoticpixeldungeon.actors.blobs;

import com.kbuffer.khaoticpixeldungeon.actors.Actor;
import com.kbuffer.khaoticpixeldungeon.actors.Char;
import com.kbuffer.khaoticpixeldungeon.actors.buffs.Buff;
import com.kbuffer.khaoticpixeldungeon.actors.buffs.Paralysis;
import com.kbuffer.khaoticpixeldungeon.effects.BlobEmitter;
import com.kbuffer.khaoticpixeldungeon.effects.Speck;

/**
 * Created by debenhame on 08/10/2014.
 */
public class StenchGas extends Blob {

    @Override
    protected void evolve() {
        super.evolve();

        Char ch;
        for (int i=0; i < LENGTH; i++) {
            if (cur[i] > 0 && (ch = Actor.findChar(i)) != null) {
                if (!ch.immunities().contains(this.getClass()))
                    Buff.prolong( ch, Paralysis.class, Paralysis.duration( ch )/5 );
            }
        }
    }

    @Override
    public void use( BlobEmitter emitter ) {
        super.use( emitter );

        emitter.pour( Speck.factory(Speck.STENCH), 0.6f );
    }

    @Override
    public String tileDesc() {
        return "A cloud of fetid stench is swirling here.";
    }
}
