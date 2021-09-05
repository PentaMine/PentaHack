package net.minecraft.PentaHack.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Command {

    public String name, description, syntax;
    public List<String> alaises = new ArrayList<String>();

    public Command(String name, String description, String syntax, String... alaises ) {
        this.name = name;
        this.description = description;
        this.syntax = syntax;
        this.alaises = Arrays.asList(alaises);
    }

    public abstract void onCommand(String[] args, String command);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSyntax() {
        return syntax;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    public List<String> getAlaises() {
        return alaises;
    }

    public void setAlaises(List<String> alaises) {
        this.alaises = alaises;
    }
}
