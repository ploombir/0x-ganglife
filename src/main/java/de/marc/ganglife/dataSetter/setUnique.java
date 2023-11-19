package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class setUnique extends QueryFactory {

    public setUnique(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Optional<String>> getUniqueID(UUID uniqueId) {
        return builder(String.class)
                .query("SELECT uniqueid FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getString("uniqueid"))
                .first();
    }
    public CompletableFuture<Boolean> insertUniqueIdIntoDatabase(UUID uniqueId, String playerName) {
        return builder()
                .query("INSERT INTO accounts (uniqueid, playername) VALUES (?,?)")
                .parameter(stmt -> stmt.setString(uniqueId.toString())
                        .setString(playerName))
                .insert()
                .send()
                .thenApply(UpdateResult::changed);
    }
}
