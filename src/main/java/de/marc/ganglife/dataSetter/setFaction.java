package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class setFaction extends QueryFactory {

    public setFaction(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Optional<String>> getFaction(UUID uniqueId) {
        return builder(String.class)
                .query("SELECT faction FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getString("faction"))
                .first();
    }

    public CompletableFuture<Boolean> setFaction(UUID uniqueId, String faction) {
        return builder().query("UPDATE accounts SET faction = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(faction)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public boolean isInFaction(UUID uniqueId, String faction) {
        getFaction(uniqueId)
                .thenApply(playerFaction -> playerFaction.isPresent() && playerFaction.get().equals(faction));
        return false;
    }

    public CompletableFuture<Optional<Integer>> getRank(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT rank FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("rank"))
                .first();
    }

    public CompletableFuture<Boolean> setRank(UUID uniqueId, int rank) {
        return builder().query("UPDATE accounts SET rank = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(rank)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public boolean isRank(UUID uniqueId, int rank) {
        getRank(uniqueId)
                .thenApply(playerRank -> playerRank.isPresent() && playerRank.get() == rank);
        return false;
    }
}
