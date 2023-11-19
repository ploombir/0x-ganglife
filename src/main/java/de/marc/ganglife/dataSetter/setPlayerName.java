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
    public CompletableFuture<Boolean> setPlayerNameDatabase(UUID uniqueId, String playername) {
        return builder().query("UPDATE accounts SET playername = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(playername)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }
}
