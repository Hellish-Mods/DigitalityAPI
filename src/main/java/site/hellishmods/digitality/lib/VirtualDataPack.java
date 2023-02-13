package site.hellishmods.digitality.lib;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.google.gson.stream.JsonReader;

import site.hellishmods.digitality.init.CommonPackInit;
import site.hellishmods.digitality.templates.ItemTemplate;
import site.hellishmods.digitality.templates.ShapelessRecipeTemplate;
import site.hellishmods.digitality.templates.TagTemplate;
import site.hellishmods.digitality.util.ExceptionHandler;

public class VirtualDataPack extends VirtualCommonPack {
    public VirtualDataPack(String modid) {
        super(modid);
        dir = CommonPackInit.dataDir.toPath().resolve(modid); // Dir = data/<mod_id>
    }

    // Shapeless crafting recipe
    public void shapelessRecipe(String id, ItemTemplate result, ArrayList<String> ingredients) {
        ShapelessRecipeTemplate recipe = new ShapelessRecipeTemplate(); // New recipe template
        recipe.result = result; // Set the result to result
        for(String ingredient : ingredients) recipe.ingredients.add(new ItemTemplate(ingredient)); // Add ingredients
    
        newJson(dir.resolve("recipes").resolve(id+".json").toFile(), recipe); // Write json
    }
    public void shapelessRecipe(String id, ItemTemplate result, String... ingredients) {shapelessRecipe(id, result, new ArrayList<>(Arrays.asList(ingredients)));}

    // Add item tags
    public void itemTag(String tag, String... newItems) {
        try {
            // Get tag file
            File tagfile = dir.resolve("tags").resolve("items").resolve(tag+".json").toFile();
            tagfile.getParentFile().mkdirs();

            TagTemplate tagJson;
            if (tagfile.exists()) tagJson = GSON.fromJson(new JsonReader(new FileReader(tagfile)), TagTemplate.class); // If it already exists, load it
            else { // If not
                tagfile.createNewFile(); // Create new tag file
                tagJson = new TagTemplate(); // New tag template
            }
            tagJson.values.addAll(Arrays.asList(newItems)); // Add all items

            tagJson.values = new ArrayList<>(tagJson.values.stream().distinct().collect(Collectors.toList())); // Leave only unique ones

            newJson(tagfile, tagJson); // Write json
        } 
        catch (IOException e) {new ExceptionHandler(e);}
    }
}
