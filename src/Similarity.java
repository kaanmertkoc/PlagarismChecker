/**
 * This class is created for to store a sentence and its similarity rate to given document.
 * It implements Comparable interface which has compareTo and equals methods.
 * It has two private data field and setter, getter methods.
 * Also this class overwrote toString method to print it's values.
 */
public class Similarity implements Comparable {
    private String sentence;
    private double similarityRate;

    /**
     * This is Similarity classes only constructor. It takes two variable and assigns it to needed places.
     * @param sentence, Sentence of the main document with type of String
     * @param similarityRate, Similarity rate to this sentence to another with type of double
     */
    Similarity(String sentence, double similarityRate) {
        this.sentence = sentence;
        this.similarityRate = similarityRate;
    }

    /**
     * @return gets the sentence which is a string
     */
    public String getSentence() { return sentence; }

    /**
     * @param sentence sets the sentence
     */
    public void setSentence(String sentence) { this.sentence = sentence; }

    /**
     *
     * @return gets similarity rate which is double
     */
    public double getSimilarityRate() { return similarityRate; }

    /**
     *
     * @param similarityRate sets the similarity rate
     */
    public void setSimilarityRate(double similarityRate) { this.similarityRate = similarityRate; }

    /**
     * This method is overwritten to print its values.
     * @return value of sentence and the similarity rate in a String
     */
    @Override
    public String toString() {
        return "Sentence is " + this.sentence + " similarity rate is " + this.similarityRate;
    }

    /**
     * This method is overwritten to specify which object is greater than the other.
     * Since Similarity class is a class that is written by me, Java can not know how to compare it
     * So we simply say if other object's similarity rate is greater than this, it means object is greater.
     * @param o any Object, in order to compare this object needs to be a Similarity classes object.
     * @return a positive number if this objects similarity rate is greater than the input object o.
     * Zero if they are equal. Negative number if o's similarity rate is greater than this object's rate.
     */
    @Override
    public int compareTo(Object o) {
        int value = -1;
        if(o instanceof  Similarity) {
            Similarity s = (Similarity) o;
            value = Double.compare(similarityRate, ((Similarity) o).getSimilarityRate());
        }
        return value;
    }

    /**
     * This method is overwritten to specify if given object is equal to this object.
     * @param obj any object, it needs to be a Similarity classes object to be equal.
     * @return True if obj is equal to this object, False otherwise.
     */
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