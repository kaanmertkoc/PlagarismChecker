import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class main {
    public static void main(String[] args) {
        List<String> mainDocument = fileToList("main.txt");
        List<String> document1 = fileToList("document1.txt");
        List<Similarity> similarities = new ArrayList<>();
        int[] checkCount = {0};
        double sum = 0.0;
        int paragprahCount = 0;
        // This is for paragraphs
        for(String s: mainDocument) {
            for(String se: document1) {
                sum += findSimilarites(s, se, similarities, checkCount);
            }
            paragprahCount++;
        }
        System.out.println("Similarity rate is " + sum / (document1.size() * 2));
        System.out.println(document1.size());
        for(Similarity s: similarities) {
                System.out.println(s);
        }

    }
    // TODO debug below shitty code. Currenlty program prints the most similar rated sentence but it can not prints whole documents similarity rate
    static double findSimilarites(String s, String se, List<Similarity> similarities, int[] checkCount) {
        double sum = 0.0;
        int sentenceCount = 0;
        String[] arr = s.split("\\.");
        String[] arr2 = se.split("\\.");
        int index = -1;
        int count = 0;
        double max = 0.0;
        // This is for sentences
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr2.length; j++) {
                String[] arr3 = arr[i].split(" ");
                String[] arr4 = arr2[j].split(" ");
                    for (int k = 0; k < arr3.length; k++) {
                        for (int l = 0; l < arr4.length; l++) {
                            if (arr3[k].equals(arr4[l]) && l > index) {
                                index = l;
                                count++;
                            }
                        }
                    }
                    double similarityRate = (100.0 * count) / arr4.length;
                    sentenceCount++;
                    sum += similarityRate;
                    if (similarities.size() < 5) {
                        similarities.add(new Similarity(arr2[j], count, similarityRate));
                    } else {
                        Similarity t = new Similarity(arr2[j], count, similarityRate);
                        int appIndex = findAppropiateIndex(similarities, t);
                        if (appIndex != -1 && !similarities.contains(t)) {
                            similarities.add(appIndex, t);
                            similarities.remove(similarities.size() - 1);
                        }
                    }
                    index = -1;
                    count = 0;

                checkCount[0]++;
            }
        }
        if(sum == 0.0 || sentenceCount == 0) {
            return 0.0;
        }
        else {
            return sum / sentenceCount;
        }
    }
    static int findAppropiateIndex(List<Similarity> similarities, Similarity t) {
        for (int i = 0; i < similarities.size(); i++) {
            if(similarities.get(i).getSimilarityRate() < t.getSimilarityRate()) {
                return i;
            }
        }
        return -1;
    }
    static List<String> fileToList(String fileName) {
        List<String> fileList = new ArrayList<>();
        try {
            File myObj = new File(fileName);
            Scanner reader = new Scanner(myObj);
            while(reader.hasNextLine()) {
                String data = reader.nextLine();
                if(!data.isEmpty()) {
                    fileList.add(data);
                }

            }
        }
        catch(FileNotFoundException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }
        return fileList;
    }
    static class Similarity implements Comparable {
        private String sentence;
        private int similarity;
        private double similarityRate;
        Similarity(String sentence, int similarity, double similarityRate) {
            this.sentence = sentence;
            this.similarity = similarity;
            this.similarityRate = similarityRate;
        }

        public String getSentence() { return sentence; }
        public void setSentence(String sentence) { this.sentence = sentence; }
        public int getSimilarity() { return similarity; }
        public void setSimilarity(int similarity) { this.similarity = similarity; }
        public double getSimilarityRate() { return similarityRate; }
        public void setSimilarityRate(double similarityRate) { this.similarityRate = similarityRate; }

        @Override
        public String toString() {
            return "Sentence is " + this.sentence + " similarity is " + this.similarity + " similarity rate is " + this.similarityRate;
        }
        @Override
        public boolean equals(Object obj) {
            boolean value = false;
            if(obj instanceof  Similarity) {
                Similarity s = (Similarity) obj;
                value = this.similarity == s.similarity && this.sentence.equals(s.sentence) && this.similarityRate == s.similarityRate;
            }
            return value;
        }

        @Override
        public int compareTo(Object o) {
            int value = -1;
            if(o instanceof  Similarity) {
                Similarity s = (Similarity) o;
                value = Integer.compare(similarity, ((Similarity) o).similarity);
            }
            return value;
        }
    }
}
