package com.kata.gildedRose;

import java.util.Arrays;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void dailyUpdate() {
        Arrays.stream(items)
                .forEach(Item::dailyUpdate);
    }
}
