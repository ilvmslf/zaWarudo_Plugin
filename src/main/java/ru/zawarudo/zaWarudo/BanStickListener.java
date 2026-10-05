package ru.zawarudo.zaWarudo;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Date;

public class BanStickListener implements Listener {
    private final ZaWarudo plugin;

    public BanStickListener(ZaWarudo plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        // Атакующий
        if (!(event.getDamager() instanceof Player attacker)) return;
        // Получающий удар
        if (!(event.getEntity() instanceof  Player victim)) return;
        // Удар палкой?
        if (attacker.getInventory().getItemInMainHand().getType() != Material.STICK) return;
        // Получающий удар заморожен?
        if (!ZaWarudoCommand.frozenPlayers.contains(victim.getUniqueId())) return;

        Bukkit.getBanList(BanList.Type.NAME).addBan(
                victim.getName(),
                "Убит силой Za Warudo",
                new Date(System.currentTimeMillis() + 24 * 60 * 60 * 100L),
                attacker.getName()
        );

        victim.kick(Component.text("Ты уничтожен, ничтожество.\n СЛАВА ВЕЛИКОМУ ZA WARUDO!!!", NamedTextColor.RED));

        attacker.sendMessage(Component.text("Ты устранил нищенку " + victim.getName(), NamedTextColor.GOLD));
    }
}
