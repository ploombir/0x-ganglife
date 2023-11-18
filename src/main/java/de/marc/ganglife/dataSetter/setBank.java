package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class setBank extends QueryFactory {

    public setBank(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Optional<Integer>> getBank(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT bank FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("bank"))
                .first();
    }

    public CompletableFuture<Boolean> addBank(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET bank = bank + ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> removeBank(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET bank = bank - ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setBank(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET bank = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Optional<Boolean>> hasEnoughBank(UUID uniqueId, int amount) {
        return builder(Boolean.class)
                .query("SELECT bank FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("bank") >= amount)
                .first();
    }
}
