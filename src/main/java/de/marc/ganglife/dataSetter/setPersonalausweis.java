package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class setPersonalausweis extends QueryFactory {

    public setPersonalausweis(DataSource dataSource) {
        super(dataSource);
    }

    public static boolean isInt(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public CompletableFuture<Optional<String>> getVorname(UUID uniqueId) {
        return builder(String.class)
                .query("SELECT vorname FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getString("vorname"))
                .first();
    }

    public CompletableFuture<Optional<String>> getNachname(UUID uniqueId) {
        return builder(String.class)
                .query("SELECT nachname FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getString("nachname"))
                .first();
    }

    public CompletableFuture<Optional<String>> getBDay(UUID uniqueId) {
        return builder(String.class)
                .query("SELECT geburtsdatum FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getString("geburtsdatum"))
                .first();
    }

    public CompletableFuture<Optional<String>> getGeschlecht(UUID uniqueId) {
        return builder(String.class)
                .query("SELECT geschlecht FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getString("geschlecht"))
                .first();
    }

    public CompletableFuture<Boolean> setVorname(UUID uniqueId, String vorname) {
        return builder().query("UPDATE accounts SET vorname = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(vorname)
                        .setString(uniqueId.toString()))
                .insert()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setNachname(UUID uniqueId, String nachname) {
        return builder().query("UPDATE accounts SET nachname = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(nachname)
                        .setString(uniqueId.toString()))
                .insert()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setBDay(UUID uniqueId, String geburtsdatum) {
        return builder().query("UPDATE accounts SET geburtsdatum = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(geburtsdatum)
                        .setString(uniqueId.toString()))
                .insert()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setGeschlecht(UUID uniqueId, String geschlecht) {
        return builder().query("UPDATE accounts SET geschlecht = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(geschlecht)
                        .setString(uniqueId.toString()))
                .insert()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setPerso(UUID uniqueId, String perso) {
        return builder().query("UPDATE accounts SET perso = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(perso)
                        .setString(uniqueId.toString()))
                .insert()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Optional<String>> getPerso(UUID uniqueId) {
        return builder(String.class)
                .query("SELECT perso FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getString("perso"))
                .first();
    }

    public boolean hasPersonalausweis(UUID uniqueId, String perso) {
        getPerso(uniqueId)
                .thenApply(storedPerso -> storedPerso.isPresent() && storedPerso.get().equals(perso));
        return false;
    }
}
