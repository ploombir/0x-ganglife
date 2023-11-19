package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class setMobile extends QueryFactory {

    public setMobile(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Optional<Integer>> getNumber(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT mobile FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("mobile"))
                .first();
    }

    public CompletableFuture<Boolean> addNumber(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET mobile = mobile + ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setNumber(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET mobile = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Optional<String>> getContacts(UUID uniqueId) {
        return builder(String.class)
                .query("SELECT contacts FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getString("contacts"))
                .first();
    }

    public CompletableFuture<Optional<String>> getPlayerFromNumber(String number) {
        return builder(String.class)
                .query("SELECT playername FROM accounts WHERE mobile = ?")
                .parameter(stmt -> stmt.setString(number))
                .readRow(row -> row.getString("playername"))
                .first();
    }

    public boolean isFlugmodus(UUID uniqueId, String perso) {
        getFlugmodus(uniqueId)
                .thenApply(storedPerso -> storedPerso.isPresent() && storedPerso.get().equalsIgnoreCase(perso));
        return false;
    }

    public CompletableFuture<Optional<String>> getFlugmodusFromNumber(String number) {
        return builder(String.class)
                .query("SELECT flugmodus FROM accounts WHERE mobile = ?")
                .parameter(stmt -> stmt.setString(number))
                .readRow(row -> row.getString("flugmodus"))
                .first();
    }
    public CompletableFuture<Boolean> setContacts(UUID uniqueId, String contacts) {
        return builder().query("UPDATE accounts SET contacts = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(contacts)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setFlugmodus(UUID uniqueId, String flugmodus) {
        return builder().query("UPDATE accounts SET flugmodus = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(flugmodus)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Optional<String>> getFlugmodus(UUID uniqueId) {
        return builder(String.class)
                .query("SELECT flugmodus FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getString("flugmodus"))
                .first();
    }

    public CompletableFuture<Optional<Integer>> getPlayers() {
        return builder(Integer.class)
                .query("SELECT player_accounts FROM server_datas")
                .emptyParams()
                .readRow(row -> row.getInt("player_accounts"))
                .first();
    }

    public CompletableFuture<Boolean> addPlayers(int amount) {
        return builder().query("UPDATE server_datas SET player_accounts = player_accounts + ?")
                .parameter(stmt -> stmt.setInt(amount))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setPlayers(int amount) {
        return builder().query("INSERT INTO server_datas (player_accounts) VALUES (?)")
                .parameter(stmt -> stmt.setInt(amount))
                .insert()
                .send()
                .thenApply(UpdateResult::changed);
    }
}
