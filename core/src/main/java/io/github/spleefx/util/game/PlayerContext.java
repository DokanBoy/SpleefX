/*
 * * Copyright 2020 github.com/ReflxctionDev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.spleefx.util.game;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;

/**
 * The data context which is stored in the player's metadata when they join a game, and is loaded back onto them
 * when they are no longer in it.
 */
public class PlayerContext {

    private static final ItemStack[] EMPTY_ARMOR = new ItemStack[4];

    /**
     * The player's items
     */
    private ItemStack[] items;

    /**
     * The player's armor
     */
    private ItemStack[] armor;

    /**
     * The player's potion effects
     */
    private Collection<PotionEffect> effects;

    /**
     * The player's experience
     */
    private int xp;

    /**
     * The player's last location
     */
    private Location location;

    /**
     * The player's health
     */
    private double health;

    /**
     * The player's hunger
     */
    private int hunger;

    /**
     * The player's gamemode
     */
    private GameMode gameMode;

    private int fireTicks;

    private float exp;

    public boolean allowFlight; // public to load the former allow-flight state for double jumps

    private boolean flying;

    /**
     * Fetches all the data from the player
     *
     * @param player Player to fetch from
     */
    public PlayerContext(Player player) {
        items = player.getInventory().getContents();
        armor = player.getInventory().getArmorContents();
        effects = player.getActivePotionEffects();
        xp = player.getLevel();
        exp = player.getExp();
        location = player.getLocation();
        health = player.getHealth();
        hunger = player.getFoodLevel();
        gameMode = player.getGameMode();
        fireTicks = player.getFireTicks();
        allowFlight = player.getAllowFlight();
        flying = player.isFlying();

        player.getActivePotionEffects().forEach(e -> player.removePotionEffect(e.getType()));
        player.getInventory().clear();
        player.getInventory().setArmorContents(EMPTY_ARMOR);
        player.setLevel(0);
        player.setExp(0);
        player.setFoodLevel(20);
        player.setHealth(20);
        player.setFireTicks(0);
        player.setFlying(false);
    }

    /**
     * Loads into the player
     *
     * @param player Player to load into
     */
    public void load(Player player) {
        player.getInventory().setContents(items);
        player.getInventory().setArmorContents(armor);

        player.getActivePotionEffects().forEach(p -> player.removePotionEffect(p.getType()));
        effects.forEach(player::addPotionEffect);

        player.setLevel(xp);
        player.setExp(exp);
        player.teleport(location);
        player.setHealth(health);
        player.setFoodLevel(hunger);
        player.setGameMode(gameMode);
        player.setFireTicks(fireTicks);
        player.setAllowFlight(allowFlight);
        player.setFlying(flying);
    }
}