package song;

import java.io.IOException;
import java.util.Scanner;

public class SongLaunch {
    public static void main(String[] args) throws IOException {
        SongDownland player = new SongDownland();
        Scanner sc = new Scanner(System.in);
        System.out.println("Какую песню ты ищешь? ");
        String str = sc.nextLine();
        player.SongLaunch(str);
    }
}