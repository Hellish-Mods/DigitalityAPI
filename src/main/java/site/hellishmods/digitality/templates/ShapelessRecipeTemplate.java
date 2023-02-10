package site.hellishmods.digitality.templates;

import java.util.ArrayList;

// Template for shapeless recipe jsons
public class ShapelessRecipeTemplate extends RecipeTemplate {
    public ArrayList<ItemTemplate> ingredients = new ArrayList<>();

    public ShapelessRecipeTemplate() {
        type = "minecraft:crafting_shapeless";
    }
}
