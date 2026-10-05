package ru.zawarudo.zaWarudo;

import org.bukkit.plugin.java.JavaPlugin;

public final class ZaWarudo extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        var command = getCommand("za_warudo");
        if (command != null) { command.setExecutor(new ZaWarudoCommand(this)); }

        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        getServer().getPluginManager().registerEvents(new BanStickListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
