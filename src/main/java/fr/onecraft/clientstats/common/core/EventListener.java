package fr.onecraft.clientstats.common.core;

import fr.onecraft.clientstats.ClientStatsAPI;
import fr.onecraft.clientstats.common.user.MixedUser;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EventListener {

    private final Map<UUID, Long> playtimes = new HashMap<>();
    private final AbstractAPI plugin;

    public EventListener(AbstractAPI plugin) {
        this.plugin = plugin;
    }

    public void onPlayerJoin(MixedUser player, boolean isNew) {
        if (!player.hasPermission(ClientStatsAPI.EXEMPT_PERMISSION)) {
            plugin.registerJoin(player, isNew);
            playtimes.put(player.getUniqueId(), System.currentTimeMillis());
        }
        plugin.updatePlayerCount();
    }

    public void onPlayerQuit(MixedUser player) {
        Long playtime = playtimes.remove(player.getUniqueId());
        if (playtime != null) {
            plugin.registerPlaytime(System.currentTimeMillis() - playtime);
        }
    }

}