/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.console;

import java.util.Map;
import org.mpasko.util.MapUtils;

/**
 *
 * @author marcin
 */
public class DefaultConfig {

    public static String fromManualFilteredWhitelistDictionaries = "inputs\\whitelists_romanji";
    public static String processedManualWhitelist = "inputs\\whitelist_other\\processed_manual.txt";
    public static String exceptions = "dictionaries/all.txt";
    public static String whitelistFromSongs = "dictionaries/songs_whitelist/agregate.txt";
    public static Map<String, String> selectedSources = MapUtils.generateMap(
            MapUtils.entry("songs1", "improved_workflow\\words\\global_sources\\songs1\\"),
            MapUtils.entry("songs2", "improved_workflow\\words\\global_sources\\songs2\\"),
            MapUtils.entry("songs3", "improved_workflow\\words\\global_sources\\songs3\\"),
            MapUtils.entry("texts", "improved_workflow\\words\\global_sources\\texts\\"),
            MapUtils.entry("fields", "improved_workflow\\words\\global_sources\\fields\\"),
            MapUtils.entry("manual_dic", "improved_workflow\\words\\global_sources\\manual_dic\\"),
            MapUtils.entry("anime", "improved_workflow\\words\\global_sources\\anime\\"));
    public static String listeningSources = "improved_workflow\\words\\listening\\source\\";
    public static String readingSources = "improved_workflow\\words\\reading\\source\\";
    public static String listeningWhitelist = "improved_workflow\\words\\listening\\whitelists\\";
    public static String readingWhitelist = "improved_workflow\\words\\reading\\whitelists\\";
    public static String understandingWhitelist = "improved_workflow\\words\\understanding\\whitelists\\";
    public static String understandingWhitelistFile = "reading_listening_merged";
    public static String whitelistFromSongsNew = understandingWhitelist + understandingWhitelistFile + ".txt";
    public static String workflowManualSources = "improved_workflow\\words\\manual_dicts";
    public static String workflowManualProcessed = "improved_workflow\\words\\global_sources\\manual_dic\\recovered_manual.txt";
    public static String onyomiWhitelist = "improved_workflow\\kanji\\test_onyomi_whitelist.txt";

}
