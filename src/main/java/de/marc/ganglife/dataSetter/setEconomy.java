package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;
import de.marc.ganglife.Main.main;
import org.bukkit.Bukkit;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class setEconomy extends QueryFactory {

    public setEconomy(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Optional<Integer>> getMoney(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT money FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("money"))
                .first();
    }

    public CompletableFuture<Boolean> addMoney(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET money = money + ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> removeMoney(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET money = money - ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setMoney(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET money = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public boolean hasEnoughMoney(UUID playerUUID, int requiredAmount) {
        CompletableFuture<Optional<Integer>> moneyFuture = getMoney(playerUUID);

        Optional<Integer> money = moneyFuture.join();

        return money.filter(actualMoney -> actualMoney >= requiredAmount).isPresent();
    }
    public int resultMoney(UUID playerUUID) {
        CompletableFuture<Optional<Integer>> moneyFuture = getMoney(playerUUID);
        Optional<Integer> money = moneyFuture.join();

        return money.get();
    }
}
