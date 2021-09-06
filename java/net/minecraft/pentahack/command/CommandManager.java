package net.minecraft.pentahack.command;

import net.minecraft.pentahack.command.impl.Toggle;
import net.minecraft.pentahack.events.listeners.EventChat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {

    public List<Command> commands = new ArrayList<Command>();
    public String prefix = ".";

    public CommandManager(){
        setup();
    }

    public void setup() {
        commands.add(new Toggle());
    }

    public void handleChat(EventChat event) {
        String message = event.getMessage();

        if(!message.startsWith(prefix)){
            return;
        }

        event.setCacelled(true);

        message = message.substring(prefix.length());

        if (message.split(" ").length > 0) {
            String commandName = message.split(" ")[0];

            for (Command c :commands){
                if (c.alaises.contains(commandName)){
                    c.onCommand(Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length), message);
                }
            }
        }
    }
}
