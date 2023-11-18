package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class setDiscordverify extends QueryFactory {

    public setDiscordverify(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Optional<Integer>> getDiscordVerify(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT discordVerify FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("discordVerify"))
                .first();
    }

    public CompletableFuture<Boolean> setDiscordVerify(UUID uniqueId, int amount) {
        return builder()
                .query("UPDATE accounts SET discordVerify = ? WHERE uniqueId = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }
}
