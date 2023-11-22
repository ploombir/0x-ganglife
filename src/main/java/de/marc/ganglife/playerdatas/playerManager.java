package de.marc.ganglife.playerdatas;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;
import org.bukkit.Bukkit;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static de.marc.ganglife.playerEvents.registerAccounts.generateRandomFourDigitNumber;

public class playerManager extends QueryFactory {
    public playerManager(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Boolean> createPlayer(UUID uniqueId) {
        return builder().query("INSERT INTO accounts(uniqueId, deathTime, isJail, cash, phoneNumber," +
                        "phoneFlightMode, phoneContacts, playerName, bank, level," +
                        "levelExp, faction, factionRank, drink, houseNumber," +
                        "houseInventory, paydayTime, playTime, weaponLicence, driveLicence," +
                        "firstaidLicence, jailTime, actReasons, garbageLevel, garbageExp," +
                        "cocaineAmount, weedAmount, methAmount, medicineAmount, bulletproofAmount," +
                        "policeBulletproofAmount, storageInventory, rentStorage, firstName, lastName," +
                        "birthDate, gender, discordVerify, discordId, premiumAccount," +
                        "isFFA, ffaInventory, ffaKills, ffaDeaths, votes) " +

                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + //20
                        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," + //20
                        " ?, ?, ?, ?, ?)")
                .parameter(stmt -> stmt.setUuidAsString(uniqueId)
                        .setInt(0).setBoolean(false)
                        .setInt(500)
                        .setInt(5000) // Mobile
                        .setBoolean(false)
                        .setString("[]")
                        .setString(Bukkit.getPlayer(uniqueId).getName())
                        .setInt(0)
                        .setInt(0)
                        .setInt(0)
                        .setString("Zivilist")
                        .setInt(0)
                        .setInt(10)
                        .setInt(0)
                        .setString("[]")
                        .setInt(0)
                        .setInt(0)
                        .setBoolean(false)
                        .setBoolean(false)
                        .setBoolean(false)
                        .setInt(0)
                        .setString("Leer")
                        .setInt(0)
                        .setInt(0)
                        .setInt(0)
                        .setInt(0)
                        .setInt(0)
                        .setInt(0)
                        .setInt(0)
                        .setInt(0)
                        .setString("[]")
                        .setBoolean(false)
                        .setString(null)
                        .setString(null)
                        .setString(null)
                        .setString(null)
                        .setInt(generateRandomFourDigitNumber())
                        .setInt(null)
                        .setBoolean(false)
                        .setBoolean(false)
                        .setString("[]")
                        .setInt(0)
                        .setInt(0)
                        .setInt(0)
                )
                .insert()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Optional<UPlayer>> loadPlayer(UUID uniqueId) {
        return builder(UPlayer.class)
                .query("SELECT * FROM accounts WHERE uniqueId = ?")
                .parameter(stmt -> stmt.setUuidAsString(uniqueId))
                .readRow(row -> new UPlayer(Bukkit.getPlayer(row.getUuidFromString("uniqueId")), this)
                        .setCash(row.getInt("money"))
                        .setJailTime(row.getInt("haft")))
                .first();
    }

    public CompletableFuture<Boolean> savePlayer(UPlayer uPlayer) {
        return builder().query("UPDATE accounts SET money = ?, haft = ? WHERE uniqueId = ?")
                .parameter(stmt -> stmt.setInt(uPlayer.getCash())
                        .setInt(uPlayer.getJailTime())
                        .setUuidAsString(uPlayer.getUUID()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }
}
