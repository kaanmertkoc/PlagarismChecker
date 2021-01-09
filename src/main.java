import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while(in.nextLine().equals("q")) {
            System.out.println("Give the name of the file that you want to check the similarity rate: ");
            String fileName = in.nextLine();
            if(fileName.equals("")) {
                System.out.println("You did not give any name. Try again. ");
                fileName = in.nextLine();
            }
            printResults(fileName);
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
    static ArrayList<List<String>> fileToList(String fileName) {
        ArrayList<List<String>> filelist = new ArrayList<List<String>>();
        try {
            File myobj = new File(fileName);
            Scanner reader = new Scanner(myobj);
            while(reader.hasNextLine()) {
                String data = reader.nextLine();
                if(!data.isEmpty()) {
                    String[] sentences = data.split("\\.");
                    for (int i = 0; i < sentences.length; i++) {
                        String[] sentecesWord = sentences[i].split(" ");
                        List<String> sentecesWordList =  Arrays.asList(sentecesWord);
                        filelist.add(sentecesWordList);
                    }
                }
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }
        return filelist;
    }
    static class Similarity implements Comparable {
        private String sentence;
        private double similarityRate;
        Similarity(String sentence, double similarityRate) {
            this.sentence = sentence;
            this.similarityRate = similarityRate;
        }

        public String getSentence() { return sentence; }
        public void setSentence(String sentence) { this.sentence = sentence; }
        public double getSimilarityRate() { return similarityRate; }
        public void setSimilarityRate(double similarityRate) { this.similarityRate = similarityRate; }

        @Override
        public String toString() {
            return "Sentence is " + this.sentence + " similarity rate is " + this.similarityRate;
        }
        @Override
        public int compareTo(Object o) {
            int value = -1;
            if(o instanceof  Similarity) {
                Similarity s = (Similarity) o;
                value = Double.compare(similarityRate, ((Similarity) o).getSimilarityRate());
            }
            return value;
        }
        @Override
        public boolean equals(Object obj) {
            boolean value = false;
            if(obj instanceof  Similarity) {
                Similarity s = (Similarity) obj;
                value = this.sentence.equals(s.sentence) && this.similarityRate == s.similarityRate;
            }
            return value;
        }
    }
}
