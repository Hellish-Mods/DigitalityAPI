package site.hellishmods.digitality.templates;

// Template for items inside recipe jsons
public class ItemTemplate {
    public String item;
    public int count = 1;

    public ItemTemplate(String itemname, int amount) {
        item = itemname;
        count = amount;
    }
    public ItemTemplate(String itemname) {item = itemname;}
}
