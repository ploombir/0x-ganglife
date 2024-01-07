package de.marc.ganglife.playerdatas;

import de.marc.ganglife.Main.main;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Getter
public class UPlayer {
    public static final Map<UUID, UPlayer> cachedUPlayers = new HashMap<>();

    public final UUID uuid;
    public final playerManager playerManager;

    private int deathTime;
    private boolean isJail;
    private int cash;
    private int phoneNumber;
    private boolean phoneFlightMode;
    private String phoneContacts;
    private String playerName;
    private int bank;
    private int level;
    private int levelExp;
    private String faction;
    private int factionRank;
    private int drink;
    private String houseNumber;
    private String houseInventory;
    private int paydayTime;
    private int playTime;
    private boolean weaponLicence;
    private boolean driveLicence;
    private boolean firstaidLicence;
    private int jailTime;
    private String actReasons;
    private int garbageLevel;
    private int garbageExp;
    private int cocaineAmount;
    private int weedAmount;
    private int methAmount;
    private int medicineAmount;
    private int bulletproofAmount;
    private int policeBulletproofAmount;
    private String storageInventory;
    private boolean rentStorage;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String gender;
    private int discordVerify;
    private String discordId;
    private boolean premiumAccount;
    private boolean isFFA;
    private String ffaInventory;
    private int ffaKills;
    private int ffaDeaths;
    private int votes;

    public UPlayer(Player player, playerManager playerManager) {
        this.uuid = player.getUniqueId();

        this.playerManager = playerManager;

        cachedUPlayers.put(this.uuid, this);
    }

    public UUID getUUID() {
        return uuid;
    }
    public static UPlayer getUPlayer(UUID uuid) {
        return cachedUPlayers.get(uuid);
    }

    public void loadData() {
        playerManager.loadPlayer(getUUID()).thenAccept(uPlayer -> {
            Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
                this.deathTime = getDeathTime();
                this.isJail = isJail();
                this.cash = getCash();
                this.phoneNumber = getPhoneNumber();
                this.phoneFlightMode = isPhoneFlightMode();
                this.phoneContacts = getPhoneContacts();
                this.faction = getFaction();
                this.playerName = getPlayerName();
                this.bank = getBank();
                this.level = getLevel();
                this.levelExp = getLevelExp();
                this.factionRank = getFactionRank();
                this.drink = getDrink();
                this.houseNumber = getHouseNumber();
                this.houseInventory = getHouseInventory();
                this.paydayTime = getPaydayTime();
                this.playTime = getPlayTime();
                this.weaponLicence = isWeaponLicence();
                this.driveLicence = isDriveLicence();
                this.firstaidLicence = isFirstaidLicence();
                this.jailTime = getJailTime();
                this.actReasons = getActReasons();
                this.garbageLevel = getGarbageLevel();
                this.garbageExp = getGarbageExp();
                this.cocaineAmount = getCocaineAmount();
                this.weedAmount = getWeedAmount();
                this.methAmount = getMethAmount();
                this.medicineAmount = getMedicineAmount();
                this.bulletproofAmount = getBulletproofAmount();
                this.policeBulletproofAmount = getPoliceBulletproofAmount();
                this.storageInventory = getStorageInventory();
                this.rentStorage = isRentStorage();
                this.firstName = getFirstName();
                this.lastName = getLastName();
                this.birthDate = getBirthDate();
                this.gender = getGender();
                this.discordVerify = getDiscordVerify();
                this.discordId = getDiscordId();
                this.premiumAccount = isPremiumAccount();
                this.isFFA = isFFA();
                this.ffaInventory = getFfaInventory();
                this.ffaKills = getFfaKills();
                this.ffaDeaths = getFfaDeaths();
                this.votes = getVotes();
            });
        });
    }

    public void saveData() {
        playerManager.savePlayer(this);
    }

    public UPlayer setDeathTime(int deathTime) {
        this.deathTime = deathTime;
        return this;
    }

    @Override
    public String toString() {
        return "UPlayer{" +
                "uuid=" + uuid +
                ", playerManager=" + playerManager +
                ", deathTime=" + deathTime +
                ", isJail=" + isJail +
                ", cash=" + cash +
                ", phoneNumber=" + phoneNumber +
                ", phoneFlightMode=" + phoneFlightMode +
                ", phoneContacts='" + phoneContacts + '\'' +
                ", playerName='" + playerName + '\'' +
                ", bank=" + bank +
                ", level=" + level +
                ", levelExp=" + levelExp +
                ", faction='" + faction + '\'' +
                ", factionRank=" + factionRank +
                ", drink=" + drink +
                ", houseNumber=" + houseNumber +
                ", houseInventory='" + houseInventory + '\'' +
                ", paydayTime=" + paydayTime +
                ", playTime=" + playTime +
                ", weaponLicence=" + weaponLicence +
                ", driveLicence=" + driveLicence +
                ", firstaidLicence=" + firstaidLicence +
                ", jailTime=" + jailTime +
                ", actReasons='" + actReasons + '\'' +
                ", garbageLevel=" + garbageLevel +
                ", garbageExp=" + garbageExp +
                ", cocaineAmount=" + cocaineAmount +
                ", weedAmount=" + weedAmount +
                ", methAmount=" + methAmount +
                ", medicineAmount=" + medicineAmount +
                ", bulletproofAmount=" + bulletproofAmount +
                ", policeBulletproofAmount=" + policeBulletproofAmount +
                ", storageInventory='" + storageInventory + '\'' +
                ", rentStorage=" + rentStorage +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", gender='" + gender + '\'' +
                ", discordVerify=" + discordVerify +
                ", discordId='" + discordId + '\'' +
                ", premiumAccount=" + premiumAccount +
                ", isFFA=" + isFFA +
                ", ffaInventory='" + ffaInventory + '\'' +
                ", ffaKills=" + ffaKills +
                ", ffaDeaths=" + ffaDeaths +
                ", votes=" + votes +
                '}';
    }

    public UPlayer setJail(boolean jail) {
        this.isJail = jail;
        return this;
    }

    public UPlayer setCash(int cash) {
        this.cash = cash;
        return this;
    }

    public UPlayer setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UPlayer setPhoneFlightMode(boolean phoneFlightMode) {
        this.phoneFlightMode = phoneFlightMode;
        return this;
    }

    public UPlayer setPhoneContacts(String phoneContacts) {
        this.phoneContacts = phoneContacts;
        return this;
    }

    public UPlayer setPlayerName(String playerName) {
        this.playerName = playerName;
        return this;
    }

    public UPlayer setBank(int bank) {
        this.bank = bank;
        return this;
    }

    public UPlayer setLevel(int level) {
        this.level = level;
        return this;
    }

    public UPlayer setLevelExp(int levelExp) {
        this.levelExp = levelExp;
        return this;
    }

    public UPlayer setFaction(String faction) {
        this.faction = faction;
        return this;
    }
    public String resultFaction() {
        return this.faction;
    }
    public UPlayer setFactionRank(int factionRank) {
        this.factionRank = factionRank;
        return this;
    }

    public UPlayer setDrink(int drink) {
        this.drink = drink;
        return this;
    }

    public UPlayer setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public UPlayer setPaydayTime(int paydayTime) {
        this.paydayTime = paydayTime;
        return this;
    }

    public UPlayer setPlayTime(int playTime) {
        this.playTime = playTime;
        return this;
    }

    public UPlayer setWeaponLicence(boolean weaponLicence) {
        this.weaponLicence = weaponLicence;
        return this;
    }

    public UPlayer setDriveLicence(boolean driveLicence) {
        this.driveLicence = driveLicence;
        return this;
    }

    public UPlayer setFirstaidLicence(boolean firstaidLicence) {
        this.firstaidLicence = firstaidLicence;
        return this;
    }

    public UPlayer setJailTime(int jailTime) {
        this.jailTime = jailTime;
        return this;
    }

    public UPlayer setActReasons(String actReasons) {
        this.actReasons = actReasons;
        return this;
    }

    public UPlayer setGarbageLevel(int garbageLevel) {
        this.garbageLevel = garbageLevel;
        return this;
    }

    public UPlayer setGarbageExp(int garbageExp) {
        this.garbageExp = garbageExp;
        return this;
    }

    public UPlayer setCocaineAmount(int cocaineAmount) {
        this.cocaineAmount = cocaineAmount;
        return this;
    }

    public UPlayer setWeedAmount(int weedAmount) {
        this.weedAmount = weedAmount;
        return this;
    }

    public UPlayer setMethAmount(int methAmount) {
        this.methAmount = methAmount;
        return this;
    }

    public UPlayer setMedicineAmount(int medicineAmount) {
        this.medicineAmount = medicineAmount;
        return this;
    }

    public UPlayer setBulletproofAmount(int bulletproofAmount) {
        this.bulletproofAmount = bulletproofAmount;
        return this;
    }

    public UPlayer setPoliceBulletproofAmount(int policeBulletproofAmount) {
        this.policeBulletproofAmount = policeBulletproofAmount;
        return this;
    }

    public UPlayer setStorageInventory(String storageInventory) {
        this.storageInventory = storageInventory;
        return this;
    }

    public UPlayer setRentStorage(boolean rentStorage) {
        this.rentStorage = rentStorage;
        return this;
    }

    public UPlayer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UPlayer setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UPlayer setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public UPlayer setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public UPlayer setDiscordVerify(int discordVerify) {
        this.discordVerify = discordVerify;
        return this;
    }

    public UPlayer setDiscordId(String discordId) {
        this.discordId = discordId;
        return this;
    }

    public UPlayer setPremiumAccount(boolean premiumAccount) {
        this.premiumAccount = premiumAccount;
        return this;
    }

    public UPlayer setFFA(boolean FFA) {
        this.isFFA = FFA;
        return this;
    }

    public UPlayer setFfaInventory(String ffaInventory) {
        this.ffaInventory = ffaInventory;
        return this;
    }

    public UPlayer setFfaKills(int ffaKills) {
        this.ffaKills = ffaKills;
        return this;
    }

    public UPlayer setFfaDeaths(int ffaDeaths) {
        this.ffaDeaths = ffaDeaths;
        return this;
    }

    public UPlayer setVotes(int votes) {
        this.votes = votes;
        return this;
    }
}
