package com.example.kanehara.instatext;

import java.util.ArrayList;

/**
 * Created by kanehara on 12/6/15.
 */
public class TextStringSingleton {
    private static TextStringSingleton instance = null;
    private ArrayList<String> niceTexts = new ArrayList<>();
    private ArrayList<String> meanTexts = new ArrayList<>();

    private ArrayList<String> meanPrefixes = new ArrayList<>();
    private ArrayList<String> meanAdjs = new ArrayList<>();
    private ArrayList<String> meanAdverbs = new ArrayList<>();


    protected TextStringSingleton() {
        // nice texts
        niceTexts.add("Love youuuuuu <3");
        niceTexts.add("You're so beautiful :)");
        niceTexts.add("You're the best");
        niceTexts.add("I like your butt");
        niceTexts.add("You're sexy ;)");
        niceTexts.add("Did you sit in a pile of sugar? Cause you have a pretty sweet ass.");
        niceTexts.add("If you were a vegetable you'd be a cute-cumber.");
        niceTexts.add("Does your left eye hurt? Because you've been looking right all day.");

        // mean texts
        meanTexts.add("You're a butt muncher >:(");
        meanTexts.add("You poop licker");
        meanTexts.add("You smell bad");
        meanTexts.add("You slimy turd captain");
        meanTexts.add("You're nothing but a stinky toot cabbage");
        meanTexts.add("You're a bawdy monkey licking pancake");

        // mean prefixes
        meanPrefixes.add("You're worse than a ");
        meanPrefixes.add("You're smellier than a ");
        meanPrefixes.add("Go suck a ");
        meanPrefixes.add("You're such a fucking ");
        meanPrefixes.add("You remind me of a ");
        // mean 1
        meanAdjs.add("crusty ");
        meanAdjs.add("decrepic ");
        meanAdjs.add("stupid ");
        meanAdjs.add("moronic ");
        meanAdjs.add("greasy ");
        meanAdjs.add("poxy ");
        meanAdjs.add("ugly ");
        meanAdjs.add("smelly ");
        meanAdjs.add("putrid ");
        meanAdjs.add("shitty ");
        meanAdjs.add("assinine ");
        meanAdjs.add("sickening ");
        meanAdjs.add("lazy ");
        meanAdjs.add("insecure ");
        meanAdjs.add("idiotic ");
        meanAdjs.add("slimy ");
        meanAdjs.add("slutty ");
        meanAdjs.add("pompous ");
        meanAdjs.add("communist ");
        meanAdjs.add("dicknose ");
        meanAdjs.add("racist ");
        meanAdjs.add("elitist ");
        meanAdjs.add("white trash ");
        meanAdjs.add("butterface ");
        meanAdjs.add("tone deaf ");
        meanAdjs.add("creepy ");
        meanAdjs.add("cunt ");
        meanAdjs.add("shit ");
        meanAdjs.add("turd ");
        meanAdjs.add("fuck ");
        meanAdjs.add("ass ");
        meanAdjs.add("");
        meanAdjs.add("");
        meanAdjs.add("");
        meanAdjs.add("");
        meanAdjs.add("");
        meanAdjs.add("");

        meanAdverbs.add("pie-eating ");
        meanAdverbs.add("drug-loving ");

    }

    public static TextStringSingleton getInstance() {
        if (instance == null)
            instance = new TextStringSingleton();
        return instance;
    }

    public ArrayList<String> getNiceTexts() {
        return this.niceTexts;
    }
    public ArrayList<String> getMeanTexts() {
        return this.meanTexts;
    }
    public ArrayList<String> getMeanPrefixes() { return this.meanPrefixes; }
    public ArrayList<String> getMeanAdjs() { return this.meanAdjs; }
    public ArrayList<String> getMeanAdverbs() { return this.meanAdverbs; }

}
