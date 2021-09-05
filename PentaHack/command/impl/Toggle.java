package net.minecraft.PentaHack.command.impl;

import net.minecraft.PentaHack.Client;
import net.minecraft.PentaHack.command.Command;
import net.minecraft.PentaHack.modules.Module;
import org.newdawn.slick.openal.MODSound;

public class Toggle extends Command {

    public Toggle(){
        super("Toggle", "Toggles a module by name", "toggle <name>", "t");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length > 0){
            String moduleName = args[0];
            boolean foundModule = false;


            for(Module module : Client.modules){
                if (module.name.equalsIgnoreCase(moduleName)){
                    module.toggle();

                    Client.addChatMessage((module.toggled ? "Enabled" : "Disabled") + " " + module.name);

                    foundModule = true;
                    break;
                }

            }
            if (!foundModule){
                Client.addChatMessage("Cloud not find module.");
            }
        }
    }
}
