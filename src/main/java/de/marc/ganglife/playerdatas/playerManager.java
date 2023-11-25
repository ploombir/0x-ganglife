package de.marc.ganglife.playerdatas;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;
import de.marc.ganglife.Main.main;
import org.bukkit.Bukkit;
import de.marc.ganglife.dataSetter.*;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static de.marc.ganglife.playerEvents.registerAccounts.generateRandomFourDigitNumber;

public class playerManager extends QueryFactory {
    public playerManager(DataSource dataSource) {
        super(dataSource);
    }
    getLastID getLastID = new getLastID(main.getPlugin().getDatabaseAsync().getDataSource());

    public CompletableFuture<Boolean> createPlayer(UUID uniqueId) {
        return builder().query("INSERT INTO accounts(uniqueid, deathTime, isJail, cash, phoneNumber," +
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
                        .setInt(0)
                        .setBoolean(false)
                        .setInt(500)
                        .setInt(5000) // Mobile
                        .setBoolean(false)
                        .setString("[]")
                        .setString(Bukkit.getPlayer(uniqueId).getName())
                        .setInt(0)
                        .setInt(1)
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
                        .setString("")
                        .setString("")
                        .setString("")
                        .setString("")
                        .setInt(generateRandomFourDigitNumber())
                        .setInt(0)
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
                .query("SELECT * FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setUuidAsString(uniqueId))
                .readRow(row -> new UPlayer(Bukkit.getPlayer(row.getUuidFromString("uniqueid")), this)
                        .setCash(row.getInt("cash"))
                        .setDeathTime(row.getInt("deathTime"))
                        .setJail(row.getBoolean("isJail"))
                        .setPhoneNumber(row.getInt("phoneNumber"))
                        .setPhoneFlightMode(row.getBoolean("phoneFlightMode"))
                        .setPhoneContacts(row.getString("phoneContacts"))
                        .setPlayerName(row.getString("playerName"))
                        .setBank(row.getInt("bank"))
                        .setLevel(row.getInt("level"))
                        .setLevelExp(row.getInt("levelExp"))
                        .setFaction(row.getString("faction"))
                        .setFactionRank(row.getInt("factionRank"))
                        .setDrink(row.getInt("drink"))
                        .setHouseNumber(row.getInt("houseNumber"))
                        .setHouseInventory(row.getString("houseInventory"))
                        .setPaydayTime(row.getInt("paydayTime"))
                        .setPlayTime(row.getInt("playTime"))
                        .setWeaponLicence(row.getBoolean("weaponLicence"))
                        .setDriveLicence(row.getBoolean("driveLicence"))
                        .setFirstaidLicence(row.getBoolean("firstaidLicence"))
                        .setJailTime(row.getInt("jailTime"))
                        .setActReasons(row.getString("actReasons"))
                        .setGarbageLevel(row.getInt("garbageLevel"))
                        .setGarbageExp(row.getInt("garbageExp"))
                        .setCocaineAmount(row.getInt("cocaineAmount"))
                        .setWeedAmount(row.getInt("weedAmount"))
                        .setMethAmount(row.getInt("methAmount"))
                        .setMedicineAmount(row.getInt("medicineAmount"))
                        .setBulletproofAmount(row.getInt("bulletproofAmount"))
                        .setPoliceBulletproofAmount(row.getInt("policeBulletproofAmount"))
                        .setStorageInventory(row.getString("storageInventory"))
                        .setRentStorage(row.getBoolean("rentStorage"))
                        .setFirstName(row.getString("firstName"))
                        .setLastName(row.getString("lastName"))
                        .setBirthDate(row.getString("birthDate"))
                        .setGender(row.getString("gender"))
                        .setDiscordVerify(row.getInt("discordVerify"))
                        .setDiscordId(row.getString("discordId"))
                        .setPremiumAccount(row.getBoolean("premiumAccount"))
                        .setFFA(row.getBoolean("isFFA"))
                        .setFfaInventory(row.getString("ffaInventory"))
                        .setFfaKills(row.getInt("ffaKills"))
                        .setFfaDeaths(row.getInt("ffaDeaths"))
                        .setVotes(row.getInt("votes"))
                )
                .first();
    }

    public CompletableFuture<Boolean> savePlayer(UPlayer uPlayer) {
        return builder().query("UPDATE accounts SET deathTime = ?, isJail = ?, cash = ?, phoneNumber = ?, phoneFlightMode = ?," +
                        "phoneContacts = ?, faction = ?, bank = ?, level = ?, levelExp = ?, playerName = ?, factionRank = ?, drink = ?," +
                        "houseNumber = ?, houseInventory = ?, paydayTime = ?, playTime = ?, weaponLicence = ?, driveLicence = ?," +
                        "firstaidLicence = ?, jailTime = ?, actReasons = ?, garbageLevel = ?, garbageExp = ?, cocaineAmount = ?," +
                        "weedAmount = ?, methAmount = ?, medicineAmount = ?, bulletproofAmount = ?, policeBulletproofAmount = ?," +
                        "storageInventory = ?, rentStorage = ?, firstName = ?, lastName = ?, birthDate = ?, gender = ?, discordVerify = ?," +
                        " discordId = ?, premiumAccount = ?, isFFA = ?, ffaInventory = ?, ffaKills = ?, ffaDeaths = ?, votes = ?" +
                        " WHERE uniqueid = ?")
                .parameter(stmt -> stmt
                        .setInt(uPlayer.getDeathTime())
                        .setBoolean(uPlayer.isJail())
                        .setInt(uPlayer.getCash())
                        .setInt(uPlayer.getPhoneNumber())
                        .setBoolean(uPlayer.isPhoneFlightMode())
                        .setString(uPlayer.getPhoneContacts())
                        .setString(uPlayer.getFaction())
                        .setInt(uPlayer.getBank())
                        .setInt(uPlayer.getLevel())
                        .setInt(uPlayer.getLevelExp())
                        .setString(uPlayer.getPlayerName())
                        .setInt(uPlayer.getFactionRank())
                        .setInt(uPlayer.getDrink())
                        .setInt(uPlayer.getHouseNumber())
                        .setString(uPlayer.getHouseInventory())
                        .setInt(uPlayer.getPaydayTime())
                        .setInt(uPlayer.getPlayTime())
                        .setBoolean(uPlayer.isWeaponLicence())
                        .setBoolean(uPlayer.isDriveLicence())
                        .setBoolean(uPlayer.isFirstaidLicence())
                        .setInt(uPlayer.getJailTime())
                        .setString(uPlayer.getActReasons())
                        .setInt(uPlayer.getGarbageLevel())
                        .setInt(uPlayer.getGarbageExp())
                        .setInt(uPlayer.getCocaineAmount())
                        .setInt(uPlayer.getWeedAmount())
                        .setInt(uPlayer.getMethAmount())
                        .setInt(uPlayer.getMedicineAmount())
                        .setInt(uPlayer.getBulletproofAmount())
                        .setInt(uPlayer.getPoliceBulletproofAmount())
                        .setString(uPlayer.getStorageInventory())
                        .setBoolean(uPlayer.isRentStorage())
                        .setString(uPlayer.getFirstName())
                        .setString(uPlayer.getLastName())
                        .setString(uPlayer.getBirthDate())
                        .setString(uPlayer.getGender())
                        .setInt(uPlayer.getDiscordVerify())
                        .setString(uPlayer.getDiscordId())
                        .setBoolean(uPlayer.isPremiumAccount())
                        .setBoolean(uPlayer.isFFA())
                        .setString(uPlayer.getFfaInventory())
                        .setInt(uPlayer.getFfaKills())
                        .setInt(uPlayer.getFfaDeaths())
                        .setInt(uPlayer.getVotes())

                        .setUuidAsString(uPlayer.getUUID()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }
}
