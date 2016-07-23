import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import model.Player;
import org.apache.commons.collections4.comparators.ComparatorChain;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Created by felipeyanagiya on 7/22/16.
 */
public class Application {

    public static void main(String[] args) {

        Application application = new Application();

        List<Player> players = application.getFile("input-test.txt");

        rankPlayers(players);

        players.forEach(new Consumer<Player>() {
            @Override
            public void accept(Player player) {
                System.out.println(player.toString());
            }
        });
    }

    private static void rankPlayers(List<Player> players) {
        ComparatorChain chain = new ComparatorChain();
        chain.addComparator(new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {

                return o2.getKills() - o1.getKills();
            }
        });
        chain.addComparator(new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return o1.getDeaths() - o2.getDeaths();
            }
        });
        players.sort(chain);
    }


    private List<Player> getFile(String fileName) {

        List<Player> players = Lists.newArrayList();

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] words = splitWords(line);
                parseLine(words, players);
            }
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return players;

    }

    String[] splitWords(String line) {
        return line.split(" ");
    }

    void parseLine(String[] words, List<Player> players) {
        if (words[3].equalsIgnoreCase("new") || words[3].equalsIgnoreCase("match")) {
            return;
        }

        if (words[3].equalsIgnoreCase("<world>")) {
            Player player = checkPlayer(words[5], players);
            player.addDeath();

            players.add(player);
        } else {
            Player killer = checkPlayer(words[3], players);
            killer.addKill();
            killer.checkKillStreak();
            players.add(killer);

            Player dead = checkPlayer(words[5], players);
            dead.addDeath();
            dead.resetKillStreak();
            players.add(dead);
        }
    }

    Player checkPlayer(final String word, List<Player> players) {
        Player editablePlayer = players
            .stream()
            .filter(player -> player.getNickName().equalsIgnoreCase(word))
            .findFirst()
            .orElse(new Player(word));

        players.remove(editablePlayer);

        return editablePlayer;
    }
}
