package net.minecraft.pentahack.util;

import net.minecraft.client.Minecraft;
import net.minecraft.pentahack.Client;
import net.minecraft.pentahack.gui.hud.HudComponent;
import net.minecraft.pentahack.modules.Module;
import net.minecraft.pentahack.settings.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class JsonUtil {
    public static void saveProperties() throws IOException {
        File file = new File("PentaHackConfig.json");
        FileWriter fw = new FileWriter(file);

        StringBuilder json = new StringBuilder("{");

        for (int i = 0; i < Client.modules.size(); i++) {

            Module m = Client.modules.get(i);

            StringBuilder mjson = new StringBuilder(String.format("\"%s\":{", m.name));
            mjson.append(String.format("\"%s\":%b,", "enabled", m.toggled));
            for (int ii = 0; ii < m.settings.size(); ii++) {

                Setting s = m.settings.get(ii);

                if (s instanceof BooleanSetting) {
                    mjson.append(String.format("\"%s\":%b%s", s.name, ((BooleanSetting) s).enabled, ii == m.settings.size() - 1 ? "" : ","));
                } else if (s instanceof NumberSetting) {
                    mjson.append(String.format("\"%s\":%s%s", s.name, ((NumberSetting) s).value, ii == m.settings.size() - 1 ? "" : ","));
                } else if (s instanceof ModeSetting) {
                    mjson.append(String.format("\"%s\":%s%s", s.name, ((ModeSetting) s).index, ii == m.settings.size() - 1 ? "" : ","));
                } else if (s instanceof KeyBindSetting) {
                    mjson.append(String.format("\"%s\":%s%s", s.name, ((KeyBindSetting) s).key, ii == m.settings.size() - 1 ? "" : ","));
                }

            }
            mjson.append(i == Client.modules.size() - 1 ? "}" : "},");
            json.append(mjson);
        }
        json.append("}");
        fw.write(json.toString());
        fw.close();

        Minecraft.LOGGER.info("Successfully wrote to the properties file");
    }

    public static void saveHUDProperties() throws IOException {
        File file = new File("PentaHackHUDConfig.json");
        FileWriter fw = new FileWriter(file);

        StringBuilder json = new StringBuilder("{");

        for (int i = 0; i < Client.hud.components.size(); i++) {

            HudComponent m = Client.hud.components.get(i);

            StringBuilder mjson = new StringBuilder(String.format("\"%s\":{", m.name));
            mjson.append(String.format("\"%s\":%b%s", "hidden", m.hidden, m.settings.size() == 0 ? "" : ","));
            for (int ii = 0; ii < m.settings.size(); ii++) {

                Setting s = m.settings.get(ii);

                if (s instanceof BooleanSetting) {
                    mjson.append(String.format("\"%s\":%b%s", s.name, ((BooleanSetting) s).enabled, ii == m.settings.size() - 1 ? "" : ","));
                } else if (s instanceof NumberSetting) {
                    mjson.append(String.format("\"%s\":%s%s", s.name, ((NumberSetting) s).value, ii == m.settings.size() - 1 ? "" : ","));
                } else if (s instanceof ModeSetting) {
                    mjson.append(String.format("\"%s\":%s%s", s.name, ((ModeSetting) s).index, ii == m.settings.size() - 1 ? "" : ","));
                } else if (s instanceof KeyBindSetting) {
                    mjson.append(String.format("\"%s\":%s%s", s.name, ((KeyBindSetting) s).key, ii == m.settings.size() - 1 ? "" : ","));
                }

            }
            mjson.append(i == Client.hud.components.size() - 1 ? "}" : "},");
            json.append(mjson);
        }
        json.append("}");
        fw.write(json.toString());
        fw.close();

        Minecraft.LOGGER.info("Successfully wrote to the HUD properties file");
    }

    public static void readProperties() {

        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("PentaHackConfig.json"))) {
            line = bufferedReader.readLine();
            Minecraft.LOGGER.info("Successfully loaded the properties file");
        } catch (FileNotFoundException e) {
            Minecraft.LOGGER.error("Properties file for PentaHack not found");
            return;
        } catch (IOException e) {
            Minecraft.LOGGER.error("An error occurred while trying to read properties");
            return;
        }

        line = line.substring(1);
        line = line.substring(0, line.length() - 1);

        List<String> json = new ArrayList<String>(Arrays.asList(line.split("},")));

        for (String s : json) {
            Module module = Client.getModuleByName(((s.split(":")[0]).substring(1)).substring(0, s.split(":")[0].length() - 2));
            if (module == null) {
                continue;
            }
            String s1 = s.split(Pattern.quote(":{"))[1].substring(0, s.split(Pattern.quote(":{"))[1].length());
            if (s1.charAt(s1.length() - 1) == '}') {
                s1 = s1.substring(0, s1.length() - 1);
            }

            for (String s2 : s1.split(",")) {

                String[] s3 = s2.split(":");

                if (s3[0].equals("\"enabled\"")) {
                    module.toggled = Boolean.parseBoolean(s3[1]);
                } else {

                    Setting setting = Client.getSettingByName(module, (s3[0].substring(1)).substring(0, s3[0].length() - 2));

                    if (setting instanceof BooleanSetting) {
                        ((BooleanSetting) setting).enabled = Boolean.parseBoolean(s3[1]);
                    } else if (setting instanceof NumberSetting) {
                        ((NumberSetting) setting).value = Double.parseDouble(s3[1]);
                    } else if (setting instanceof ModeSetting) {
                        ((ModeSetting) setting).index = Integer.parseInt(s3[1]);
                    } else if (setting instanceof KeyBindSetting) {
                        ((KeyBindSetting) setting).key = Integer.parseInt(s3[1]);
                        module.keyCode = Integer.parseInt(s3[1]);
                    }
                }
            }
        }
        Minecraft.LOGGER.info("Successfully read the properties file");
    }
    public static void readHUDProperties() {

        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("PentaHackHUDConfig.json"))) {
            line = bufferedReader.readLine();
            Minecraft.LOGGER.info("Successfully loaded the HUD properties file");
        } catch (FileNotFoundException e) {
            Minecraft.LOGGER.error("Properties file for PentaHack HUD not found");
            return;
        } catch (IOException e) {
            Minecraft.LOGGER.error("An error occurred while trying to read HUD properties");
            return;
        }

        line = line.substring(1);
        line = line.substring(0, line.length() - 1);

        List<String> json = new ArrayList<String>(Arrays.asList(line.split("},")));

        for (String s : json) {
            HudComponent module = Client.hud.getComponentByName(((s.split(":")[0]).substring(1)).substring(0, s.split(":")[0].length() - 2));
            if (module == null) {
                continue;
            }
            String s1 = s.split(Pattern.quote(":{"))[1].substring(0, s.split(Pattern.quote(":{"))[1].length());
            if (s1.charAt(s1.length() - 1) == '}') {
                s1 = s1.substring(0, s1.length() - 1);
            }
            System.out.println(s1);
            for (String s2 : s1.split(",")) {

                String[] s3 = s2.split(":");

                if (s3[0].equals("\"hidden\"")) {
                    module.hidden = Boolean.parseBoolean(s3[1]);
                } else {

                    Setting setting = Client.hud.getSettingByName(module, (s3[0].substring(1)).substring(0, s3[0].length() - 2));

                    if (setting instanceof BooleanSetting) {
                        ((BooleanSetting) setting).enabled = Boolean.parseBoolean(s3[1]);
                    } else if (setting instanceof NumberSetting) {
                        ((NumberSetting) setting).value = Double.parseDouble(s3[1]);
                    } else if (setting instanceof ModeSetting) {
                        ((ModeSetting) setting).index = Integer.parseInt(s3[1]);
                    } else if (setting instanceof KeyBindSetting) {
                        ((KeyBindSetting) setting).key = Integer.parseInt(s3[1]);
                    }
                }
            }
        }
        Minecraft.LOGGER.info("Successfully read the HUD properties file");
    }
}
