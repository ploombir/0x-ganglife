package de.marc.ganglife.playerdatas;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Getter
public class UPlayer {
    public static final Map<UUID, UPlayer> cachedUPlayers = new HashMap<>();
    public final UUID uuid;
    private final playerManager playerManager;

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
    private int houseNumber;
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
        playerManager.loadPlayer(this.uuid).thenAccept(uPlayer -> {
            this.deathTime = uPlayer.get().getDeathTime();
            this.isJail = uPlayer.get().isJail();
            this.cash = uPlayer.get().getCash();
            this.phoneNumber = uPlayer.get().getPhoneNumber();
            this.phoneFlightMode = uPlayer.get().isPhoneFlightMode();
            this.phoneContacts = uPlayer.get().getPhoneContacts();
            this.playerName = uPlayer.get().getPlayerName();
            this.bank = uPlayer.get().getBank();
            this.level = uPlayer.get().getLevel();
            this.levelExp = uPlayer.get().getLevelExp();
            this.faction = uPlayer.get().getFaction();
            this.factionRank = uPlayer.get().getFactionRank();
            this.drink = uPlayer.get().getDrink();
            this.houseNumber = uPlayer.get().getHouseNumber();
            this.houseInventory = uPlayer.get().getHouseInventory();
            this.paydayTime = uPlayer.get().getPaydayTime();
            this.playTime = uPlayer.get().getPlayTime();
            this.weaponLicence = uPlayer.get().isWeaponLicence();
            this.driveLicence = uPlayer.get().isDriveLicence();
            this.firstaidLicence = uPlayer.get().isFirstaidLicence();
            this.jailTime = uPlayer.get().getJailTime();
            this.actReasons = uPlayer.get().getActReasons();
            this.garbageLevel = uPlayer.get().getGarbageLevel();
            this.garbageExp = uPlayer.get().getGarbageExp();
            this.cocaineAmount = uPlayer.get().getCocaineAmount();
            this.weedAmount = uPlayer.get().getWeedAmount();
            this.methAmount = uPlayer.get().getMethAmount();
            this.medicineAmount = uPlayer.get().getMedicineAmount();
            this.bulletproofAmount = uPlayer.get().getBulletproofAmount();
            this.policeBulletproofAmount = uPlayer.get().getPoliceBulletproofAmount();
            this.storageInventory = uPlayer.get().getStorageInventory();
            this.rentStorage = uPlayer.get().isRentStorage();
            this.firstName = uPlayer.get().getFirstName();
            this.lastName = uPlayer.get().getLastName();
            this.birthDate = uPlayer.get().getBirthDate();
            this.gender = uPlayer.get().getGender();
            this.discordVerify = uPlayer.get().getDiscordVerify();
            this.discordId = uPlayer.get().getDiscordId();
            this.premiumAccount = uPlayer.get().isPremiumAccount();
            this.isFFA = uPlayer.get().isFFA();
            this.ffaInventory = uPlayer.get().getFfaInventory();
            this.ffaKills = uPlayer.get().getFfaKills();
            this.ffaDeaths = uPlayer.get().getFfaDeaths();
            this.votes = uPlayer.get().getVotes();
        });
    }

    public void saveData() {
        playerManager.savePlayer(this);
    }

    public UPlayer setDeathTime(int deathTime) {
        this.deathTime = deathTime;
        return this;
    }

    public UPlayer setJail(boolean jail) {
        isJail = jail;
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

    public UPlayer setFactionRank(int factionRank) {
        this.factionRank = factionRank;
        return this;
    }

    public UPlayer setDrink(int drink) {
        this.drink = drink;
        return this;
    }

    public UPlayer setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public UPlayer setHouseInventory(String houseInventory) {
        this.houseInventory = houseInventory;
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
        isFFA = FFA;
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
