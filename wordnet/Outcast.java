import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        if (wordnet == null) {
            throw new IllegalArgumentException("WordNet object cannot be null");
        }
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        if (nouns == null) {
            throw new IllegalArgumentException("Array of nouns cannot be null");
        }
        if (nouns.length < 2) {
            throw new IllegalArgumentException("At least two nouns are required");
        }

        int maxDistance = -1;
        String outcast = null;

        for (String noun : nouns) {
            int distanceSum = 0;
            for (String otherNoun : nouns) {
                if (!noun.equals(otherNoun)) {
                    distanceSum += wordnet.distance(noun, otherNoun);
                }
            }

            if (distanceSum > maxDistance) {
                maxDistance = distanceSum;
                outcast = noun;
            }
        }

        return outcast;
    }

    // test client (see below)
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
        Outcast outcast = new Outcast(wordNet);

        // Test with known nouns
        String[] nouns = { "horse", "zebra", "cat", "bear", "table" };
        StdOut.println("Outcast of the group: " + outcast.outcast(nouns));

        // Additional test with different set of nouns
        String[] moreNouns = { "coffee", "sugar", "tea", "beer", "rice" };
        StdOut.println("Outcast of the group: " + outcast.outcast(moreNouns));

        // Edge case with only two nouns
        String[] twoNouns = { "Python", "Java" };
        StdOut.println("Outcast of the group: " + outcast.outcast(twoNouns));
    }
}
