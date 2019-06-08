package com.tfar.examplemod;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

import java.util.*;

@Mod.EventBusSubscriber
@Config(modid = ToolHelper.MODID)
@Mod(modid = ToolHelper.MODID, name = ToolHelper.NAME, version = ToolHelper.VERSION, clientSideOnly = true)
public class ToolHelper {

  @Config.Name("show enchantability")
  public static boolean showEnchantability = false;

  @Config.Ignore
  public static final String MODID = "toolhelper";
  @Config.Ignore
  public static final String NAME = "Tool Helper";
  @Config.Ignore
  public static final String VERSION = "1.0";
  @Config.Ignore
  public static final List<ToolData> intToString = new ArrayList<>();

  static {
    intToString.add(new ToolData(TextFormatting.GRAY,"Stone"));
    intToString.add(new ToolData(TextFormatting.WHITE,"Iron"));
    intToString.add(new ToolData(TextFormatting.AQUA,"Diamond"));
    intToString.add(new ToolData(TextFormatting.DARK_PURPLE,"Obsidian"));
    intToString.add(new ToolData(TextFormatting.BLUE,"Cobalt"));
  }

  @SubscribeEvent
  public static void addTooltips(ItemTooltipEvent e) {
    List<String> tooltips = e.getToolTip();
    ItemStack stack = e.getItemStack();
    if (stack.getItem() instanceof ItemTool) {
      Set<String> toolclasses = stack.getItem().getToolClasses(stack);
      if (!toolclasses.isEmpty()) {
        int harvestLevel = stack.getItem().getHarvestLevel(stack, "pickaxe", null, null);
        harvestLevel = Math.min(harvestLevel, intToString.size() - 1);
        if (harvestLevel >= 0 && harvestLevel < 5) tooltips.add("Harvest level: " + intToString.get(harvestLevel).color + intToString.get(harvestLevel).harvest_level);
        else if(harvestLevel >= 5) tooltips.add("Harvest level: " + TextFormatting.GOLD + harvestLevel);
        if (showEnchantability) tooltips.add("Enchantability: " + stack.getItem().getItemEnchantability());
      }
    }
  }
  public static class ToolData {
    public TextFormatting color;
    public String harvest_level;
    ToolData(TextFormatting color,String harvest_level){
      this.color = color;
      this.harvest_level = harvest_level;
    }
  }
}
