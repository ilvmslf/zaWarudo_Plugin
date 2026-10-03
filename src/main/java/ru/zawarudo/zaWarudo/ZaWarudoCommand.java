package ru.zawarudo.zaWarudo;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ZaWarudoCommand implements CommandExecutor {
    // UUID Типочков (жертв)
    public static final Set<UUID> frozenPlayers = new HashSet<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Как ты посмел...");
            return true;
        }

        Title title = Title.title(
                Component.text("ZA WARUDO!", NamedTextColor.GOLD),
                Component.text("Time stopped", NamedTextColor.GRAY),
                Title.Times.times(
                        Duration.ofMillis(500),
                        Duration.ofSeconds(3),
                        Duration.ofMillis(500)
                )
        );

        for (Player online : Bukkit.getOnlinePlayers()) { online.showTitle(title); };

        frozenPlayers.clear();
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (!online.equals(player)) {
                frozenPlayers.add(online.getUniqueId());
            }
        }

        Bukkit.getServer().getServerTickManager().setFrozen(true);

        Bukkit.getScheduler().runTaskLater(
                ZaWarudo.getPlugin(ZaWarudo.class),
                () -> {
                    frozenPlayers.clear();
                    Bukkit.getServer().getServerTickManager().setFrozen(false);
                },
                400L
        );



        return true;
    }
}