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

    public boolean isInFaction(UUID playerUUID, String faction) {
        CompletableFuture<Optional<String>> facFuture = getFaction(playerUUID);

        Optional<String> fac = facFuture.join();

        return fac.filter(actualMoney -> actualMoney.equals(faction)).isPresent();
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

    public boolean hasRank(UUID playerUUID, int rank) {
        CompletableFuture<Optional<Integer>> rankFuture = getRank(playerUUID);

        Optional<Integer> rang = rankFuture.join();

        return rang.filter(actualMoney -> actualMoney >= rank).isPresent();
    }
}
