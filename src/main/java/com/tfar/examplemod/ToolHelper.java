package com.tfar.examplemod;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber
@Mod(value = ToolHelper.MODID)
public class ToolHelper {

  public static final String MODID = "toolhelper";

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
    List<ITextComponent> tooltips = e.getToolTip();
    ItemStack stack = e.getItemStack();
    if (stack.getItem() instanceof ToolItem) {
      Set<ToolType> toolclasses = stack.getItem().getToolTypes(stack);
      if (!toolclasses.isEmpty()) {
        int harvestLevel = stack.getItem().getHarvestLevel(stack, ToolType.PICKAXE, null, null);
        harvestLevel = Math.min(harvestLevel, intToString.size() - 1);
        if (harvestLevel >= 0 && harvestLevel < 5) tooltips.add(new StringTextComponent("Harvest level: " + intToString.get(harvestLevel).color + intToString.get(harvestLevel).harvest_level));
        else if(harvestLevel >= 5) tooltips.add(new StringTextComponent(
        "Harvest level: " + TextFormatting.GOLD + harvestLevel));
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
