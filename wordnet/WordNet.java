import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordNet {
    // Maps each noun to its corresponding set of synset IDs
    private final Map<String, Set<Integer>> nounToIds;
    // Maps each synset ID to its corresponding synset (group of synonyms)
    private final Map<Integer, String> idToSynset;
    // Shortest Common Ancestor utility for finding the shortest ancestral path
    private final ShortestCommonAncestor sca;

    // Constructor takes the name of the two input files: synsets and hypernyms
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("Arguments to the"
                                                       + " constructor cannot "
                                                       + "be null");
        }
        nounToIds = new HashMap<>();
        idToSynset = new HashMap<>();

        // Parsing synsets file
        In inSynsets = new In(synsets);
        while (inSynsets.hasNextLine()) {
            String[] fields = inSynsets.readLine().split(",");
            int id = Integer.parseInt(fields[0]);
            idToSynset.put(id, fields[1]);
            for (String noun : fields[1].split(" ")) {
                nounToIds.computeIfAbsent(noun, k -> new HashSet<>()).add(id);
            }
        }
        inSynsets.close();

        // Initializing digraph with the number of vertices equal to number of synsets
        // Digraph representing WordNet's hypernyms relationships
        Digraph wordnetDigraph = new Digraph(idToSynset.size());

        // Parsing hypernyms file
        In inHypernyms = new In(hypernyms);
        while (inHypernyms.hasNextLine()) {
            String[] fields = inHypernyms.readLine().split(",");
            int synsetId = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                int hypernymId = Integer.parseInt(fields[i]);
                wordnetDigraph.addEdge(synsetId, hypernymId);
            }
        }
        inHypernyms.close();

        DirectedCycle cycleFinder = new DirectedCycle(wordnetDigraph);
        if (cycleFinder.hasCycle()) {
            throw new IllegalArgumentException("The digraph is not acyclic.");
        }

        // Checking if the graph is rooted
        if (!isRootedDAG(wordnetDigraph)) {
            throw new IllegalArgumentException("The digraph is not rooted.");
        }

        sca = new ShortestCommonAncestor(wordnetDigraph);
    }

    // Checks if the digraph is a rooted DAG
    private boolean isRootedDAG(Digraph G) {
        int roots = 0;
        // Assuming outdegree is a method that returns the outdegree of a vertex
        // in constant time
        for (int i = 0; i < G.V(); i++) {
            if (G.outdegree(i) == 0) {
                roots++;
                if (roots > 1) { // There should be only one root
                    return false;
                }
            }
        }
        return roots == 1; // Confirming there is exactly one root
    }


    // Returns the set of all WordNet nouns
    public Iterable<String> nouns() {
        return nounToIds.keySet();
    }

    // Determines if the word is a WordNet noun
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Argument to isNoun() is null");
        }
        return nounToIds.containsKey(word);
    }

    // Returns a synset (second field of synsets.txt) that is the shortest
    // common ancestor of noun1 and noun2
    public String sca(String noun1, String noun2) {
        validateNoun(noun1);
        validateNoun(noun2);
        Set<Integer> ids1 = nounToIds.get(noun1);
        Set<Integer> ids2 = nounToIds.get(noun2);
        int ancestorId = sca.ancestorSubset(ids1, ids2);
        return idToSynset.get(ancestorId);
    }

    // Calculates the distance between noun1 and noun2
    public int distance(String noun1, String noun2) {
        validateNoun(noun1);
        validateNoun(noun2);
        Set<Integer> ids1 = nounToIds.get(noun1);
        Set<Integer> ids2 = nounToIds.get(noun2);
        return sca.lengthSubset(ids1, ids2);
    }


    // Validates that the given noun exists within the WordNet
    private void validateNoun(String noun) {
        if (noun == null || !isNoun(noun)) {
            throw new IllegalArgumentException("'" + noun + "' is not a WordNet noun");
        }
    }

    // Unit testing (required)
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
        // tests
        StdOut.println("Nouns in WordNet: " + wordNet.nouns());
        String nounA = "bird";
        String nounB = "worm";
        if (wordNet.isNoun(nounA) && wordNet.isNoun(nounB)) {
            StdOut.println("Distance between " + nounA + " and " + nounB + ": "
                                   + wordNet.distance(nounA, nounB));
            StdOut.println("SCA of " + nounA + " and " + nounB + ": "
                                   + wordNet.sca(nounA, nounB));
        }
        else {
            StdOut.println("One of the nouns is not in WordNet");
        }

        String testNoun = "bird";
        if (wordNet.isNoun(testNoun)) {
            StdOut.println("isNoun test passed for existing noun");
        }
        else {
            StdOut.println("isNoun test failed for existing noun");
        }


    }
}
