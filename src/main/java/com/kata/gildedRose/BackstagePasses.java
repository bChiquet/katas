package com.kata.gildedRose;

public class BackstagePasses extends Item {
    public BackstagePasses(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    /**
     * Backstage passes follow special rules.
     */
    @Override
    public void qualityDecay() {
        if (sellIn > 10) {
            quality +=1;
        }
        else if (sellIn > 5 ) {
            quality +=2;
        }
        else if (sellIn>0) {
            quality+=3;
        }
        else quality = 0;
        if (quality > 50) {
            quality = 50;
        }
    }

}
