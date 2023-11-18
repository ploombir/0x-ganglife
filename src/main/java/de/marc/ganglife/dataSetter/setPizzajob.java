package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class setPizzajob extends QueryFactory {

    public setPizzajob(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Optional<Integer>> getExpSystem(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT pizzajob_exp FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("pizzajob_exp"))
                .first();
    }

    public CompletableFuture<Boolean> addExpSystem(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET pizzajob_exp = pizzajob_exp + ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> removeExpSystem(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET pizzajob_exp = pizzajob_exp - ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setExpSystem(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET pizzajob_exp = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public boolean hasEnoughExpSystem(UUID uniqueId, int amount) {
        getExpSystem(uniqueId)
                .thenApply(playerExp -> playerExp.isPresent() && playerExp.get() >= amount);
        return false;
    }

    public CompletableFuture<Optional<Integer>> getLevelSystem(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT pizzajob_level FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("pizzajob_level"))
                .first();
    }

    public CompletableFuture<Boolean> addLevelSystem(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET pizzajob_level = pizzajob_level + ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> removeLevelSystem(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET pizzajob_level = pizzajob_level - ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setLevelSystem(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET pizzajob_level = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public boolean isLevelSystem(UUID uniqueId, int amount) {
        getLevelSystem(uniqueId)
                .thenApply(playerLevel -> playerLevel.isPresent() && playerLevel.get() == amount);
        return false;
    }
}
