package com.ernestas.entities;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    @Test
    public void testPlayerHealth() {
        Player player = new Player();
        int maxHp = player.getMaxHealth();

        player.heal(maxHp);

        assertThat(player.getHealth(), is(player.getMaxHealth()));

        player.damage(player.getHealth() + 1);
        assertThat(player.getHealth(), is(0));
        assertTrue(player.isDead());

        player.heal(5);
        assertThat(player.getHealth(), is(5));

        player.setHealth(3);
        assertThat(player.getHealth(), is(3));
    }

    @Test
    public void testPlayerPosition() {
        Player player = new Player();

        float x = player.getX();
        float y = player.getY();
        float z = player.getZ();

        player.move(10, 10);
        assertThat(player.getX(), is(x + 10));
        assertThat(player.getY(), is(y));
        assertThat(player.getZ(), is(z + 10));

        player.setPosition(100, 100, 100);
        assertThat(player.getX(), is(100f));
        assertThat(player.getY(), is(100f));
        assertThat(player.getZ(), is(100f));

        player.translate(1, 2, 3);
        assertThat(player.getX(), is(101f));
        assertThat(player.getY(), is(102f));
        assertThat(player.getZ(), is(103f));
    }

}