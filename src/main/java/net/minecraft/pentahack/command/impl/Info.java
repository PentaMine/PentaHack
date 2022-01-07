package net.minecraft.pentahack.command.impl;

import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.command.Command;
import net.minecraft.pentahack.modules.Module;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;

public class Info extends Command {

    public Info() {
        super("Info", "Displays client info", "info", "i");
    }

    @Override
    public void onCommand(String[] args, String command) {
        Client.addChatMessage(Client.name + " " + Client.version + " by " + Arrays.toString(Client.devs));
    }
}
