package solver;

import java.io.FileInputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PackingSolver {
    public static long startTime;

    public static void main(String[] args) {
        startTime = System.currentTimeMillis();

        final Set<String> knownParams = new HashSet<>(Arrays.asList(
                "debug", "file", "greedy", "stupid", "master", "nfdh"
        ));
        final Map<Character, String> shorthand = new HashMap<>(knownParams.size());
        shorthand.put('d', "debug");
        shorthand.put('f', "file");
        shorthand.put('g', "greedy");
        shorthand.put('s', "stupid");
        shorthand.put('n', "nfdh");
        shorthand.put('m', "master");


        final Map<String, List<String>> params = new HashMap<>();

        List<String> values = null;
        for (String arg : args) {
            if (arg.charAt(0) == '-') {
                int length = arg.length();
                if (length < 2) {
                    System.err.printf("Invalid argument: %s\n", arg);
                    System.exit(-1);
                }

                String param;
                if (arg.charAt(1) == '-') {
                    values = new ArrayList<>();
                    params.put(arg.substring(2), values);
                } else {
                    for (int i = 1; i < length; i++) {
                        char c = arg.charAt(i);
                        if (!shorthand.containsKey(c)) {
                            System.err.printf("Unknown shorthand flag: %s\n", c);
                            System.exit(-1);
                        }
                        param = shorthand.get(c);
                        if (!params.containsKey(param)) {
                            values = new ArrayList<>();
                            params.put(param, values);
                        } else {
                            values = params.get(param);
                        }
                    }
                }
            } else if (values != null) {
                values.add(arg);
            } else {
                System.err.printf("Invalid argument: %s\n", arg);
                System.exit(-1);
            }
        }

        Set<String> specified = new HashSet<>(params.size());
        params.forEach((param, v) -> {
            if (!knownParams.contains(param)) {
                System.err.printf("Unknown parameter: %s\n", param);
                System.exit(-1);
            }
            specified.add(param);
        });

        Logger log = Logger.getGlobal();
        if (params.containsKey("d") || params.containsKey("debug")) {
            log.setLevel(Level.ALL);
        } else {
            log.setLevel(Level.OFF);
        }

        Case c = null;
        try {
            if (params.containsKey("file")) {
                c = new Case(new FileInputStream(params.get("file").get(0)));
            } else {
                c = new Case(System.in);
            }
        } catch (Exception e) {
            log.severe("Failed to parse case: " + e.getMessage());
            System.exit(-1);
        }

        Packer p;
        if (params.containsKey("greedy")) {
            p = new GreedyPacker();
        } else if (params.containsKey("stupid")) {
            p = new StupidPacker();
        } else if (params.containsKey("master")) {
            p = new MasterPacker();
        } else if (params.containsKey("nfdh")) {
            p = new NFDHPacker();
        }  else {
            // default
            p = new GreedyPacker();
        }
        
        Solution s = null;
        try {
            s = new Solution(c, p);
        } catch (Exception e) {
            log.severe("Failed to solve case: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }

        System.out.println(s);
        log.info("Running time: " + (System.currentTimeMillis() - startTime) + "ms");
    }
}
