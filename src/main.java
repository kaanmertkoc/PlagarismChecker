import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.println("Give the name of the file that you want to check the similarity rate: ");
            String fileName = in.nextLine();
            if(fileName.equals("") || fileToList(fileName) == null) {
                System.out.println("You did not give any name. Try again. ");
                fileName = in.nextLine();
            }
            printResults(fileName);
            System.out.println("Press q to quit. Or press 1 to give another file. ");
            if(in.nextLine().equals("q"))
                return;
        }
        }
    static void printResults(String filename) {
            ArrayList<List<String>> mainDocument = fileToList("main.txt");
            ArrayList<List<String>> otherDocument = fileToList(filename);
            List<Similarity> similarities = new ArrayList<>();

            // This is for paragraphs
            for (List<String> firstDocumentsSentence : mainDocument) {
                findSimilarites(firstDocumentsSentence, otherDocument, similarities);

            }
            Collections.sort(similarities, Collections.reverseOrder());
            double sum = 0.0;
            for (Similarity s : similarities) {
                sum += s.getSimilarityRate();
            }
            System.out.println("Similarity rate is " + (sum / similarities.size()));
            System.out.println("Most Similar sentence is " + similarities.get(0).getSentence() + " with similarity rate " + similarities.get(0).getSimilarityRate());
            System.out.println("Second Most Similar Sentence " + similarities.get(1).getSentence() + " with similarity rate " + similarities.get(1).getSimilarityRate());
            System.out.println("Third Most Similar Sentence " + similarities.get(2).getSentence() + " with similarity rate " + similarities.get(2).getSimilarityRate());
            System.out.println("Fourth Most Similar Sentence " + similarities.get(3).getSentence() + " with similarity rate " + similarities.get(3));
            System.out.println("Fifth Most Similar Sentence " + similarities.get(4).getSentence() + " with similarity rate " + similarities.get(4).getSimilarityRate());
    }
    /**
     *
     * @param s,  is a sentence in main document to compare
     * @param se, is a document where it consists a lot of senteces, article
     * @param similarities, Similarity array
     * @return, max similarity between s and se's senteces
     */
    static void findSimilarites(List<String> s, ArrayList<List<String>> se, List<Similarity> similarities) {
        double max = 0;
        for(List<String> secondSentence: se) {
            double count = countHowManySameWord(s, secondSentence);
            if(count > max)
                max = count;
        }
        StringBuilder sentence = new StringBuilder();
        for(String word: s)
            sentence.append(word).append(" ");
        similarities.add(new Similarity(sentence.toString(), max));
    }
    // TODO implement folder text thing. Write comments. Folder thing is implemented in vs code.
    /**
     * This method counts multiple consecutive words.
     * If there is a one different word among this consecutive words it can still count.
     * It only counts when a word is equal and right side of the sentence.
     * @param firstSentence a list of words. Arraylist string.
     * @param secondSentence another sentences that will be compared. Arraylist string
     * @return a
     */
    static double countHowManySameWord(List<String> firstSentence, List<String> secondSentence) {
        int index = -1;
        int count = 0;
        for (String s : firstSentence) {
            for (int j = 0; j < secondSentence.size(); j++) {
                if (s.equals(secondSentence.get(j)) && j > index) {
                    index = j;
                    count++;
                    break;
                }
            }
        }
        return (100.0 * count) / secondSentence.size();
    }

    /**
     * This method takes a file name and converts it to double dimension Arraylist with type of String to String.
     * Where each index is a sentence. By sentence i mean a List of strings that is split by spaces. With this way it
     * is way easier to compare.
     * @param fileName name of the file type of string.
     * @return double dimension array list where each column is an ArrayList string.
     */
    static ArrayList<List<String>> fileToList(String fileName) {
        ArrayList<List<String>> filelist = new ArrayList<List<String>>();
        try {
            File myobj = new File(fileName);
            Scanner reader = new Scanner(myobj);
            while(reader.hasNextLine()) {
                String data = reader.nextLine();
                if(!data.isEmpty()) {
                    String[] sentences = data.split("\\.");
                    for (String sentence : sentences) {
                        String[] sentencesWord = sentence.split(" ");
                        List<String> sentencesWordList = Arrays.asList(sentencesWord);
                        filelist.add(sentencesWordList);
                    }
                }
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("Could not find this file. Please give another one.");
            return null;
        }
        return filelist;
    }
}
