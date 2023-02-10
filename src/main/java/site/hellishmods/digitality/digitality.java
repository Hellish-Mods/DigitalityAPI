package site.hellishmods.digitality;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import site.hellishmods.digitality.init.CommonPackInit;
import site.hellishmods.digitality.util.ExceptionHandler;

@Mod(digitality.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = digitality.MOD_ID)
public class digitality
{
    // Consts and vars
    public static final String MOD_ID = "digitality";
    public static final Logger LOGGER = LogManager.getLogger();

    public digitality() {
        MinecraftForge.EVENT_BUS.register(this); // Register mod
    }
    
    @SubscribeEvent
    static void addIcon(FMLLoadCompleteEvent e) { // Add pack.png icon to the virtual data/resource pack
        try {
            if (FMLEnvironment.dist==Dist.CLIENT) // If on client
                FileUtils.copyInputStreamToFile(Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation(MOD_ID, "icon.png")).getInputStream(), CommonPackInit.tmpDir.resolve("pack.png").toFile()); // Copy icon from assets
        } catch (IOException err) {new ExceptionHandler(err);}
    }
}
