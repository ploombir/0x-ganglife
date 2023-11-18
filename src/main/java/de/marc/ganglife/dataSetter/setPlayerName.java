package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class setPlayerName extends QueryFactory {

    public setPlayerName(DataSource dataSource) {
        super(dataSource);
    }

    public static void setPlayerName(UUID name, String string) {
        File file = new File("plugins/0x", "datas.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        cfg.set(name + ".playername", string);
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CompletableFuture<Boolean> setPlayerNameDatabase(UUID uniqueId, String playerName) {
        return builder().query("UPDATE accounts SET playername = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(playerName)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }
}
