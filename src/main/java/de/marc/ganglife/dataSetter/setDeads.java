package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class setDeads extends QueryFactory {

    public setDeads(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Boolean> setDead(UUID uniqueId, String dead) {
        return builder().query("UPDATE accounts SET dead = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(dead)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Optional<String>> getDead(UUID uniqueId) {
        return builder(String.class)
                .query("SELECT dead FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getString("dead"))
                .first();
    }
    public boolean isDead(UUID playerUUID, String dead) {
        CompletableFuture<Optional<String>> deadFture = getDead(playerUUID);

        Optional<String> d = deadFture.join();

        return d.filter(actualMoney -> actualMoney.equals(dead)).isPresent();
    }
}
