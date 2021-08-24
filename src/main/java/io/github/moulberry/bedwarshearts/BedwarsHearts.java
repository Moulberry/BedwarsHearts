package io.github.moulberry.bedwarshearts;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Pattern;

@Mod(modid = BedwarsHearts.MODID, version = BedwarsHearts.VERSION, clientSideOnly = true)
public class BedwarsHearts {
    public static final String MODID = "bedwarshearts";
    public static final String VERSION = "1.0-REL";

    public static boolean enabled = true;
    public static final Pattern BED_LOSS_PATTERN = Pattern.compile("^\u00a7r\u00a7f\u00a7lBED DESTRUCTION > \u00a7r\u00a77Your Bed .*");

    public static boolean useHardcoreHearts = false;

    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);

        ClientCommandHandler.instance.registerCommand(new SimpleCommand("bedwarshearts", new SimpleCommand.ProcessCommandRunnable() {
            @Override
            public void processCommand(ICommandSender sender, String[] args) {
                if(args.length != 1) {
                    sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"Invalid usage: /bedwarshearts on/off"));
                    return;
                }
                if(args[0].equalsIgnoreCase("on")) {
                    enabled = true;
                    sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN+"Enabled BedwarsHearts"));
                } else if(args[0].equalsIgnoreCase("off")) {
                    enabled = false;
                    sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"Disabled BedwarsHearts"));
                } else {
                    sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"Invalid usage: /bedwarshearts on/off"));
                }
            }
        }));
    }

    @SubscribeEvent
    public void onWorldSwitch(WorldEvent.Unload event) {
        useHardcoreHearts = false;
    }

    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {
        if(BED_LOSS_PATTERN.matcher(event.message.getFormattedText()).matches()) {
            useHardcoreHearts = true;
        }
    }

}
