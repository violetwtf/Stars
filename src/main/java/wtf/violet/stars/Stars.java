package wtf.violet.stars;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public final class Stars extends JavaPlugin {

    @Override
    public void onEnable() {
        final Server server = getServer();
        final Scoreboard scoreboard = server.getScoreboardManager().getMainScoreboard();
        Objective objective;

        try {
            objective = scoreboard.registerNewObjective(
                    "netherStars",
                    "dummy",
                    "Nether Stars"
            );
        } catch (Exception ignored) {
            objective = scoreboard.getObjective("netherStars");
        } // Already exists

        objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);

        server.getScheduler().runTaskTimerAsynchronously(this, () -> {
            for (final Player player : server.getOnlinePlayers()) {
                int count = 0;

                for (final ItemStack stack : player.getInventory().getContents()) {
                    if (null == stack) {
                        continue;
                    }
                    if (stack.getType().equals(Material.NETHER_STAR)) {
                        count += stack.getAmount();
                    }
                }

                getServer()
                        .getScoreboardManager()
                        .getMainScoreboard()
                        .getObjective("netherStars")
                        .getScore(player.getName())
                        .setScore(count);
            }
        }, 0, 20);
    }
    
}
