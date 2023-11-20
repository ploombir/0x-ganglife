package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class setLager extends QueryFactory {

    public setLager(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Boolean> setLagerInventory(UUID uniqueId, String inventory) {
        return builder().query("UPDATE accounts SET lagerhalle_inventory = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(inventory)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Optional<String>> getLagerInventory(UUID uniqueId) {
        return builder(String.class)
                .query("SELECT lagerhalle_inventory FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getString("lagerhalle_inventory"))
                .first();
    }

    public CompletableFuture<Boolean> setHasLager(UUID uniqueId, String inventory) {
        return builder().query("UPDATE accounts SET buylager = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(inventory)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Optional<String>> getHasLager(UUID uniqueId) {
        return builder(String.class)
                .query("SELECT buylager FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getString("buylager"))
                .first();
    }
    public boolean hasLager(UUID playerUUID, String bool) {
        CompletableFuture<Optional<String>> future = getHasLager(playerUUID);

        Optional<String> str = future.join();

        return str.filter(actualMoney -> actualMoney.equals(bool)).isPresent();
    }
}
