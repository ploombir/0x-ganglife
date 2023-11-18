package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class setVotes extends QueryFactory {

    public setVotes(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Optional<Integer>> getVotes(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT votes FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("votes"))
                .first();
    }

    public CompletableFuture<Boolean> addVotes(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET votes = votes + ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setVotes(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET votes = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> removeVotes(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET votes = votes - ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }
}
