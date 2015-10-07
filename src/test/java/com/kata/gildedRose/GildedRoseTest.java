package com.kata.gildedRose;

import com.google.common.io.Resources;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnitParamsRunner.class)
public class GildedRoseTest {

    @Test
    public void should_decrease_sellIn_when_day_passes() {
        Item testedItem = createItemAndPassOneDay("foo", 1, 1);
        assertEquals(0, testedItem.sellIn);
    }

    @Test
    public void should_decrease_quality_when_day_passes() {
        Item testedItem = createItemAndPassOneDay("foo", 0, 1);
        assertEquals(0, testedItem.quality);
    }

    @Test
    public void should_decrease_quality_twice_as_fast_when_sell_date_has_passed() {
        Item testedItem = createItemAndPassOneDay("foo", 0, 2);
        assertEquals(0, testedItem.quality);
    }

    @Test
    @Parameters({"foo",
            ItemFactory.CONJURED,
            ItemFactory.BACKSTAGE_PASSES,
            ItemFactory.AGED_BRIE})
    public void should_never_have_negative_quality(String objectName) {
        Item testedItem = createItemAndPassOneDay(objectName, 1, 0);
        assertTrue(testedItem.quality >= 0);
    }

    @Test
    @Parameters({"foo",
            ItemFactory.CONJURED,
            ItemFactory.BACKSTAGE_PASSES,
            ItemFactory.AGED_BRIE})
    public void should_never_have_negative_quality_after_sell_date_passed(String objectName) {
        Item testedItem = createItemAndPassOneDay(objectName, 0, 0);
        assertTrue(testedItem.quality >= 0);
    }

    @Test
    public void should_increase_aged_brie_quality_when_day_passes() {
        Item testedItem = createItemAndPassOneDay("Aged Brie", 1, 0);
        assertEquals(1, testedItem.quality);
    }

    @Test
    public void should_increase_aged_brie_quality_twice_as_fast_when_day_passes_after_sell_date() {
        Item testedItem = createItemAndPassOneDay("Aged Brie", 0, 0);
        assertEquals(2, testedItem.quality);
    }

    @Test
    @Parameters({"foo",
            ItemFactory.CONJURED,
            ItemFactory.BACKSTAGE_PASSES,
            ItemFactory.AGED_BRIE})
    public void should_never_increase_quality_above_50(String objectName) {
        Item testedItem = createItemAndPassOneDay(objectName, 1, 50);
        assertTrue(testedItem.quality <= 50);
    }

    @Test
    public void should_never_change_sulfuras_sellIn() {
        Item testedItem = createItemAndPassOneDay("Sulfuras, Hand of Ragnaros", 1, 80);
        assertEquals(1, testedItem.sellIn);
    }

    @Test
    public void should_never_change_sulfuras_quality() {
        Item testedItem = createItemAndPassOneDay("Sulfuras, Hand of Ragnaros", 1, 80);
        assertEquals(80, testedItem.quality);
    }

    @Test
    @Parameters({"11", "12", "33"})
    public void should_increase_backstage_pass_quality_when_day_passes(int days_left) {
        Item testedItem = createItemAndPassOneDay("Backstage passes to a TAFKAL80ETC concert", days_left, 0);
        assertEquals(1, testedItem.quality);
    }

    @Test
    @Parameters({"10", "6", "9"})
    public void should_increase_backstage_pass_quality_by_2_10_to_6_days_before_concert(int days_left) {
        Item testedItem = createItemAndPassOneDay("Backstage passes to a TAFKAL80ETC concert", days_left, 0);
        assertEquals(2, testedItem.quality);
    }

    @Test
    @Parameters({"5", "1", "3"})
    public void should_increase_backstage_pass_quality_by_3_5_days_before_concert(int days_left) {
        Item testedItem = createItemAndPassOneDay("Backstage passes to a TAFKAL80ETC concert", days_left, 0);
        assertEquals(3, testedItem.quality);
    }

    @Test
    public void should_drop_backstage_pass_to_zero_after_concert_date() {
        Item testedItem = createItemAndPassOneDay("Backstage passes to a TAFKAL80ETC concert", 0, 40);
        assertEquals(0, testedItem.quality);
    }

    @Test
    public void should_decrease_quality_of_conjured_items_twice_as_fast_before_sell_date() {
        Item testedItem = createItemAndPassOneDay("Conjured Mana Cake", 1, 3);
        assertEquals(1, testedItem.quality);
    }

    @Test
    public void should_decrease_quality_of_conjured_items_twice_as_fast_after_sell_date() {
        Item testedItem = createItemAndPassOneDay("Conjured Mana Cake", 0, 5);
        assertEquals(1, testedItem.quality);
    }

    @Test
    public void should_keep_golden_behaviour() throws IOException {
        //Sending console output to file
        PrintStream console = System.out;
        File file = new File ("out");
        FileOutputStream fos = new FileOutputStream(file);
        PrintStream ps = new PrintStream(fos);
        System.setOut(ps);

        //Get the golden behaviour
        final Iterator<String> golden_behaviour =
                Files.lines(Paths.get(Resources.getResource("golden_behaviour").getPath())).iterator();
        TexttestFixture.main(new String[]{});

        //Assert compare file streams
        Files.lines(Paths.get(file.getPath()))
                .forEachOrdered(s -> assertEquals(golden_behaviour.next(), s));

        //restoring console output
        System.setOut(console);
    }

    private Item createItemAndPassOneDay(String name, int sellIn, int quality) {
        Item item = ItemFactory.newItem(name, sellIn, quality);
        Item[] items = {item};
        GildedRose app = new GildedRose(items);
        app.dailyUpdate();
        return item;
    }
}
