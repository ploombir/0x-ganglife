package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class setActs extends QueryFactory {

    public setActs(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Optional<Integer>> getHaft(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT haft FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("haft"))
                .first();
    }

    public CompletableFuture<List<String>> getReasons(UUID uniqueId) {
        return builder(String.class)
                .query("SELECT akten FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getString("akten"))
                .all();
    }

    public CompletableFuture<Boolean> setReasons(UUID uniqueId, List<String> reasons) {
        return builder().query("UPDATE accounts SET akten = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(String.join(", ", reasons))
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> addHaft(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET haft = haft + ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> removeHaft(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET haft = haft - ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setHaft(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET haft = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Optional<Boolean>> getHaft(UUID uniqueId, int amount) {
        return builder(Boolean.class)
                .query("SELECT haft FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("haft") >= amount)
                .first();
    }

    public CompletableFuture<Optional<Boolean>> noHaft(UUID uniqueId, int amount) {
        return builder(Boolean.class)
                .query("SELECT haft FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("haft") == amount)
                .first();
    }

    public CompletableFuture<Boolean> setJail(UUID uniqueId, String jail) {
        return builder().query("UPDATE accounts SET jail = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(jail)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Optional<Boolean>> isJail(UUID uniqueId, String jail) {
        return builder(Boolean.class)
                .query("SELECT jail FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getString("jail") != null && row.getString("jail").equalsIgnoreCase(jail))
                .first();
    }
}
