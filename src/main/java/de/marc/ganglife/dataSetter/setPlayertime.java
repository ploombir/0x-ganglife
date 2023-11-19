package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class setPlayertime extends QueryFactory {

    public setPlayertime(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Optional<Integer>> getPlayetime(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT playertime FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("playertime"))
                .first();
    }

    public CompletableFuture<Boolean> addPlayertime(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET playertime = playertime + ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setPlayertime(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET playertime = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }
}
