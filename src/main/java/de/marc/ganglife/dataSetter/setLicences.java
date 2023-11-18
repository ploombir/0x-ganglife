package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class setLicences extends QueryFactory {

    public setLicences(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Optional<String>> getGunFile(UUID uniqueId) {
        return builder(String.class)
                .query("SELECT weapon_licenc FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getString("weapon_licenc"))
                .first();
    }

    public CompletableFuture<Boolean> setGunFile(UUID uniqueId, String string) {
        return builder().query("UPDATE accounts SET weapon_licenc = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(string)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public boolean hasGunFile(UUID uniqueId, String gun) {
        getGunFile(uniqueId)
                .thenApply(playerGun -> playerGun.isPresent() && playerGun.get().equalsIgnoreCase(gun));
        return false;
    }

    public CompletableFuture<Optional<String>> getDriveFile(UUID uniqueId) {
        return builder(String.class)
                .query("SELECT drive_licenc FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getString("drive_licenc"))
                .first();
    }

    public CompletableFuture<Boolean> setDriveFile(UUID uniqueId, String string) {
        return builder().query("UPDATE accounts SET drive_licenc = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(string)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public boolean hasDriveFile(UUID uniqueId, String drive) {
        getDriveFile(uniqueId)
                .thenApply(playerDrive -> playerDrive.isPresent() && playerDrive.get().equalsIgnoreCase(drive));
        return false;
    }

    public CompletableFuture<Optional<String>> getFirstKitFile(UUID uniqueId) {
        return builder(String.class)
                .query("SELECT firstaid_licenc FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getString("firstaid_licenc"))
                .first();
    }

    public CompletableFuture<Boolean> setFirstKitFile(UUID uniqueId, String string) {
        return builder().query("UPDATE accounts SET firstaid_licenc = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(string)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public boolean hasFirstKitFile(UUID uniqueId, String drive) {
        getFirstKitFile(uniqueId)
                .thenApply(playerFirstKit -> playerFirstKit.isPresent() && playerFirstKit.get().equalsIgnoreCase(drive));
        return false;
    }
}
