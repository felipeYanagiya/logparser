import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import model.Player;
import org.junit.Test;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.*;

/**
 * Created by felipeyanagiya on 7/22/16.
 */
public class ApplicationTest {

    private final Application app = new Application();
    private static final String PLAYER_NICK_NAME = "Player";
    private static final String KILLER_NICK_NAME = "Killer";
    private static final String DEAD_NICK_NAME = "PassedAway";


    @Test
    public void runMainTest() {
        Application.main(null);
    }

    @Test
    public void checkPlayerShouldReturnNewPlayerIfNotFound() {
        Player result = app.checkPlayer(PLAYER_NICK_NAME, Lists.newArrayList());

        assertNotNull(result);
        assertEquals(result.getNickName(), PLAYER_NICK_NAME);
    }

    @Test
    public void checkPlayerShouldReturnPlayerInList() {
        Player expected = new Player(PLAYER_NICK_NAME);
        expected.setKills(3);
        expected.setDeaths(5);

        Player result = app.checkPlayer(PLAYER_NICK_NAME, Lists.newArrayList(expected));

        assertPlayer(expected, result);
    }

    @Test
    public void parseLineWithNewShouldNotAddPlayerToList() {
        List<Player> unaffectedList = Lists.newArrayList();

        app.parseLine(new String[] {"1", "2", "3", "new"}, unaffectedList);

        assertNotNull(unaffectedList);
        assertTrue(unaffectedList.isEmpty());
    }

    @Test
    public void parseLineWithMatchShouldNotAddPlayerToList() {
        List<Player> unaffectedList = Lists.newArrayList();

        app.parseLine(new String[] {"1", "2", "3", "match"}, unaffectedList);

        assertNotNull(unaffectedList);
        assertTrue(unaffectedList.isEmpty());
    }

    @Test
    public void parseLineWithWorldShouldOnlyAddKillCount() {
        List<Player> players = Lists.newArrayList();

        app.parseLine(new String[] {"1", "2", "3", "<world>", "4", DEAD_NICK_NAME, "5"}, players);

        assertNotNull(players);
        assertFalse(players.isEmpty());

        Player dead = Iterables.getFirst(players, null);
        assertNotNull(dead);
        assertEquals(dead.getNickName(), DEAD_NICK_NAME);
        assertEquals(dead.getDeaths(), 1);
    }

    @Test
    public void parseLineShouldEditKillerAndDeadPlayers() {
        List<Player> players = Lists.newArrayList(new Player(KILLER_NICK_NAME));

        app.parseLine(new String[] {"1", "2", "3", KILLER_NICK_NAME, "4", DEAD_NICK_NAME, "5"}, players);

        assertNotNull(players);
        assertFalse(players.isEmpty());

        Player killerEdited = players.stream().filter(new Predicate<Player>() {
            @Override
            public boolean test(Player player) {
                return player.getNickName().equalsIgnoreCase(KILLER_NICK_NAME);
            }
        }).findFirst().orElse(null);

        assertNotNull(killerEdited);
        assertEquals(1, killerEdited.getKills());
        assertEquals(0, killerEdited.getDeaths());

        Player deadEdited = players.stream().filter(new Predicate<Player>() {
            @Override
            public boolean test(Player player) {
                return player.getNickName().equalsIgnoreCase(DEAD_NICK_NAME);
            }
        }).findFirst().orElse(null);

        assertNotNull(deadEdited);
        assertEquals(1, deadEdited.getDeaths());
        assertEquals(0, deadEdited.getKills());
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void parseLineWithWrongInputShouldThrowArrayIndexOutofBoundsException() {
        app.parseLine(new String[] {"bla"}, Lists.newArrayList());

        fail();
    }

    void assertPlayer(Player expected, Player result) {
        assertNotNull(expected);
        assertNotNull(result);
        assertEquals(expected.getNickName(), result.getNickName());
        assertEquals(expected.getDeaths(), result.getDeaths());
        assertEquals(expected.getKills(), expected.getKills());
    }

}
