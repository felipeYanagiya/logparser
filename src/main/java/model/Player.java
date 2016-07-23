package model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Map;

/**
 * Created by felipeyanagiya on 7/22/16.
 */
public class Player {

    private String nickName;
    private int kills = 0;
    private int deaths = 0;
    private int killStreak = 0;
    private boolean killStreakAward;
    private boolean noDeathAward;
    private Map<String, Integer> weaponKills;


    public Player(String word) {
        this.setNickName(word);
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getKillStreak() {
        return killStreak;
    }

    public void setKillStreak(int killStreak) {
        this.killStreak = killStreak;
    }

    public boolean isKillStreakAward() {
        return killStreakAward;
    }

    public void setKillStreakAward(boolean killStreakAward) {
        this.killStreakAward = killStreakAward;
    }

    public boolean isNoDeathAward() {
        return noDeathAward;
    }

    public void setNoDeathAward(boolean noDeathAward) {
        this.noDeathAward = noDeathAward;
    }

    public void addDeath() {
        this.setDeaths(this.getDeaths() + 1);
    }

    //TODO add +1 to weapon preference
    public void addKill() {
        this.setKills(this.getKills() + 1);
    }

    //TODO check if user get award and sum +1 to killStreak
    public void checkKillStreak() {

    }

    //TODO reset killstreak number
    public void resetKillStreak() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return new EqualsBuilder()
            .append(nickName, player.nickName)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(nickName)
            .toHashCode();
    }

    @Override
    public String toString() {
        return "Player {" +
            " nickName= '" + nickName + "'," +
            " kills= '" + kills + "'," +
            " deaths= '" + deaths + "'," +
            " }";
    }
}
