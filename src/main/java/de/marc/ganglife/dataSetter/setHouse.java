package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class setHouse extends QueryFactory {

    public setHouse(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Optional<Integer>> getHouseSystem(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT house FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("house"))
                .first();
    }

    public CompletableFuture<Boolean> setHouseSystem(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET house = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }
    public boolean hasHouse(UUID playerUUID, int amount) {
        CompletableFuture<Optional<Integer>> future = getHouseSystem(playerUUID);

        Optional<Integer> integer = future.join();

        return integer.filter(actualMoney -> actualMoney == amount).isPresent();
    }

    public CompletableFuture<Boolean> setHouseInventory(UUID uniqueId, String inventory) {
        return builder().query("UPDATE accounts SET house_inventory = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(inventory)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Optional<Integer>> getHouseInventory(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT house_inventory FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("house_inventory"))
                .first();
    }
}
