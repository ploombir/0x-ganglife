package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class setFaction extends QueryFactory {

    public setFaction(DataSource dataSource) {
        super(dataSource);
    }
    public CompletableFuture<Boolean> setOfflineFaction(String faction, String playerName) {
        return builder().query("UPDATE accounts SET faction = ? WHERE playerName = ?")
                .parameter(stmt -> stmt
                        .setString(faction)
                        .setString(playerName))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }
    public CompletableFuture<Boolean> setOfflineFactionRank(Integer rank, String playerName) {
        return builder().query("UPDATE accounts SET factionRank = ? WHERE playerName = ?")
                .parameter(stmt -> stmt
                        .setInt(rank)
                        .setString(playerName))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }
    public CompletableFuture<Optional<String>> getOfflineFaction(String playerName) {
        return builder(String.class)
                .query("SELECT faction FROM accounts WHERE playerName = ?")
                .parameter(stmt -> stmt.setString(playerName))
                .readRow(row -> row.getString("faction"))
                .first();
    }
    public CompletableFuture<List<String>> getFactionMembers(String faction) {
        return builder(String.class)
                .query("SELECT playerName FROM accounts WHERE faction = ?")
                .parameter(stmt -> stmt.setString(faction))
                .readRow(row -> row.getString("playerName"))
                .all();
    }

    public CompletableFuture<Optional<Integer>> getOfflineFactionRank(String playerName) {
        return builder(Integer.class)
                .query("SELECT factionRank FROM accounts WHERE playerName = ?")
                .parameter(stmt -> stmt.setString(playerName))
                .readRow(row -> row.getInt("factionRank"))
                .first();
    }
}
