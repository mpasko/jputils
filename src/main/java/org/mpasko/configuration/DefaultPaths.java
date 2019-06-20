/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.configuration;

/**
 *
 * @author marcin
 */
public class DefaultPaths {

    public static String exceptions = "dictionaries/all.txt";
    public static String listeningSources = "improved_workflow/words/listening/source/";
    public static String readingSources = "improved_workflow/words/reading/source/";
    public static String listeningWhitelist = "improved_workflow/words/listening/whitelists/";
    public static String readingWhitelist = "improved_workflow/words/reading/whitelists/";
    public static String listeningBlacklist = "improved_workflow/words/listening/blacklists/";
    public static String readingBlacklist = "improved_workflow/words/reading/blacklists/";
    public static String understandingWhitelist = "improved_workflow/words/understanding/whitelists/";
    public static String understandingWhitelistFile = "reading_listening_merged";
    public static String workflowManualSources = "improved_workflow/words/manual_dicts";
    public static String wordsGlobalSources = "improved_workflow/words/global_sources";
    public static String workflowManualProcessed = wordsGlobalSources + "/manual_dic/recovered_manual.txt";
    public static String onyomiWhitelist = "improved_workflow/kanji/test_onyomi_whitelist.txt";
    public static String textSources = "improved_workflow/texts";
    public static String readingOutput = "texts";
    public static String configFile = "improved_workflow/config.json";
    public static String sourceCaches = "improved_workflow/caches";
}
