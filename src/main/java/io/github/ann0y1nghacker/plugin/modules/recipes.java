package io.github.ann0y1nghacker.plugin.modules;

import io.github.ann0y1nghacker.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public class recipes {
    Plugin plugin;

    public recipes(Plugin plugin) {
        this.plugin = plugin;
        Bukkit.addRecipe(oakSlabsToBlocks());
    }

    public ShapelessRecipe oakSlabsToBlocks() {
        ItemStack item = new ItemStack(Material.OAK_PLANKS); NamespacedKey key = new NamespacedKey(plugin, "oak_planks"); ShapelessRecipe recipe = new ShapelessRecipe(key, item);
        recipe.addIngredient(2, Material.OAK_SLAB); return recipe;
    }

    public ShapelessRecipe spruceSlabsToBlocks() {
        ItemStack item = new ItemStack(Material.SPRUCE_PLANKS); NamespacedKey key = new NamespacedKey(plugin, "spruce_planks"); ShapelessRecipe recipe = new ShapelessRecipe(key, item);
        recipe.addIngredient(2, Material.SPRUCE_SLAB); return recipe;
    }

    public ShapelessRecipe birchSlabsToBlocks() {
        ItemStack item = new ItemStack(Material.BIRCH_PLANKS); NamespacedKey key = new NamespacedKey(plugin, "birch_planks"); ShapelessRecipe recipe = new ShapelessRecipe(key, item);
        recipe.addIngredient(2, Material.BIRCH_SLAB); return recipe;
    }

    public ShapelessRecipe jungleSlabsToBlocks() {
        ItemStack item = new ItemStack(Material.JUNGLE_PLANKS); NamespacedKey key = new NamespacedKey(plugin, "jungle_planks"); ShapelessRecipe recipe = new ShapelessRecipe(key, item);
        recipe.addIngredient(2, Material.JUNGLE_SLAB); return recipe;
    }

    public ShapelessRecipe acaciaSlabsToBlocks() {
        ItemStack item = new ItemStack(Material.ACACIA_PLANKS); NamespacedKey key = new NamespacedKey(plugin, "acacia_planks"); ShapelessRecipe recipe = new ShapelessRecipe(key, item);
        recipe.addIngredient(2, Material.ACACIA_SLAB); return recipe;
    }

    public ShapelessRecipe darkoakSlabsToBlocks() {
        ItemStack item = new ItemStack(Material.DARK_OAK_PLANKS); NamespacedKey key = new NamespacedKey(plugin, "dark_oak_planks"); ShapelessRecipe recipe = new ShapelessRecipe(key, item);
        recipe.addIngredient(2, Material.DARK_OAK_SLAB); return recipe;
    }

    public ShapelessRecipe crimsonSlabsToBlocks() {
        ItemStack item = new ItemStack(Material.CRIMSON_PLANKS); NamespacedKey key = new NamespacedKey(plugin, "crimson_planks"); ShapelessRecipe recipe = new ShapelessRecipe(key, item);
        recipe.addIngredient(2, Material.CRIMSON_SLAB); return recipe;
    }

    public ShapelessRecipe warpedSlabsToBlocks() {
        ItemStack item = new ItemStack(Material.WARPED_PLANKS); NamespacedKey key = new NamespacedKey(plugin, "warped_planks"); ShapelessRecipe recipe = new ShapelessRecipe(key, item);
        recipe.addIngredient(2, Material.WARPED_SLAB); return recipe;
    }

    public ShapelessRecipe stoneSlabsToBlocks() {
        ItemStack item = new ItemStack(Material.STONE); NamespacedKey key = new NamespacedKey(plugin, "stone"); ShapelessRecipe recipe = new ShapelessRecipe(key, item);
        recipe.addIngredient(2, Material.STONE_SLAB); return recipe;
    }
}
