package paul.NLPTextDungeon;

import paul.NLPTextDungeon.entities.Dungeon;
import paul.NLPTextDungeon.entities.DungeonRoom;
import paul.NLPTextDungeon.entities.Hero;
import paul.NLPTextDungeon.entities.MetaLocation;
import paul.NLPTextDungeon.entities.parsing.StatementAnalysis;
import paul.NLPTextDungeon.entities.parsing.StatementAnalyzer;
import paul.NLPTextDungeon.utils.VictoryException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Paul Dennis on 8/8/2017.
 */
public class DungeonRunner {

    Dungeon dungeon;
    Hero hero;

    DungeonRoom currentRoom;
    StatementAnalyzer analyzer;
    Scanner scanner;

    boolean done = false;

    static List<String> CLEAR_REQUIRED_FOR_ACTION = Arrays.asList("move", "loot", "plunder", "rescue");

    List<MetaLocation> metaLocations;
    //Solidify interface
    public DungeonRunner () {
        hero = new Hero();
        analyzer = new StatementAnalyzer();
        scanner = new Scanner(System.in);

        dungeon = new Dungeon(1);
        metaLocations = new ArrayList<>();
        metaLocations.add(dungeon);
    }

    public void run () {
        currentRoom = dungeon.getEntrance();
        hero.setLocation(currentRoom);
        currentRoom.addHero(hero);
        System.out.println("Welcome to the " + dungeon.getDungeonName());
        System.out.println("Your goal:");
        System.out.println(dungeon.getGoalDescription());
        mainActionMenu();
    }

    public void mainActionMenu () {
        currentRoom.describeRoom();
        System.out.println("What would you like to do?");
        String response = scanner.nextLine();
        StatementAnalysis analysis = analyzer.analyzeStatement(response);
        if (analysis.isActionable()) {
            try {
                //analysis.printFinalAnalysis();
                String actionWord = analysis.getActionWord();
                if (CLEAR_REQUIRED_FOR_ACTION.contains(actionWord) && !currentRoom.isCleared()) {
                    System.out.println("Oh honey, you have to clear the room of monsters first.");
                } else {
                    if (analysis.getActionParam() != null) {
                        hero.takeAction(actionWord, analysis.getActionParam());
                    } else {
                        hero.takeAction(actionWord);
                    }
                }
            } catch (VictoryException ex) {
                System.out.println("Victory!");
                System.out.println(ex.getMessage());
                System.out.println("The bards will sing of this day.");
                done = true;
            } catch (Exception ex) {
                System.out.println("Let's keep going, but the message was:");
                System.out.println(ex.getMessage() + " and of type " + ex.getClass());

            }
            currentRoom = hero.getLocation();
            if (!done) {
                mainActionMenu();
            }
        } else {
            System.out.println("Could not analyze to an actionable statement.\nComments:");
            analysis.getComments().forEach(System.out::print);
            mainActionMenu();
        }
    }

    public static void main(String[] args) {
        new DungeonRunner().run();
    }
}
