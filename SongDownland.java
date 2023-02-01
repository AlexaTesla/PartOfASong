package song;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SongDownland {
    void SongLaunch (String search) throws IOException {
        playSongInt(search, 1);
    }

    void SongLaunch (String search, int limit) throws IOException {
        playSongInt(search, limit);
    }

    private void playSongInt(String search, int limit) throws IOException {
        String url = buildUrl(search, limit);
        String page = downland(url);

        String artistName = getTag(page, "artistName");
        String trackName = getTag(page, "trackName");
        String previewUrl = getTag(page, "previewUrl");
        System.out.println(artistName + " - " + trackName);
        try (InputStream in = new URL(previewUrl).openStream()) {
            Files.copy(in, Paths.get(trackName + ".m4a"));
        }
        System.out.println("Сохранено!");

        if (!Desktop.isDesktopSupported()) {
            System.out.println("Not sup");
            return;
        }
        Desktop desktop = Desktop.getDesktop();
        File file = new File(trackName + ".m4a");
        desktop.open(file);
    }

    private String getTag(String page, String tagName) {
        int start = page.indexOf(tagName) + tagName.length() + 3;
        int end = page.indexOf("\"", start);
        String value = page.substring(start, end);
        return value;
    }

    private String buildUrl(String search, int limit) {
        String term = search.replaceAll(" ", "+");
        String urll = "https://itunes.apple.com/search?term=";
        String lim = "&limit=" + limit;
        String media = "&media=music";
        StringBuilder builder = new StringBuilder();
        builder.append(urll);
        builder.append(term);
        builder.append(lim);
        builder.append(media);
        return builder.toString();
    }

    String downland(String url) {
        StringBuilder result = new StringBuilder();
        String line;
        URLConnection urlConnection = null;
        try {
            urlConnection = new URL(url).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (InputStream is = urlConnection.getInputStream(); BufferedReader br = new
                BufferedReader(new InputStreamReader(is))) {
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}