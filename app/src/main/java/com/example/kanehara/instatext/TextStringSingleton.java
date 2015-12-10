package com.example.kanehara.instatext;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by kanehara on 12/6/15.
 */
public class TextStringSingleton {
    private static TextStringSingleton instance = null;
    private HashSet<String> niceTexts = new HashSet<>();
    private HashSet<String> meanTexts = new HashSet<>();

    private HashSet<String> meanPrefixes = new HashSet<>();
    private HashSet<String> meanAdjs = new HashSet<>();
    private HashSet<String> meanNouns1 = new HashSet<>();
    private HashSet<String> meanNouns2 = new HashSet<>();
    private HashSet<String> meanVerbs = new HashSet<>();

    protected TextStringSingleton() {
        initMean();
        initNice();
    }

    private void initMean() {
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
        meanPrefixes.add("You're such a fricking ");
        meanPrefixes.add("You remind me of a ");

        // mean adjectives
        meanAdjs.add("crusty");
        meanAdjs.add("decrepic");
        meanAdjs.add("stupid");
        meanAdjs.add("moronic");
        meanAdjs.add("greasy");
        meanAdjs.add("poxy");
        meanAdjs.add("ugly");
        meanAdjs.add("smelly");
        meanAdjs.add("putrid");
        meanAdjs.add("shitty");
        meanAdjs.add("assinine");
        meanAdjs.add("lazy");
        meanAdjs.add("insecure");
        meanAdjs.add("idiotic");
        meanAdjs.add("slimy");
        meanAdjs.add("slutty");
        meanAdjs.add("pompous");
        meanAdjs.add("white trash");
        meanAdjs.add("tone deaf");
        meanAdjs.add("creepy");
        meanAdjs.add("turd");
        meanAdjs.add("fuck");
        meanAdjs.add("lilly-livered");
        meanAdjs.add("rotten");
        meanAdjs.add("stinky");
        meanAdjs.add("lame");
        meanAdjs.add("dim witted");
        meanAdjs.add("funky");
        meanAdjs.add("steamy");
        meanAdjs.add("drizzly");
        meanAdjs.add("grizzly");
        meanAdjs.add("squirty");
        meanAdjs.add("uptight");
        meanAdjs.add("hairy");
        meanAdjs.add("husky");
        meanAdjs.add("arrogant");
        meanAdjs.add("nippy");
        meanAdjs.add("chunky");
        meanAdjs.add("drooling");
        meanAdjs.add("sickening");
        meanAdjs.add("base court");
        meanAdjs.add("drug loving");
        meanAdjs.add("beef witted");
        meanAdjs.add("beetle headed");
        meanAdjs.add("boil brained");
        meanAdjs.add("clay brained");
        meanAdjs.add("cross eyed");
        meanAdjs.add("dread bolted");
        meanAdjs.add("earth vexing");
        meanAdjs.add("elf skinned");
        meanAdjs.add("fat kidneyed");
        meanAdjs.add("fen sucked");
        meanAdjs.add("flap mouthed");
        meanAdjs.add("fly bitten");
        meanAdjs.add("folly fallen");
        meanAdjs.add("fool born");
        meanAdjs.add("full gorged");
        meanAdjs.add("guts griping");
        meanAdjs.add("half faced");
        meanAdjs.add("hasty witted");

        // mean nouns 1
        meanNouns1.add("cunt");
        meanNouns1.add("shit");
        meanNouns1.add("dicknose");
        meanNouns1.add("racist");
        meanNouns1.add("elitist");
        meanNouns1.add("butterface");
        meanNouns1.add("communist");
        meanNouns1.add("pie");
        meanNouns1.add("bat");
        meanNouns1.add("butt");
        meanNouns1.add("monkey");
        meanNouns1.add("fart");
        meanNouns1.add("snot");
        meanNouns1.add("grub");
        meanNouns1.add("turd");
        meanNouns1.add("ass");
        meanNouns1.add("douche");
        meanNouns1.add("testicle");
        meanNouns1.add("cock");
        meanNouns1.add("sack");
        meanNouns1.add("twat");
        meanNouns1.add("waffle");
        meanNouns1.add("rectum");
        meanNouns1.add("crotch");
        meanNouns1.add("taint");
        meanNouns1.add("boner");
        meanNouns1.add("sphincter");

        // mean verbs
        meanVerbs.add("eating");
        meanVerbs.add("fowling");
        meanVerbs.add("kissing");
        meanVerbs.add("kicking");
        meanVerbs.add("licking");
        meanVerbs.add("munching");
        meanVerbs.add("fucking");
        meanVerbs.add("sniffing");
        meanVerbs.add("spanking");
        meanVerbs.add("flicking");
        meanVerbs.add("loving");
        meanVerbs.add("worshiping");
        meanVerbs.add("guzzling");
        meanVerbs.add("gobbling");
        meanVerbs.add("chugging");
        meanVerbs.add("pouding");
        meanVerbs.add("riding");
        meanVerbs.add("banging");
        meanVerbs.add("peddling");
        meanVerbs.add("fapping");

        // mean noun 2
        meanNouns2.add("squeegee");
        meanNouns2.add("turtle");
        meanNouns2.add("cabbage");
        meanNouns2.add("bomb");
        meanNouns2.add("sniffer");
        meanNouns2.add("binkie");
        meanNouns2.add("stump");
        meanNouns2.add("nugget");
        meanNouns2.add("whistle");
        meanNouns2.add("twig");
        meanNouns2.add("knuckle");
        meanNouns2.add("burger");
        meanNouns2.add("hot dog");
        meanNouns2.add("loaf");
        meanNouns2.add("freckle");
        meanNouns2.add("soldier");
        meanNouns2.add("kernel");
        meanNouns2.add("shingle");
        meanNouns2.add("baggage");
        meanNouns2.add("barnacle");
        meanNouns2.add("bladder");
        meanNouns2.add("boar-pig");
        meanNouns2.add("bugbear");
        meanNouns2.add("canker-blossom");
        meanNouns2.add("clack-dish");
        meanNouns2.add("clot-pole");
        meanNouns2.add("coxcomb");
        meanNouns2.add("codpiece");
        meanNouns2.add("death-token");
        meanNouns2.add("dewberry");
        meanNouns2.add("flap-dragon");
        meanNouns2.add("flax-wench");
        meanNouns2.add("foot-licker");
        meanNouns2.add("fustilarian");
        meanNouns2.add("giglet");
        meanNouns2.add("gudgeon");
        meanNouns2.add("haggard");
        meanNouns2.add("harpy");
        meanNouns2.add("hedge-pig");
        meanNouns2.add("horn-beast");
        meanNouns2.add("hemorrhoid");
        meanNouns2.add("fuckface");
        meanNouns2.add("asshole");
        meanNouns2.add("scumbucket");
        meanNouns2.add("toerag");
        meanNouns2.add("hackwack");
        meanNouns2.add("imbecile");
        meanNouns2.add("stunodigan");
        meanNouns2.add("maggot");
        meanNouns2.add("hipster");
        meanNouns2.add("garbage");
        meanNouns2.add("pilot");
        meanNouns2.add("canoe");
        meanNouns2.add("captain");
        meanNouns2.add("pirate");
        meanNouns2.add("hammer");
        meanNouns2.add("knob");
        meanNouns2.add("box");
        meanNouns2.add("jockey");
        meanNouns2.add("nazi");
        meanNouns2.add("goblin");
        meanNouns2.add("waffle");
        meanNouns2.add("goblin");
        meanNouns2.add("blossum");
        meanNouns2.add("bisquit");
        meanNouns2.add("clown");
        meanNouns2.add("socket");
        meanNouns2.add("monster");
        meanNouns2.add("hound");
        meanNouns2.add("dragon");
        meanNouns2.add("balloon");
        meanNouns2.add("egg");
        meanNouns2.add("butter");
        meanNouns2.add("froth");
        meanNouns2.add("foam");
        meanNouns2.add("fluff");
        meanNouns2.add("cheese");
        meanNouns2.add("dumpling");
        meanNouns2.add("noodle");
        meanNouns2.add("nugget");
        meanNouns2.add("tostada");
        meanNouns2.add("fritter");
        meanNouns2.add("cream");
        meanNouns2.add("salami");
        meanNouns2.add("taco");
        meanNouns2.add("jelly");
        meanNouns2.add("sausage");
        meanNouns2.add("meat");
        meanNouns2.add("jam");
        meanNouns2.add("pancake");
        meanNouns2.add("salad");
        meanNouns2.add("syrup");
        meanNouns2.add("broth");
        meanNouns2.add("sandwich");
        meanNouns2.add("pizza");
        meanNouns2.add("soup");
        meanNouns2.add("souffle");
        meanNouns2.add("twinkie");
        meanNouns2.add("bean");
        meanNouns2.add("tortilla");
        meanNouns2.add("brocolli");
        meanNouns2.add("bologna");

    }

    private void initNice() {
        // nice texts
        niceTexts.add("Love youuuuuu <3");
        niceTexts.add("You're so beautiful :)");
        niceTexts.add("You're the best");
        niceTexts.add("I like your butt");
        niceTexts.add("You're sexy ;)");
        niceTexts.add("Did you sit in a pile of sugar? Cause you have a pretty sweet ass.");
        niceTexts.add("If you were a vegetable you'd be a cute-cumber.");
        niceTexts.add("Does your left eye hurt? Because you've been looking right all day.");
    }

    public static TextStringSingleton getInstance() {
        if (instance == null)
            instance = new TextStringSingleton();
        return instance;
    }

    public HashSet<String> getNiceTexts() {
        return this.niceTexts;
    }
    public HashSet<String> getMeanTexts() {
        return this.meanTexts;
    }
    public HashSet<String> getMeanPrefixes() { return this.meanPrefixes; }
    public HashSet<String> getMeanAdjs() { return this.meanAdjs; }
    public HashSet<String> getmeanNouns1() { return this.meanNouns1; }
    public HashSet<String> getMeanVerbs() { return this.meanVerbs; }

}
