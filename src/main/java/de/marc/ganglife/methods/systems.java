package de.marc.ganglife.methods;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.playerdatas.UPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class systems {

    public static void updateLevel(Player player, UPlayer uPlayer) {

        int requiredExp = 500 + (uPlayer.getLevel() - 1) * 250;

        player.setLevel(uPlayer.getLevel());
        updatePlayerLevelAndExp(player, uPlayer);

        if (uPlayer.getLevelExp() >= requiredExp) {
            uPlayer.setLevel(uPlayer.getLevel() + 1);
            uPlayer.setLevelExp(uPlayer.getLevelExp() - requiredExp);

            player.sendMessage(main.prefix + "§aDu bist nun Level §6" + uPlayer.getLevel());

            int  requiredExpNew = 500 + (uPlayer.getLevel() - 1) * 250;

            player.sendMessage(" §f▹ §7Du benötigst nun §6" + requiredExpNew + " §7EXP, um das nächste Level freizuschalten.");
            main.playSuccessSound(player);
            player.setLevel(uPlayer.getLevel());
            updatePlayerLevelAndExp(player, uPlayer);
        }
    }
    public static void updatePlayerLevelAndExp(Player player, UPlayer uPlayer) {

        int requiredExpNew = 500 + (uPlayer.getLevel() - 1) * 250;

        float expProgress = (float) uPlayer.getLevelExp() / requiredExpNew;

        expProgress = Math.max(0.0f, Math.min(1.0f, expProgress));

        player.setExp(expProgress);
    }
}
