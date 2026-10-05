package ru.zawarudo.zaWarudo;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ZaWarudoCommand implements CommandExecutor {
    // UUID Типочков (жертв)
    public static final Set<UUID> frozenPlayers = new HashSet<>();

    // Флажок на тайм-стоп
    private static boolean active = false;

    // Время кд
    private static long cooldown = 0;

    private final ZaWarudo plugin;

    public ZaWarudoCommand(ZaWarudo plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Как ты посмел...");
            return true;
        }

        // Проверка на наличие силы Za Warudo
        String owner = plugin.getConfig().getString("owner", "");
        if (owner.isEmpty() || !player.getName().equalsIgnoreCase(owner)) {
            player.sendMessage(Component.text("Как ты посмел, ничтожество, у тебя нет силы Za warudo!", NamedTextColor.RED));
            player.setHealth(1.0);
            player.playSound(
                    player.getLocation(),
                    org.bukkit.Sound.ENTITY_ENDER_DRAGON_GROWL,
                    1.0f, 1.0f
            );
            return true;
        }

        // Проверка на то, активен ли Za Wardo сейчас или нет
        if (active) {
            player.sendMessage(Component.text("Za Warudo уже активирован, молись что это не за тобой...", NamedTextColor.GOLD));
            return true;
        }

        active = true;

        long now = System.currentTimeMillis();
        if (now < cooldown) {
            long secondLeft = (cooldown - now) / 1000 + 1;
            player.sendMessage(Component.text("Ещё не время, восстановись, подожди ещё" + secondLeft + "секунд...", NamedTextColor.GOLD));
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

        for (Player online : Bukkit.getOnlinePlayers()) {
            online.showTitle(title);

            online.playSound(
                    online.getLocation(),
                    Sound.BLOCK_BEACON_ACTIVATE,
                    1.0f, 1.5f
            );
        };

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
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        online.playSound(
                                online.getLocation(),
                                org.bukkit.Sound.BLOCK_PORTAL_TRIGGER,
                                1.0f, 1.5f
                        );
                    }
                },
                150L
        );

        Bukkit.getScheduler().runTaskLater(
                ZaWarudo.getPlugin(ZaWarudo.class),
                () -> {
                    active = false;
                    frozenPlayers.clear();
                    Bukkit.getServer().getServerTickManager().setFrozen(false);
                    for (int i = 0; i < player.getInventory().getSize(); i++) {
                        ItemStack item = player.getInventory().getItem(i);
                        if (item == null) continue;
                        if (item.getType() != Material.STICK) continue;
                        if (!item.hasItemMeta()) continue;
                        if (!item.getItemMeta().hasDisplayName()) continue;

                        Component displayName = item.getItemMeta().displayName();
                        if (displayName != null && displayName.equals(Component.text("Бан-палка", NamedTextColor.GOLD))) {
                            player.getInventory().setItem(i, null);
                        }
                    }
                    },
                200L
        );

        // Выдача палки
        ItemStack banStick = new ItemStack(Material.STICK);
        ItemMeta meta = banStick.getItemMeta();
        if (meta != null) {
            meta.displayName(Component.text("Бан-палка", NamedTextColor.GOLD));
            meta.lore(java.util.List.of(
                    Component.text("Ударь негодяя и отправь его в бан", NamedTextColor.RED)
            ));
            banStick.setItemMeta(meta);
        }
        player.getInventory().addItem(banStick);

        return true;
    }
}