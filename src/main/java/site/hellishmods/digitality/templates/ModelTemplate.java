package site.hellishmods.digitality.templates;

import com.google.gson.JsonObject;

// Abstract template for general model jsons
public abstract class ModelTemplate {
    protected String parent;
    public JsonObject textures = new JsonObject();
}
