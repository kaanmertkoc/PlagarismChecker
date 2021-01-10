import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This program takes a main text and a folder which contains multiple text files then compares it's similarities with
 * main text file. Prints the results.
 */
public class main {

    public static void main(String[] args) {

        File mainFile = getMainFile();
        ArrayList<File> files = getFolder();
        findAllSimilarities(mainFile, files);

    }

    /**
     * This method handles folder input. If input is not correct it will ask until input is true.
     * @return an array list of files representing files in the folder.
     */
    static ArrayList<File> getFolder() {
        Scanner in = new Scanner(System.in);
        System.out.println("Give a path to your folder.");
        String pathToFolder = in.next();
        File folder = new File(pathToFolder);
        ArrayList<File> files = new ArrayList<>();
        while(!folder.exists() || !folder.isDirectory()) {
            System.out.println("Your input is invalid please give another one: ");
            folder = new File(in.nextLine());
        }
        for(final File fileEntry: folder.listFiles()) {
           String nameOfFile = fileEntry.toString();
           if(nameOfFile.substring(nameOfFile.length() - 3).equals("txt")) {
               files.add(fileEntry);
           }
        }
        return files;
    }

    /**
     * This method loop's through every file in given folder then prints the result.
     * @param mainFile main file that will be compared with type of File.
     * @param files an arraylist of files representing files in the folder.
     */
    static void findAllSimilarities(File mainFile, ArrayList<File> files) {
        int count = 1;
        for(File f: files) {
            printResults(f, Objects.requireNonNull(fileToList(mainFile)), count++);
        }
    }

    /**
     * This method handles main file input. Given desired path, it will return a file with that path but if
     * input is invalid it will loop until input is true.
     * @return main file with type of File.
     */
    static File getMainFile() {
        Scanner in = new Scanner(System.in);
        System.out.println("Give a path to the main file: ");
        String mainFilePath = in.nextLine();
        File mainFile = new File(mainFilePath);
        while(!mainFile.exists() || !mainFilePath.substring(mainFilePath.length() - 3).equals("txt")) {
            System.out.println("Given path either does not exists or not a txt file. Please give a valid one: " );
            mainFilePath = in.nextLine();
            mainFile = new File(mainFilePath);
        }
        return mainFile;
    }

    /**
     * This method prints the similarities with main and given document in desired format.
     * @param otherFile file that will be compared with main file type of File.
     * @param mainDocument main file with type of File
     * @param count an integer for how many documents are compared.
     */
    static void printResults(File otherFile, ArrayList<List<String>> mainDocument, int count) {
            ArrayList<List<String>> otherDocument = fileToList(otherFile);
            List<Similarity> similarities = new ArrayList<>();

            // This is for paragraphs
            for (List<String> firstDocumentsSentence : mainDocument) {
                findSimilarites(firstDocumentsSentence, otherDocument, similarities);
            }
            similarities.sort(Collections.reverseOrder());
            double sum = 0.0;
            for (Similarity s : similarities) {
                sum += s.getSimilarityRate();
            }
            System.out.println("Similarity rate is " + (sum / similarities.size()) + " with document " + count);
            System.out.println("Most Similar sentence is " + similarities.get(0).getSentence() + " with similarity rate " + similarities.get(0).getSimilarityRate());
            System.out.println("Second Most Similar Sentence " + similarities.get(1).getSentence() + " with similarity rate " + similarities.get(1).getSimilarityRate());
            System.out.println("Third Most Similar Sentence " + similarities.get(2).getSentence() + " with similarity rate " + similarities.get(2).getSimilarityRate());
            System.out.println("Fourth Most Similar Sentence " + similarities.get(3).getSentence() + " with similarity rate " + similarities.get(3).getSimilarityRate());
            System.out.println("Fifth Most Similar Sentence " + similarities.get(4).getSentence() + " with similarity rate " + similarities.get(4).getSimilarityRate());
    }
    /**
     *
     * @param s,  is a sentence in main document to compare
     * @param se, is a document where it consists a lot of sentences, article
     * @param similarities, Similarity array
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
    /**
     * This method counts multiple consecutive words.
     * If there is a one different word among this consecutive words it can still count.
     * It only counts when a word is equal and right side of the sentence.
     * @param firstSentence a list of words. Arraylist string.
     * @param secondSentence another sentences that will be compared. Arraylist string
     * @return a percentage of how many words are same with type of double.
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
     * @param fileEntry object of the file type of File.
     * @return double dimension array list where each column is an ArrayList string.
     */
    static ArrayList<List<String>> fileToList(File fileEntry) {
        ArrayList<List<String>> fileList = new ArrayList<List<String>>();
        try {
            Scanner reader = new Scanner(fileEntry);
            while(reader.hasNextLine()) {
                String data = reader.nextLine();
                if(!data.isEmpty()) {
                    String[] sentences = data.split("\\.");
                    for (String sentence : sentences) {
                        String[] sentencesWord = sentence.split(" ");
                        List<String> sentencesWordList = Arrays.asList(sentencesWord);
                        fileList.add(sentencesWordList);
                    }
                }
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("Could not find this file. Please give another one.");
        }
        return fileList;
    }
}
