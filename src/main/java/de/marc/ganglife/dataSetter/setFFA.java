package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class setFFA extends QueryFactory {

    public setFFA(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Optional<String>> getisinFFA(UUID uniqueId) {
        return builder(String.class)
                .query("SELECT isFFA FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getString("isFFA"))
                .first();
    }

    public CompletableFuture<Boolean> setFFA(UUID uniqueId, String isFFA) {
        return builder().query("UPDATE accounts SET isFFA = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(isFFA)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }
    public boolean isInFFA(UUID playerUUID, String bool) {
        CompletableFuture<Optional<String>> ffaFture = getisinFFA(playerUUID);

        Optional<String> ffa = ffaFture.join();

        return ffa.filter(actualMoney -> actualMoney.equals(bool)).isPresent();
    }

    public CompletableFuture<Optional<String>> getFFAInventory(UUID uniqueId) {
        return builder(String.class)
                .query("SELECT inventory_ffa FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getString("inventory_ffa"))
                .first();
    }

    public CompletableFuture<Boolean> setFFAInventory(UUID uniqueId, String isFFA) {
        return builder().query("UPDATE accounts SET inventory_ffa = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(isFFA)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Optional<Integer>> getFFAKills(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT ffa_kills FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("ffa_kills"))
                .first();
    }

    public CompletableFuture<Boolean> addFFAKill(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET ffa_kills = ffa_kills + ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Optional<Integer>> getFFADeaths(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT ffa_deaths FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("ffa_deaths"))
                .first();
    }

    public CompletableFuture<Boolean> addFFADeath(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET ffa_deaths = ffa_deaths + ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setFFAKills(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET ffa_kills = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setFFADeaths(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET ffa_deaths = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }
}
