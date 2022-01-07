package net.minecraft.pentahack.command.impl;

import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.command.Command;
import net.minecraft.pentahack.modules.Module;
import org.lwjgl.input.Keyboard;

public class Bind extends Command {

    public Bind() {
        super("Bind", "Binds a module by name", "bind <name> <key> | clear", "b");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length == 2) {
            String moduleName = args[0];
            String keyName = args[1];

            boolean foundModule = false;

            for (Module m : Client.modules) {
                if (m.name.equalsIgnoreCase(moduleName)) {
                    m.keyCode = Keyboard.getKeyIndex(keyName.toUpperCase());
                    Client.addChatMessage(String.format("Bound %s to %s", moduleName, keyName));
                    foundModule = true;
                    break;
                }
            }
            if (!foundModule){
                Client.addChatMessage("Cloud not find module.");
            }


        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("clear")) {
                for (Module m : Client.modules) {
                    m.keyCode = (Keyboard.KEY_NONE);

                }
            }
            Client.addChatMessage("Cleared all binds for module.");
        }

    }
}
